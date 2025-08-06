package com.timekeeper.common.data.mybatis;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.util.ClassUtils;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * MybatisPlus 自动填充配置
 *
 * @author 魏子越
 */
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    private final CurrentUserService currentUserService;

    public MybatisPlusMetaObjectHandler(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    /**
     * 获取当前用户的ID
     *
     * @return Integer
     */
    private Integer getCurrentUserId() {
        if (ObjectUtil.isNull(currentUserService)) {
            return null;
        }
        return currentUserService.getUid();
    }

    /**
     * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
     *
     * @param fieldName 属性名
     * @param fieldVal 属性值
     * @param metaObject MetaObject
     * @param isCover 是否覆盖原有值,避免更新操作手动入参
     */
    private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
        // 0. 如果填充值为空
        if (fieldVal == null) {
            return;
        }
        // 1. 没有 get 或 set 方法
        if (!metaObject.hasSetter(fieldName) || !metaObject.hasGetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值
        Object userSetValue = metaObject.getValue(fieldName);
        String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
        if (StrUtil.isNotBlank(setValueStr) && !isCover) {
            return;
        }
        // 3. field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        // 审计字段自动填充
        LocalDateTime now = LocalDateTime.now();
        fillValIfNullByName("createTime", now, metaObject, false);
        fillValIfNullByName("updateTime", now, metaObject, false);
        fillValIfNullByName("createBy", getCurrentUserId(), metaObject, false);
        fillValIfNullByName("updateBy", getCurrentUserId(), metaObject, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject, true);
        fillValIfNullByName("updateBy", getCurrentUserId(), metaObject, true);
    }
}
