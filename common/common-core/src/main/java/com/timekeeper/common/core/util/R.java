package com.timekeeper.common.core.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author 魏子越
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "响应信息主体")
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回业务Code")
    private int code;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public static <T> R<T> ok() { return restResult(100000,null,null); }
    public static <T> R<T> ok(T data) {
        return restResult(100000, null, data);
    }
    public static <T> R<T> ok(T data, String msg) {
        return restResult(100000, msg, data);
    }
    public static <T> R<T> failed() {
        return restResult(100900, null, null);
    }
    public static <T> R<T> failed(String msg) {
        return restResult(100900, msg,null);
    }
    public static <T> R<T> failed(int code) {
        return restResult(code, null, null);
    }
    public static <T> R<T> failed(int code, String msg) {
        return restResult(code, msg, null);
    }
    public static <T> R<T> failed(int code, String msg, T data) {
        return restResult(code, msg, data);
    }
    public static <T> R<T> failed(T data) {
        return restResult(100900, null, null);
    }

    private static <T> R<T> restResult(int code, String msg, T data) {
        return new R<T>()
                .setCode(code)
                .setMsg(msg)
                .setData(data);
    }
}
