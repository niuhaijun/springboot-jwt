package com.niu.springbootjwt.interceptor;

import static com.niu.springbootjwt.common.SystemResponseStatus.REQUEST_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.niu.springbootjwt.common.Result;
import com.niu.springbootjwt.security.JwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: niuhaijun
 * @Date: 2019-07-21 11:15
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalControllerAdvice {

  /**
   * 认证失败异常捕获处理
   */
  @ExceptionHandler({JwtAuthenticationException.class})
  public Result<String> handleAuthenticationException(JwtAuthenticationException e) {

    log.info("认证失败: {}", e.getMessage());

    Result<String> result = new Result<>(UNAUTHORIZED.value(), null, e.getMessage());

    return result;
  }

  /**
   * 全局异常捕捉处理
   */
  @ExceptionHandler(value = Exception.class)
  public Result<String> handleException(Exception e) {

    log.info("System error: {}", e.getMessage());
    Result<String> result = new Result<>(REQUEST_UNAUTHORIZED, null, e.getMessage());

    return result;
  }

}
