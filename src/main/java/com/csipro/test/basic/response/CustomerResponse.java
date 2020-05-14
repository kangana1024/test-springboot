package com.csipro.test.basic.response;

import com.csipro.test.basic.entity.AccountEntity;
import java.util.Date;

public class CustomerResponse {
  private Integer id;
  private String name;
  private String address;
  private String phone;
  private Date birthday;
  private String email;
  private AccountEntity account;

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }

  public String getAddress() { return address; }

  public void setAddress(String address) { this.address = address; }

  public String getPhone() { return phone; }

  public void setPhone(String phone) { this.phone = phone; }

  public Integer getId() { return id; }

  public void setId(Integer id) { this.id = id; }

  public void setAccount(AccountEntity account) { this.account = account; }

  public AccountEntity getAccount() { return account; }

  public void setDob(Date dob) { this.birthday = dob; }

  public Date getDob() { return birthday; }
}
