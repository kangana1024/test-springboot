package com.csipro.test.basic.controller;

import com.csipro.test.basic.entity.CustomerEntity;
import com.csipro.test.basic.repository.CustomerRepository;
import com.csipro.test.basic.request.CustomerRequest;
import com.csipro.test.basic.response.CommonResponse;
import com.csipro.test.basic.response.CustomerResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
// import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
  @Autowired public CustomerRepository customerRepository;

  @GetMapping()
  public CommonResponse getAccountList() {

    List<CustomerEntity> customerEntityLists = customerRepository.findAll();

    List<CustomerResponse> customerResponseList = new ArrayList<>();
    for (CustomerEntity customerEntity : customerEntityLists) {
      CustomerResponse customerResponse = new CustomerResponse();
      customerResponse.setId(customerEntity.getId());
      customerResponse.setName(customerEntity.getName());
      customerResponse.setPhone(customerEntity.getPhone());
      customerResponse.setAddress(customerEntity.getAddress());
      customerResponse.setEmail(customerEntity.getEmail());
      customerResponse.setAccount(customerEntity.getAccount());
      customerResponse.setDob(customerEntity.getDob());
      customerResponseList.add(customerResponse);
    }

    return new CommonResponse("SUCCESS", customerResponseList);
  }

  @GetMapping("/{id}")
  public CommonResponse getAccount(@PathVariable("id") Integer id) {

    CustomerEntity customerEntity = customerRepository.getById(id);
    if (customerEntity == null) {
      return new CommonResponse("ERROR.NOT_FOUND", "account not found.");
    }

    CustomerResponse customerResponse = new CustomerResponse();
    customerResponse.setId(customerEntity.getId());
    customerResponse.setName(customerEntity.getName());
    customerResponse.setPhone(customerEntity.getPhone());
    customerResponse.setAddress(customerEntity.getAddress());
    customerResponse.setEmail(customerEntity.getEmail());
    customerResponse.setAccount(customerEntity.getAccount());
    customerResponse.setDob(customerEntity.getDob());

    return new CommonResponse("SUCCESS", customerResponse);
  }

  @PostMapping()
  public CommonResponse
  createAccount(@RequestBody CustomerRequest customerRequest) {
    UUID uuid = UUID.randomUUID();
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setAddress(customerRequest.getAddress());
    customerEntity.setName(customerRequest.getName());
    customerEntity.setPhone(uuid.toString());
    customerEntity.setEmail(customerRequest.getEmail());
    Date dob;
    try {
      dob = new SimpleDateFormat("dd-MM-yyyy").parse(customerRequest.getDob());
    } catch (ParseException e) {
      e.printStackTrace();
      return new CommonResponse("ERROR.NOT_FOUND", "account not found.");
    }
    customerEntity.setDob(dob);

    customerRepository.save(customerEntity);

    return new CommonResponse("SUCCESS", "account created.");
  }
}
