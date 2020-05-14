package com.csipro.test.basic.request;

public class CustomerRequest {
  private Integer id;
  private String name;
  private String phone;
  private String address;
  private String email;
  private String dob;

  public Integer getId() { return id; }

  public void setId(Integer id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public void setAddress(String address) { this.address = address; }

  public String getAddress() { return address; }

  public String getPhone() { return phone; }

  public void setPhone(String phone) { this.phone = phone; }

  public String getDob() { return dob; }

  public void setDob(String dob) { this.dob = dob; }
}
