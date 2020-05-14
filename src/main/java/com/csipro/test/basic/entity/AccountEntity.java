package com.csipro.test.basic.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "account")
public class AccountEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name") private String name;

  @Column(name = "number") private String number;

  @Column(name = "amount") private Double amount;

  @Column(name = "createdAt") @CreationTimestamp private Date createdAt;

  @Column(name = "createdBy") private String createdBy;

  @Column(name = "updatedAt") @CreationTimestamp private Date updatedAt;

  @Column(name = "updatedBy") private String updatedBy;

  @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerEntity customer;

  public void setCustomer(CustomerEntity customer) throws Exception {
    if (customer == null) {
      throw new Exception("Can not save");
    }
    this.customer = customer;
  }

  public Integer getCutomerId() { return customer.getId(); }

  public Integer getId() { return id; }

  public void setId(Integer id) { this.id = id; }

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public String getNumber() { return number; }

  public void setNumber(String number) { this.number = number; }

  public Date getCreatedAt() { return createdAt; }

  public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

  public String getCreatedBy() { return createdBy; }

  public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

  public Date getUpdatedAt() { return updatedAt; }

  public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

  public String getUpdatedBy() { return updatedBy; }

  public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

  public Double getAmount() { return amount; }

  public void setAmount(Double amount) { this.amount = amount; }
}
