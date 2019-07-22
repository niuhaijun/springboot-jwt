package com.niu.springbootjwt.controller;

import com.niu.springbootjwt.common.Result;
import com.niu.springbootjwt.security.JwtTokenUtil;
import com.niu.springbootjwt.security.JwtUser;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

  @Value("${jwt.header}")
  private String tokenHeader;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  @Qualifier("jwtUserDetailsServiceImpl")
  private UserDetailsService userDetailsService;

  /**
   * 获取已授权的用户
   */
  @GetMapping(value = "user")
  public Result<JwtUser> getAuthenticatedUser(HttpServletRequest request) {

    String token = request.getHeader(tokenHeader).substring(7);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
    return new Result<>(user);
  }

}
