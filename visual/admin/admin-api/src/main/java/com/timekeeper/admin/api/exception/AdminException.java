package com.timekeeper.admin.api.exception;

import com.timekeeper.common.core.constant.BusinessCodes;
import com.timekeeper.common.core.exception.BusinessException;
import lombok.Getter;

@Getter
public class AdminException extends BusinessException {

    private final int code;

    public AdminException(String message) {
        super(message);
        this.code = BusinessCodes.ADMIN_ERROR;
    }
}
