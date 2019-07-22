package com.niu.springbootjwt.common;

import static com.niu.springbootjwt.common.SystemResponseStatus.SUCCESS;
import static com.niu.springbootjwt.common.SystemResponseStatus.SUCCESS_INFO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: niuhaijun
 * @Date: 2019-07-21 11:17
 * @Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

  private Integer code;
  private T data;
  private String message;

  public Result(T data) {

    this.code = SUCCESS;
    this.data = data;
    this.message = SUCCESS_INFO;
  }

  public Result(int code, String message) {

    this.code = code;
    this.message = message;
  }

}