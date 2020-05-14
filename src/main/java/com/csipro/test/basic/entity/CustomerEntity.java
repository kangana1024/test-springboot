package com.csipro.test.basic.entity;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "customer")
public class CustomerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name") private String name;
  @Column(name = "email") private String email;

  @Column(name = "address") private String address;

  @Column(name = "dob") private Date dob;

  @Column(name = "phone") private String phone;

  @Column(name = "createdAt") @CreationTimestamp private Date createdAt;

  @Column(name = "createdBy") private String createdBy;

  @Column(name = "updatedAt") @CreationTimestamp private Date updatedAt;

  @Column(name = "updatedBy") private String updatedBy;

  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
  private AccountEntity account;

  public Integer getId() { return id; }

  public void setId(Integer id) { this.id = id; }

  public String getAddress() { return address; }

  public void setAddress(String address) { this.address = address; }

  public Date getDob() { return dob; }

  public void setDob(Date dob) { this.dob = dob; }

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }

  public String getPhone() { return phone; }

  public void setPhone(String phone) { this.phone = phone; }

  public Date getCreatedAt() { return createdAt; }

  public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

  public String getCreatedBy() { return createdBy; }

  public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

  public Date getUpdatedAt() { return updatedAt; }

  public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

  public String getUpdatedBy() { return updatedBy; }

  public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

  public AccountEntity getAccount() { return account; }

  public void setAccount(AccountEntity account) throws Exception {
    if (account == null) {
      throw new Exception("Can not save");
    } else {
      this.account = account;
    }
  }
}
