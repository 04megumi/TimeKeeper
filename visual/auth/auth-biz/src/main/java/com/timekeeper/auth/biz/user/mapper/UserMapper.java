package com.timekeeper.auth.biz.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timekeeper.auth.api.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表数据访问层接口
 *
 * 继承 MyBatis-Plus 提供的 BaseMapper，
 * 支持对 User 实体类对应数据库表的基础 CRUD 操作。
 *
 * @author 魏子越
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getUserByUserId(String uid);

    User getUserByUserNameEn(String userNameEn);

    User getUserByPhoneNum(String phoneNum);

    User getUserByOpenidUserName(@Param("openid") String openid, @Param("userName") String userName);

    // ====================== 恢复软删除 ======================

    int restoreUserByUser(User user);
}
