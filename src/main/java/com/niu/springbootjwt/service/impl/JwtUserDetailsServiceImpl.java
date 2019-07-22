package com.niu.springbootjwt.service.impl;

import com.niu.springbootjwt.dto.UserDto;
import com.niu.springbootjwt.mapper.AuthorityMapper;
import com.niu.springbootjwt.mapper.UserAuthorityMapper;
import com.niu.springbootjwt.mapper.UserMapper;
import com.niu.springbootjwt.model.Authority;
import com.niu.springbootjwt.model.AuthorityExample;
import com.niu.springbootjwt.model.User;
import com.niu.springbootjwt.model.UserAuthority;
import com.niu.springbootjwt.model.UserAuthorityExample;
import com.niu.springbootjwt.model.UserExample;
import com.niu.springbootjwt.security.JwtUserFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @Author: niuhaijun
 * @Date: 2019-07-21 01:20
 * @Version 1.0
 */
@Service
@Slf4j
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserAuthorityMapper userAuthorityMapper;

  @Autowired
  private AuthorityMapper authorityMapper;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserExample userExample = new UserExample();
    userExample.createCriteria().andUsernameEqualTo(username);
    List<User> users = userMapper.selectByExample(userExample);
    if (CollectionUtils.isEmpty(users)) {
      log.info("No user found with username: {}", username);
      throw new UsernameNotFoundException(
          String.format("No user found with username '%s'.", username));
    }

    User user = users.get(0);

    UserAuthorityExample userAuthorityExample = new UserAuthorityExample();
    userAuthorityExample.createCriteria().andUserIdEqualTo(user.getId());
    List<UserAuthority> userAuthorities = userAuthorityMapper.selectByExample(userAuthorityExample);
    List<Integer> authorityIds = userAuthorities.stream().map(UserAuthority::getAuthorityId)
        .collect(Collectors.toList());

    AuthorityExample authorityExample = new AuthorityExample();
    authorityExample.createCriteria().andIdIn(authorityIds);
    List<Authority> authorities = authorityMapper.selectByExample(authorityExample);

    UserDto userDto = UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .phone(user.getPhone())
        .enabled(user.getEnabled())
        .deleted(user.getDeleted())
        .lastPasswordRestDate(user.getLastPasswordRestDate())
        .authorities(authorities)
        .build();

    return JwtUserFactory.create(userDto);

  }
}
