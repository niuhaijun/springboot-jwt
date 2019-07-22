package com.niu.springbootjwt.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  private Integer id;
  private String username;
  private String password;
  private String phone;
  private Boolean enabled;
  private Boolean deleted;
  private Date lastPasswordRestDate;
  private String updateSql;

  public User(Integer id, String username, String password, String phone, Boolean enabled,
      Boolean deleted, Date lastPasswordRestDate) {

    this.id = id;
    this.username = username;
    this.password = password;
    this.phone = phone;
    this.enabled = enabled;
    this.deleted = deleted;
    this.lastPasswordRestDate = lastPasswordRestDate;
  }

  public User() {

    super();
  }

  public Integer getId() {

    return id;
  }

  public void setId(Integer id) {

    this.id = id;
  }

  public String getUsername() {

    return username;
  }

  public void setUsername(String username) {

    this.username = username == null ? null : username.trim();
  }

  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password == null ? null : password.trim();
  }

  public String getPhone() {

    return phone;
  }

  public void setPhone(String phone) {

    this.phone = phone == null ? null : phone.trim();
  }

  public Boolean getEnabled() {

    return enabled;
  }

  public void setEnabled(Boolean enabled) {

    this.enabled = enabled;
  }

  public Boolean getDeleted() {

    return deleted;
  }

  public void setDeleted(Boolean deleted) {

    this.deleted = deleted;
  }

  public Date getLastPasswordRestDate() {

    return lastPasswordRestDate;
  }

  public void setLastPasswordRestDate(Date lastPasswordRestDate) {

    this.lastPasswordRestDate = lastPasswordRestDate;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", username=").append(username);
    sb.append(", password=").append(password);
    sb.append(", phone=").append(phone);
    sb.append(", enabled=").append(enabled);
    sb.append(", deleted=").append(deleted);
    sb.append(", lastPasswordRestDate=").append(lastPasswordRestDate);
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