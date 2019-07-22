package com.niu.springbootjwt.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 403 forbidden
 *
 * @Author: niuhaijun
 * @Date: 2019-07-21 18:15
 * @Version 1.0
 */
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException, ServletException {

    //返回json形式的错误信息
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType("application/json");

    httpServletResponse.getWriter()
        .println("{\"code\":403,\"message\":\"小弟弟，你没有权限访问呀！\",\"data\":\"\"}");
    httpServletResponse.getWriter().flush();
  }
}

