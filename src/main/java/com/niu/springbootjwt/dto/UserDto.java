package com.niu.springbootjwt.dto;

import com.niu.springbootjwt.model.Authority;
import com.niu.springbootjwt.model.User;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: niuhaijun
 * @Date: 2019-07-21 20:30
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends User {

  private Integer id;

  private String username;

  private String password;

  private String phone;

  private Boolean enabled;

  private Boolean deleted;

  private Date lastPasswordRestDate;

  private List<Authority> authorities;

}
