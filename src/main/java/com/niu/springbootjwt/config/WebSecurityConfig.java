package com.niu.springbootjwt.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.niu.springbootjwt.service.impl.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security 配置类
 *
 * @Author: niuhaijun
 * @Date: 2019-07-21 01:00
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationEntryPoint unauthorizedHandler;

  @Autowired
  private JwtAccessDeniedHandler accessDeniedHandler;

  @Autowired
  private JwtUserDetailsServiceImpl userDetailsService;

  @Autowired
  private JwtAuthorizationTokenFilter authenticationTokenFilter;

  @Value("${jwt.header}")
  private String tokenHeader;

  @Value("${jwt.route.authentication.path}")
  private String authenticationPath;

  /**
   * 加密器
   */
  @Bean
  public PasswordEncoder passwordEncoderBean() {

    return new BCryptPasswordEncoder();
  }

  /**
   * 认证管理器
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {

    return super.authenticationManagerBean();
  }

  /**
   * 用户认证
   */
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoderBean());
  }

  /**
   * 请求授权
   */
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        // we don't need CSRF because our token is invulnerable
        .csrf()
        .disable()

        // don't create session
        .sessionManagement()
        .sessionCreationPolicy(STATELESS).and()

        // 异常处理器
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .accessDeniedHandler(accessDeniedHandler)
        .and()

        // 配置请求的访问权限
        .authorizeRequests()
        .antMatchers("/protected/admin").hasRole("ADMIN")
        .anyRequest().authenticated();

    httpSecurity
        .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    // disable page caching
    httpSecurity
        .headers()
        .frameOptions()
        .sameOrigin()  // required to set for H2 else H2 Console will be blank.
        .cacheControl();
  }

  /**
   * ignore certain requests.
   */
  @Override
  public void configure(WebSecurity web) throws Exception {

    // JwtAuthorizationTokenFilter will ignore the below paths
    web
        .ignoring()
        .antMatchers(HttpMethod.POST, "/auth/**").and()

        // allow anonymous resource requests
        .ignoring()
        .antMatchers(HttpMethod.GET, "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js").and();
  }

}
