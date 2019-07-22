package com.niu.springbootjwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niu.springbootjwt.common.Result;
import com.niu.springbootjwt.security.JwtAuthenticationException;
import com.niu.springbootjwt.security.JwtAuthenticationRequest;
import com.niu.springbootjwt.security.JwtAuthenticationResponse;
import com.niu.springbootjwt.security.JwtTokenUtil;
import com.niu.springbootjwt.security.JwtUser;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Slf4j
public class AuthUserController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  @Qualifier("jwtUserDetailsServiceImpl")
  private UserDetailsService userDetailsService;

  @Value("${jwt.header}")
  private String tokenHeader;

  /**
   * 登录接口
   */
  @PostMapping(value = "/login")
  public Result<Map<String, String>> createAuthenticationToken(
      @RequestBody JwtAuthenticationRequest authenticationRequest)
      throws JwtAuthenticationException {

    String username = authenticationRequest.getUsername();
    String password = authenticationRequest.getPassword();

    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {

      Result<Map<String, String>> result = new Result<>();
      result.setCode(400);
      result.setMessage("username或password参数缺失或存在空值");
      result.setData(null);
      return result;
    }

    authenticate(username, password);

    // Reload password post-security so we can generate the token
    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    final String token = jwtTokenUtil.generateToken(userDetails);

    // Return the token
    Map<String, String> data = new HashMap<>();
    data.put("token", token);
    data.put("username", username);

    return new Result<>(data);
  }

  /**
   * 刷新并获取token接口
   */
  @GetMapping(value = "/refresh")
  public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {

    String authToken = request.getHeader(tokenHeader);
    final String token = authToken.substring(7);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

    if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
      String refreshedToken = jwtTokenUtil.refreshToken(token);
      return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
    }

    return ResponseEntity.badRequest().body(null);
  }

  /**
   * authenticate the user. 认证用户
   *
   * If something is wrong, an {@link JwtAuthenticationException} will be thrown
   */
  private void authenticate(String username, String password) {

    try {
      Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
      Authentication obj = authenticationManager.authenticate(authentication);
      log.info(new ObjectMapper().writeValueAsString(obj));
    }
    catch (DisabledException e) {
      log.info("User is disabled! {}", e.getMessage());
      throw new JwtAuthenticationException("User is disabled!", e);
    }
    catch (BadCredentialsException e) {
      log.info("Bad credentials! {}", e.getMessage());
      throw new JwtAuthenticationException("Bad credentials!", e);
    }
    catch (Exception e) {
      log.info("Unknown error! {}", e.getMessage());
      throw new JwtAuthenticationException("Unknown error!", e);
    }
  }
}
