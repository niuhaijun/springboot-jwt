package com.niu.springbootjwt.model;

import java.io.Serializable;

public class UserAuthority implements Serializable {

  private static final long serialVersionUID = 1L;
  private Integer id;
  private Integer userId;
  private Integer authorityId;
  private String updateSql;

  public UserAuthority(Integer id, Integer userId, Integer authorityId) {

    this.id = id;
    this.userId = userId;
    this.authorityId = authorityId;
  }

  public UserAuthority() {

    super();
  }

  public Integer getId() {

    return id;
  }

  public void setId(Integer id) {

    this.id = id;
  }

  public Integer getUserId() {

    return userId;
  }

  public void setUserId(Integer userId) {

    this.userId = userId;
  }

  public Integer getAuthorityId() {

    return authorityId;
  }

  public void setAuthorityId(Integer authorityId) {

    this.authorityId = authorityId;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", userId=").append(userId);
    sb.append(", authorityId=").append(authorityId);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }

  public String getUpdateSql() {

    return this.updateSql;
  }

  public void setUpdateSql(String updateSql) {

    this.updateSql = updateSql;
  }
}