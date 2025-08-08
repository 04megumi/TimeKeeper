package com.timekeeper.auth.biz.converter;

import com.timekeeper.auth.api.vo.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * TokenConverter 接口
 *
 * 使用 MapStruct 自动生成实现类，
 * 实现将字符串转换为 Token 对象的功能。
 *
 * 映射规则：
 * - 将传入的字符串作为 Token 对象的 token 属性值。
 * @author 魏子越
 */
@Mapper
public interface TokenConverter {

    TokenConverter INSTANCE = Mappers.getMapper(TokenConverter.class);

    @Mapping(target = "token", source = ".")
    Token toToken(String token);
}
