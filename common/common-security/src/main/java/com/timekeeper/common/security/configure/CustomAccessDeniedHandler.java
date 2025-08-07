package com.timekeeper.common.security.configure;

import cn.hutool.json.JSONUtil;
import com.timekeeper.common.core.util.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义访问拒绝处理器
 *
 * <p>当用户携带的Token合法但权限不足以访问请求资源时触发，返回403状态码和错误信息。</p>
 * <p>响应内容为JSON格式，包含错误码和异常消息。</p>
 *
 * @author 魏子越
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 处理访问拒绝异常
     *
     * @param request   当前HTTP请求
     * @param response  当前HTTP响应
     * @param exception 权限拒绝异常
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) throws IOException, ServletException {
        // 设置响应状态码为 403 Forbidden，表示权限不足
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // 获取响应输出流
        PrintWriter printWriter = response.getWriter();

        // 输出格式化的JSON错误信息，包含自定义错误码和异常信息
        printWriter.print(JSONUtil.toJsonStr(R.failed(100900, exception.getMessage())));

        printWriter.flush();
        printWriter.close();
    }
}