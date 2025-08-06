package com.timekeeper.common.data.mybatis.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timekeeper.common.core.constant.PaginationConstants;
import lombok.Data;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每页条数
     */
    @Min(value = 1)
    protected Integer size;

    /**
     * 当前页
     */
    @Min(value = 1)
    protected Integer current;

    /**
     * 获取每页条数
     *
     * @return Integer
     */
    public Integer getSize() {
        if (size == null || size <= 0) {
            return PaginationConstants.DEFAULT_SIZE;
        }
        return size;
    }

    /**
     * 获取当前页
     *
     * @return Integer
     */
    public Integer getCurrent() {
        if (current == null || current <= 0) {
            return PaginationConstants.DEFAULT_CURRENT;
        }
        return current;
    }

    /**
     * 获取分页对象
     *
     * @return IPage
     */
    public <T> IPage<T> getNewPage() { return new Page<>(getCurrent(), getSize()); }
}
