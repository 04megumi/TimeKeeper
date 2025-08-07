package com.timekeeper.common.security.configure;

import cn.hutool.json.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.timekeeper.common.core.util.R;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义认证入口点
 *
 * <p>当客户端请求资源但未携带有效的认证信息（如token无效或未提供token）时，
 * Spring Security 会触发此入口点，返回统一的401响应。</p>
 *
 * <p>这里实现了 AuthenticationEntryPoint 接口，重写 commence 方法，
 * 返回一个 JSON 格式的错误响应，包含自定义的错误码和错误信息。</p>
 *
 * <p>适用于资源服务器端对未认证访问的统一处理。</p>
 *
 * @author 魏子越
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 认证失败时调用的方法
     *
     * @param request  当前请求
     * @param response 当前响应
     * @param exception 认证异常信息
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        PrintWriter printWriter = response.getWriter();
        // 返回自定义的失败响应，错误码100900，消息来自异常
        printWriter.print(JSONUtil.toJsonStr(R.failed(100900, exception.getMessage())));
        printWriter.flush();
        printWriter.close();
    }
}