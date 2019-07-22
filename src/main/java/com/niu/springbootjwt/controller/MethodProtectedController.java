package com.niu.springbootjwt.controller;

import com.niu.springbootjwt.common.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("protected")
public class MethodProtectedController {

  /**
   * This is an example of some different kinds of granular restriction for endpoints.
   * You can use the built-in SPEL expressions
   * in @PreAuthorize such as 'hasRole()' to determine if a user has access.
   * Remember that the hasRole expression assumes a 'ROLE_' prefix on all role names.
   * So 'ADMIN' here is actually stored as 'ROLE_ADMIN' in database!
   **/
  @GetMapping("admin")
  public Result<String> adminAccess() {

    Result<String> result = new Result<>("Greetings from 【admin】 protected method!");

    return result;
  }


  @GetMapping("user")
  @PreAuthorize("hasRole('USER')")
  public Result<String> userAccess() {

    Result<String> result = new Result<>("Greetings from 【user】 protected method!");

    return result;
  }

}