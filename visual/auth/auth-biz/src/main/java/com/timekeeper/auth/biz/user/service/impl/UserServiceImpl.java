package com.timekeeper.auth.biz.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.timekeeper.auth.api.dto.LoginRequest;
import com.timekeeper.auth.api.dto.impl.AccountLoginDTO;
import com.timekeeper.auth.api.dto.impl.PhoneLoginDTO;
import com.timekeeper.auth.api.dto.impl.WechatMpAppLoginDTO;
import com.timekeeper.auth.api.entity.User;
import com.timekeeper.auth.api.exception.AuthException;
import com.timekeeper.auth.biz.constant.Platform;
import com.timekeeper.auth.biz.user.mapper.UserMapper;
import com.timekeeper.auth.biz.user.service.AuthUserDetailsService;
import com.timekeeper.auth.biz.user.service.JwtEncoder;
import com.timekeeper.common.core.constant.RoleConstants;
import com.timekeeper.common.security.service.BaseUserDetails;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * 该类实现了 BaseUserDetailsService 接口，提供根据用户ID或用户名加载用户详情的方法，
 * 主要用于 Spring Security 认证流程中获取用户信息。
 *
 * 依赖 UserMapper 操作数据库中的用户表，利用 MyBatis-Plus 提供的 API 简化查询。
 */
