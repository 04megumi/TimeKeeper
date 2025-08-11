package com.timekeeper.admin.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timekeeper.auth.api.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 魏子越
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
