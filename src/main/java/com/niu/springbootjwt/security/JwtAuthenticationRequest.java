package com.niu.springbootjwt.security;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * JWT认证请求信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationRequest implements Serializable {

  private static final long serialVersionUID = -8445943548965154778L;

  /**
   * 用户名
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 校验码
   */
  private String captcha;

  /**
   * 手机号
   */
  private String phone;

  /**
   * 短信码
   */
  private String smsCode;

}
