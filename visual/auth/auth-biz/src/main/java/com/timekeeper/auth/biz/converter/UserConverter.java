package com.timekeeper.auth.biz.converter;

import com.timekeeper.auth.api.entity.User;
import com.timekeeper.auth.api.vo.MeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * User 实体 与 MeVO 视图对象之间的转换器
 * <p>
 * 使用 MapStruct 自动生成实现类，简化对象之间的属性拷贝。
 * 一般用于将数据库实体 {@link User} 转换为对外展示的 VO 对象 {@link MeVO}，
 * 避免向前端暴露敏感字段（如密码、手机号等）。
 * </p>
 *
 * <p>使用方式示例：
 * <pre>{@code
 * MeVO meVO = UserConverter.INSTANCE.toMeVO(userEntity);
 * }</pre>
 * </p>
 *
 * @author 魏子越
 * @since 2025-08-09
 */
@Mapper
public interface UserConverter {

    /**
     * 单例实例，由 MapStruct 自动生成实现类
     */
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * 将 User 实体转换为 MeVO 对象
     *
     * @param user 用户实体对象
     * @return 用户基本信息 VO
     */
    MeVO toMeVO(User user);
}