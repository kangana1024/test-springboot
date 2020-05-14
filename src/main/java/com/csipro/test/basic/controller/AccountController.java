package com.csipro.test.basic.controller;

import com.csipro.test.basic.entity.AccountEntity;
import com.csipro.test.basic.entity.CustomerEntity;
import com.csipro.test.basic.repository.AccountRepository;
import com.csipro.test.basic.repository.CustomerRepository;
import com.csipro.test.basic.request.AccountRequest;
import com.csipro.test.basic.response.AccountResponse;
import com.csipro.test.basic.response.CommonResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
// import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/account")
public class AccountController {
  @Autowired public AccountRepository accountRepository;
  @Autowired public CustomerRepository customerRepository;

  @GetMapping()
  public CommonResponse getAccountList() {

    List<AccountEntity> accountEntityList = accountRepository.findAll();

    List<AccountResponse> accountResponseList = new ArrayList<>();
    for (AccountEntity accountEntity : accountEntityList) {
      AccountResponse accountResponse = new AccountResponse();
      accountResponse.setId(accountEntity.getId());
      accountResponse.setName(accountEntity.getName());
      accountResponse.setNumber(accountEntity.getNumber());
      accountResponse.setAmount(accountEntity.getAmount());
      accountResponseList.add(accountResponse);
    }

    return new CommonResponse("SUCCESS", accountResponseList);
  }

  @GetMapping("/{id}")
  public CommonResponse getAccount(@PathVariable("id") Integer id) {

    AccountEntity accountEntity = accountRepository.getById(id);
    if (accountEntity == null) {
      return new CommonResponse("ERROR.NOT_FOUND", "account not found.");
    }

    AccountResponse accountResponse = new AccountResponse();
    accountResponse.setId(accountEntity.getId());
    accountResponse.setName(accountEntity.getName());
    accountResponse.setNumber(accountEntity.getNumber());

    return new CommonResponse("SUCCESS", accountResponse);
  }

  @PutMapping("/{id}")
  public CommonResponse
  updateAccount(@PathVariable("id") Integer id,
                @RequestBody AccountRequest accountRequest) {
    AccountEntity accountEntity = accountRepository.getById(id);
    UUID uuid = UUID.randomUUID();

    CustomerEntity customerEntity =
        customerRepository.getById(accountEntity.getCutomerId());
    customerEntity.setAddress(accountRequest.getAddress());
    customerEntity.setName(accountRequest.getName());
    customerEntity.setPhone(uuid.toString());
    Date dob;
    try {
      dob = new SimpleDateFormat("dd-MM-yyyy").parse(accountRequest.getDob());
    } catch (ParseException e) {
      e.printStackTrace();
      return new CommonResponse("ERROR.NOT_UPDATE", "DOB. not found.");
    }
    customerEntity.setDob(dob);
    customerEntity.setEmail(accountRequest.getEmail());

    accountEntity.setName(accountRequest.getName());
    accountEntity.setNumber(uuid.toString());
    accountEntity.setAmount(accountRequest.getAmount());

    try {
      customerEntity.setAccount(accountEntity);
      accountEntity.setCustomer(customerEntity);
      customerRepository.save(customerEntity);
    } catch (Exception e) {
      e.printStackTrace();
      return new CommonResponse("ERROR.NOT_UPDATE", "account not save.");
    }
    return new CommonResponse("SUCCESS", "account id : " + id + " updated...");
  }

  @DeleteMapping("/{id}")
  public CommonResponse deleteAccount(@PathVariable("id") Integer id) {

    AccountEntity accountEntity = accountRepository.getById(id);
    if (accountEntity == null) {
      return new CommonResponse("ERROR.NOT_FOUND", "account not found.");
    }

    try {
      accountRepository.delete(accountEntity);
    } catch (Exception e) {
      return new CommonResponse("ERROR.NOT_DELETE",
                                "can not delete this account.");
    }

    return new CommonResponse("SUCCESS", null);
  }

  @PostMapping()
  public CommonResponse
  createAccount(@RequestBody AccountRequest accountRequest) {
    UUID uuid = UUID.randomUUID();

    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setAddress(accountRequest.getAddress());
    customerEntity.setName(accountRequest.getName());
    customerEntity.setPhone(uuid.toString());
    Date dob;
    try {
      dob = new SimpleDateFormat("dd-MM-yyyy").parse(accountRequest.getDob());
    } catch (ParseException e) {
      e.printStackTrace();
      return new CommonResponse("ERROR.NOT_SAVE", "DOB. not found.");
    }
    customerEntity.setDob(dob);
    customerEntity.setEmail(accountRequest.getEmail());

    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setName(accountRequest.getName());
    accountEntity.setNumber(uuid.toString());
    accountEntity.setAmount(accountRequest.getAmount());

    try {
      customerEntity.setAccount(accountEntity);
      accountEntity.setCustomer(customerEntity);
      customerRepository.save(customerEntity);
    } catch (Exception e) {
      e.printStackTrace();
      return new CommonResponse("ERROR.NOT_SAVE", "account not save.");
    }

    return new CommonResponse("SUCCESS", "account created.");
  }
}
