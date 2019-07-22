package com.niu.springbootjwt.common;

/**
 * @Author: niuhaijun
 * @Date: 2019-07-21 11:17
 * @Version 1.0
 */
public class SystemResponseStatus {

  /**请求成功*/
  public static final String SUCCESS_INFO = "success";

  /***
   * 以下为信息message
   */
  /**请求成功*/
  public static final int SUCCESS = 2000000;

  /**
   * 以下为code
   */
  /**请求异常*/
  public static final int REQUEST_EXCEPTION = 4000000;
  /**请求未授权*/
  public static final int REQUEST_UNAUTHORIZED = 4010001;
  /**请求参数不能为空*/
  public static final int REQUEST_PARAMETER_NOT_EMPTY = 4150001;
  /**Token失效*/
  public static final int TOKEN_INVALID = 4170199;
  /**服务处理异常*/
  public static final int SERVICE_EXCEPTION = 5000000;

  private SystemResponseStatus() {

  }

}
