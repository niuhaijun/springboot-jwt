package com.niu.springbootjwt.config;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 401 Unauthorized
 *
 * 在没有通过身份验证时，访问受保护的资源。返回一个响应码，暗示用户必须去认证。
 *
 *
 * @Author: niuhaijun
 * @Date: 2019-07-21 01:01
 * @Version 1.0
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * 未登录或无权限时触发的操作
   * @throws IOException
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    log.info("无权访问，需要去认证");

    // This is invoked when user tries to access a secured REST resource without
    // supplying any credentials.
    // We should just send a 401 Unauthorized response because there is no
    // 'login page' to redirect to
    response.sendError(SC_UNAUTHORIZED, "Unauthorized");
  }
}
