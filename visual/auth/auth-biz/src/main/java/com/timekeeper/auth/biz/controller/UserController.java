package com.timekeeper.auth.biz.controller;

import com.timekeeper.auth.api.entity.User;
import com.timekeeper.auth.api.vo.MeVO;
import com.timekeeper.auth.biz.converter.UserConverter;
import com.timekeeper.common.core.util.R;
import com.timekeeper.common.security.util.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /**
     * 用户信息接口
     *
     * <p>用于获取当前登录用户的基础信息。
     *
     * <h3>请求说明</h3>
     * <ul>
     *     <li>请求方法：GET</li>
     *     <li>请求路径：/me</li>
     *     <li>请求头：必须携带 <code>Authorization: Bearer &lt;JWT&gt;</code> 形式的 Token</li>
     * </ul>
     *
     * <h3>返回数据格式</h3>
     * <pre>
     * {
     *   "code": 0,
     *   "msg": "success",
     *   "data": {
     *     "name": "张三",      // 小程序端使用此字段作为展示名
     *     "nameEn": "zhangsan" // Web 端使用此字段作为展示名（系统唯一标识）
     *   }
     * }
     * </pre>
     *
     * <h3>注意事项</h3>
     * <ul>
     *     <li>小程序请求时使用 <code>name</code> 字段展示用户名称</li>
     *     <li>Web 端请求时使用 <code>nameEn</code> 字段展示用户名称</li>
     * </ul>
     *
     * @return 包含当前用户信息的统一响应结果
     */
    @GetMapping(value = "/me")
    public R<MeVO> me() {
        return R.ok(UserConverter.INSTANCE.toMeVO((User) SecurityUtils.getUser()));
    }
}
