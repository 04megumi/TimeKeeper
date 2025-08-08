package com.timekeeper.common.data.mybatis.model;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * Mybatis 数据基类
 *
 * @author 魏子越
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseModel<T extends BaseModel<?>> extends Model<T> {

    private static final long serialVersionUID = 1L;

    /**
     * 是否删除（0：否，1：是）
     */
    @TableLogic
    @TableField(value = "is_deleted")
    protected Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    protected String createBy;

    /**
     * 修改人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;

    /**
     * 乐观锁版本号
     */
    @Version
    @TableField(value = "version")
    protected Integer version;

    /**
     * 是否已删除记录
     *
     * @return 是否
     */
    public boolean isDeleted() {
        return ObjectUtil.isNotNull(isDeleted) && isDeleted.equals(1);
    }

    /**
     * 是否存续记录
     *
     * @return 是否
     */
    public boolean isActive() {
        return ObjectUtil.isNull(isDeleted) || isDeleted.equals(0);
    }
}
