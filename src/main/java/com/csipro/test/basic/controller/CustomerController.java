package com.csipro.test.basic.controller;

import com.csipro.test.basic.entity.AccountEntity;
import com.csipro.test.basic.entity.CustomerEntity;
// import com.csipro.test.basic.fileupload.FileUploadApplication;
import com.csipro.test.basic.repository.AccountRepository;
import com.csipro.test.basic.repository.CustomerRepository;
import com.csipro.test.basic.request.AccountRequest;
import com.csipro.test.basic.request.CustomerRequest;
import com.csipro.test.basic.response.CommonResponse;
import com.csipro.test.basic.response.CustomerResponse;
import com.csipro.test.basic.service.FileStorageService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
// import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
  @Autowired public CustomerRepository customerRepository;
  @Autowired public AccountRepository accountRepository;
  @Autowired private FileStorageService fileStorageService;

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

  @PatchMapping("/{id}")
  public CommonResponse
  updateAccount(@PathVariable("id") Integer id,
                @RequestBody AccountRequest accountRequest) {
    CustomerEntity customerEntity = customerRepository.getById(id);
    UUID uuid = UUID.randomUUID();

    AccountEntity accountEntity = customerEntity.getAccount();
    customerEntity.setAddress(accountRequest.getAddress());
    customerEntity.setName(accountRequest.getName());
    customerEntity.setPhone(accountRequest.getPhone());
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

  @PostMapping("/upload/{id}")
  public CommonResponse uploadFile(@PathVariable("id") Integer id,
                                   @RequestParam("file") MultipartFile file) {
    CustomerEntity customerEntity = customerRepository.getById(id);
    try {
      String fileName = fileStorageService.storeFile(file);
      String fileDownloadUri =
          ServletUriComponentsBuilder.fromCurrentContextPath()
              .path("/customer/downloadFile/")
              .path(fileName)
              .toUriString();
      customerEntity.setDownloadFile(fileDownloadUri);

      customerRepository.save(customerEntity);
      return new CommonResponse("SUCCESS", fileDownloadUri);
    } catch (Exception e) {
      return new CommonResponse("ERROR.BAD_REQUEST", e);
    }
  }

  @PostMapping()
  public CommonResponse
  createAccount(@RequestBody CustomerRequest customerRequest) {
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setAddress(customerRequest.getAddress());
    customerEntity.setName(customerRequest.getName());
    customerEntity.setPhone(customerEntity.getPhone());
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

  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
                                               HttpServletRequest request)
      throws Exception {
    // Load file as Resource
    Resource resource = fileStorageService.loadFileAsResource(fileName);

    // Try to determine file's content type
    String contentType = null;
    try {
      contentType = request.getServletContext().getMimeType(
          resource.getFile().getAbsolutePath());
    } catch (IOException ex) {
      throw new Exception("Error : ", ex);
    }

    // Fallback to the default content type if type could not be determined
    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }
}
