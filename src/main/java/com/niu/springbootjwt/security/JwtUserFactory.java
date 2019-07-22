package com.niu.springbootjwt.security;

import com.niu.springbootjwt.dto.UserDto;
import com.niu.springbootjwt.model.Authority;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 工厂类
 *
 * 将数据库中用户信息封装为Spring Security需要的信息
 */
public final class JwtUserFactory {

  private JwtUserFactory() {

  }

  public static JwtUser create(UserDto user) {

    return new JwtUser(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        mapToGrantedAuthorities(user.getAuthorities()),
        user.getEnabled(),
        user.getLastPasswordRestDate());
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {

    return authorities.stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getName()))
        .collect(Collectors.toList());
  }
}
