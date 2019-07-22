package com.niu.springbootjwt.security;

import java.util.Collection;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author: niuhaijun
 * @Date: 2019-07-21 01:23
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser implements UserDetails {

  private Integer id;
  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private boolean enabled;
  private Date lastPasswordResetDate;

  /**
   * 返回授予用户的权限, 不能返回null
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return authorities;
  }

  /**
   * 返回用于验证用户身份的密码，不能返回null
   */
  @Override
  public String getPassword() {

    return password;
  }

  /**
   * 用于验证用户身份的用户名，不能返回null
   */
  @Override
  public String getUsername() {

    return username;
  }

  /**
   * 用户是否未过期
   *
   * true 未过期
   * false 已过期
   */
  @Override
  public boolean isAccountNonExpired() {

    return true;
  }

  /**
   * 用户是否未被锁定
   *
   * true 未被锁定
   * false 已锁定
   */
  @Override
  public boolean isAccountNonLocked() {

    return true;
  }

  /**
   * 密码是否未过期
   *
   * true 未过期
   * false 已过期
   */
  @Override
  public boolean isCredentialsNonExpired() {

    return true;
  }

  /**
   * 用户是否可用
   *
   * true 可用
   * false 不可用
   */
  @Override
  public boolean isEnabled() {

    return enabled;
  }
}