@Service
public class UserServiceImpl implements AuthUserDetailsService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 用户数据访问对象，负责与数据库交互
     */
    @NonNull
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 根据用户ID加载用户详情
     *
     * @param uid 用户唯一标识（ID）
     * @return 用户详情对象，需实现 BaseUserDetails 接口
     * @throws AuthException 如果用户不存在则抛出异常
     */
    @Override
    public BaseUserDetails loadUserByUid(String uid) {
        try {
            User user = userMapper.selectById(uid);
            if (ObjectUtil.isNull(user)) {
                throw new AuthException("用户不存在，ID: " + uid);
            }
            return user;
        } catch (Exception e) {
            throw new AuthException("LoadUserByUid::" + e.getMessage());
        }
    }

    /**
     * 根据用户名加载用户详情
     *
     * @param usernameEn 用户名英文
     * @return 用户详情对象，需实现 BaseUserDetails 接口
     * @throws AuthException 如果用户名对应的用户不存在则抛出异常
     */
    public BaseUserDetails loadUserByUsername(String usernameEn) {
        try {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.eq("name_en", usernameEn);
            User user = userMapper.selectOne(query);
            // 判断用户是否存在或主键ID是否为空
            if (ObjectUtil.isNull(user) || ObjectUtil.isNull(user.getId())) {
                throw new AuthException("用户不存在：" + usernameEn);
            }
            return user;
        } catch (Exception e) {
            throw new AuthException("LoadUserByUsername::" + e.getMessage());
        }
    }

    /**
     * 根据手机号加载用户详情
     *
     * @param phoneNumber 用户手机号
     * @return 用户详情对象，需实现 BaseUserDetails 接口
     * @throws AuthException 如果手机号对应的用户不存在则抛出异常
     */
    public BaseUserDetails loadUserByPhoneNumber(String phoneNumber) {
        try {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.eq("phone_num", phoneNumber);
            User user = userMapper.selectOne(query);

            if (ObjectUtil.isNull(user) || ObjectUtil.isNull(user.getId())) {
                throw new AuthException("用户不存在，手机号：" + phoneNumber);
            }
            return user;
        } catch (Exception e) {
            throw new AuthException("LoadUserByPhoneNumber::" + e.getMessage());
        }
    }

    /**
     * 根据 OpenID, username 加载用户详情
     * 注意一个家长可能有多个孩子, 所以需要联合唯一索引 (openid, username)
     *
     * @param openId 用户的第三方登录唯一标识 OpenID,
     * @param username 学生名
     * @return 用户详情对象，需实现 BaseUserDetails 接口
     * @throws AuthException 如果 OpenID 对应的用户不存在则抛出异常
     */
    public BaseUserDetails loadUserByOpenIdUserName(String openId, String username) {
        try {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.eq("openid", openId);
            query.eq("name", username);
            User user = userMapper.selectOne(query);
            if (ObjectUtil.isNull(user) || ObjectUtil.isNull(user.getId())) {
                throw new AuthException("用户不存在，OpenID：" + openId);
            }
            return user;
        } catch (Exception e) {
            throw new AuthException("LoadUserByOpenIdUserName::" + e.getMessage());
        }
    }

    /**
     * 根据登录平台及登录请求进行登录操作
     *
     * @param platform     登录平台枚举
     * @param loginRequest 登录请求参数，具体实现类视平台而定
     * @return jwtToken
     * @throws AuthException 如果登录请求类型无效或登录失败
     */
    @Override
    public String login(Platform platform, LoginRequest loginRequest) {
        return switch (platform) {
            case WX -> {
                if (!(loginRequest instanceof WechatMpAppLoginDTO)) {
                    throw new AuthException("无效的登录请求类型");
                }
                yield JwtEncoder.generateToken(login((WechatMpAppLoginDTO) loginRequest).getId());
            }
            case PHONE -> {
                if (!(loginRequest instanceof PhoneLoginDTO)) {
                    throw new AuthException("无效的登录请求类型");
                }
                yield JwtEncoder.generateToken(login((PhoneLoginDTO) loginRequest).getId());
            }
            case NAME -> {
                if (!(loginRequest instanceof AccountLoginDTO)) {
                    throw new AuthException("无效的登录请求类型");
                }
                yield JwtEncoder.generateToken(login((AccountLoginDTO) loginRequest).getId());
            }
            default -> throw new AuthException("未知登录平台");
        };
    }

    /**
     * 微信小程序登录，若无账号则注册
     *
     * @param loginRequest 微信小程序登录请求DTO
     * @return 登录或注册成功的用户详情
     */
    private BaseUserDetails login(WechatMpAppLoginDTO loginRequest) {
        try {
            if (ObjectUtil.isNull(loginRequest.getOpenid()) || ObjectUtil.isNull(loginRequest.getOpenid())) {
                throw new AuthException("校验参数");
            }
            BaseUserDetails user = this.loadUserByOpenIdUserName(loginRequest.getOpenid(), loginRequest.getUserName());
            // 小程序端 需要注册即登录
            if (ObjectUtil.isNull(user)) {
                register(loginRequest);
            }
            // fix: 这里需要重新查询
            return this.loadUserByOpenIdUserName(loginRequest.getOpenid(), loginRequest.getUserName());
        } catch (Exception e) {
            throw new AuthException("登录异常::WX::" + e.getMessage());
        }
    }

    /**
     * 手机号登录，校验密码
     *
     * @param loginRequest 手机号登录请求DTO
     * @return 登录成功的用户详情
     * @throws AuthException 密码错误或用户不存在时抛出
     */
    private BaseUserDetails login(PhoneLoginDTO loginRequest) {
        try {
            BaseUserDetails user = loadUserByPhoneNumber(loginRequest.getPhoneNum());
            if (ObjectUtil.isNull(user) || !encoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new AuthException("账号名或密码错误");
            }
            return user;
        } catch (Exception e) {
            throw new AuthException("登录异常::PHONE::" + e.getMessage());
        }
    }

    /**
     * 账号密码登录，校验密码
     *
     * @param loginRequest 账号登录请求DTO
     * @return 登录成功的用户详情
     * @throws AuthException 密码错误或用户不存在时抛出
     */
    private BaseUserDetails login(AccountLoginDTO loginRequest) {
        try {
            BaseUserDetails user = loadUserByUsername(loginRequest.getUserNameEn());
            if (ObjectUtil.isNull(user) || !encoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new AuthException("账号名或密码错误");
            }
            return user;
        } catch (Exception e) {
            throw new AuthException("登录异常::ACCOUNT::" + e.getMessage());
        }
    }

    /**
     * 用户注册入口，根据登录平台类型选择不同的注册实现方法。
     *
     * @param platform     登录平台枚举，支持微信小程序(WX)、手机号(PHONE)、用户名(NAME)
     * @param loginRequest 注册请求参数，具体实现类视平台而定（WechatMpAppLoginDTO、PhoneLoginDTO、AccountLoginDTO）
     * @throws AuthException 当传入的注册请求类型与平台不匹配，或平台未知时抛出异常
     */
    @Override
    public void register(Platform platform, LoginRequest loginRequest) {
        switch (platform) {
            case WX:
                if (!(loginRequest instanceof WechatMpAppLoginDTO)) {
                    throw new AuthException("注册异常::无效的注册请求类型");
                }
                register((WechatMpAppLoginDTO) loginRequest);
                break;

            case PHONE:
                if (!(loginRequest instanceof PhoneLoginDTO)) {
                    throw new AuthException("注册异常::无效的注册请求类型");
                }
                register((PhoneLoginDTO) loginRequest);
                break;

            case NAME:
                if (!(loginRequest instanceof AccountLoginDTO)) {
                    throw new AuthException("注册异常::无效的注册请求类型");
                }
                register((AccountLoginDTO) loginRequest);
                break;

            default:
                throw new AuthException("注册异常::未知登录平台");
        }
    }

    /**
     * 根据微信小程序登录信息注册用户
     * <p>
     * 先查询数据库中是否存在该openid对应的用户：
     * - 如果存在且激活状态（未软删除），抛出账号已存在异常
     * - 如果存在但已软删除，则恢复用户（设置 isDeleted=0 并更新）
     * - 如果不存在，则新建用户记录并插入数据库
     *
     * @param loginRequest 微信小程序登录请求DTO，包含openid
     * @throws AuthException 账号已存在时抛出异常
     */
    private void register(WechatMpAppLoginDTO loginRequest) {
        try {
            if (ObjectUtil.isNull(loginRequest.getOpenid()) || ObjectUtil.isNull(loginRequest.getOpenid())) {
                throw new AuthException("校验参数");
            }

            User user = userMapper.getUserByOpenidUserName(loginRequest.getOpenid(), loginRequest.getUserName());
            if (ObjectUtil.isNotNull(user) && user.isActive()) {
                throw new AuthException("账号已存在: " + user.getOpenId() + "+" + user.getName());
            }
            if (ObjectUtil.isNull(user)) {
                User newUser = new User();
                newUser.setOpenId(loginRequest.getOpenid());
                newUser.setName(loginRequest.getUserName());
                newUser.setRole(RoleConstants.STUDENT);
                userMapper.insert(newUser);
                return;
            }
            user.setIsDeleted(0);
            user.setName(loginRequest.getUserName());
            user.setRole(RoleConstants.STUDENT);
            userMapper.updateById(user);
        } catch (Exception e) {
            throw new AuthException("注册异常::WX::" + e.getMessage());
        }
    }

    /**
     * 根据手机号注册用户
     * <p>
     * 先查询手机号是否已注册：
     * - 如果存在且激活状态，抛出账号已存在异常
     * - 如果存在但软删除，则恢复用户（设置 isDeleted=0 并更新）
     * - 如果不存在，则新建用户记录并插入数据库
     * <p>
     * 注意：密码应在插入前进行加密处理，防止明文存储。
     *
     * @param loginRequest 手机号登录请求DTO，包含手机号和密码
     * @throws AuthException 账号已存在时抛出异常
     */
    private void register(PhoneLoginDTO loginRequest) {
        try {
            if (ObjectUtil.isNull(loginRequest.getPhoneNum()) || ObjectUtil.isNull(loginRequest.getPhoneNum())) {
                throw new AuthException("校验参数");
            }

            User user = userMapper.getUserByPhoneNum(loginRequest.getPhoneNum());
            if (ObjectUtil.isNotNull(user) && user.isActive()) {
                throw new AuthException("账号已存在: " + user.getPhoneNumber());
            }
            if (ObjectUtil.isNull(user)) {
                User newUser = new User();
                newUser.setPhoneNumber(loginRequest.getPhoneNum());
                newUser.setPassword(encoder.encode(loginRequest.getPassword()));
                newUser.setRole(RoleConstants.STUDENT);
                userMapper.insert(newUser);
                return;
            }
            user.setIsDeleted(0);
            user.setPassword(encoder.encode(loginRequest.getPassword()));
            user.setRole(RoleConstants.STUDENT);
            userMapper.updateById(user);
        } catch (Exception e) {
            throw new AuthException("注册异常::PHONE::" + e.getMessage());
        }
    }

    /**
     * 根据用户名注册用户
     * <p>
     * 先查询用户名是否已注册：
     * - 如果存在且激活状态，抛出账号已存在异常
     * - 如果存在但软删除，则恢复用户（设置 isDeleted=0 并更新）
     * - 如果不存在，则新建用户记录并插入数据库
     * <p>
     * 注意：密码应在插入前进行加密处理，防止明文存储。
     *
     * @param loginRequest 账号登录请求DTO，包含用户名和密码
     * @throws AuthException 账号已存在时抛出异常
     */
    private void register(AccountLoginDTO loginRequest) {
        try {
            if (ObjectUtil.isNull(loginRequest.getUserNameEn()) || ObjectUtil.isNull(loginRequest.getPassword())) {
                throw new AuthException("校验参数");
            }

            User user = userMapper.getUserByUserNameEn(loginRequest.getUserNameEn());
            if (ObjectUtil.isNotNull(user) && user.isActive()) {
                throw new AuthException("账号已存在: " + user.getUserNameEn());
            }
            if (ObjectUtil.isNull(user)) {
                User newUser = new User();
                newUser.setNameEn(loginRequest.getUserNameEn());
                newUser.setPassword(encoder.encode(loginRequest.getPassword()));
                newUser.setRole(RoleConstants.STUDENT);
                userMapper.insert(newUser);
                return;
            }
            user.setIsDeleted(0);
            user.setPassword(encoder.encode(loginRequest.getPassword()));
            user.setRole(RoleConstants.STUDENT);
            userMapper.updateById(user);
        } catch (Exception e) {
            throw new AuthException("注册异常::ACCOUNT::" + e.getMessage());
        }
    }
}