package com.csipro.test.basic.controller;

import com.csipro.test.basic.entity.AccountEntity;
import com.csipro.test.basic.repository.AccountRepository;
import com.csipro.test.basic.request.AccountRequest;
import com.csipro.test.basic.response.AccountResponse;
import com.csipro.test.basic.response.CommonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/account")
public class AccountController {
    @Autowired
    public AccountRepository accountRepository;

    @GetMapping("/")
    public CommonResponse getAccountList() {

        List<AccountEntity> accountEntityList = accountRepository.findAll();

        List<AccountResponse> accountResponseList = new ArrayList<>();
        for (AccountEntity accountEntity: accountEntityList) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setId(accountEntity.getId());
            accountResponse.setName(accountEntity.getName());
            accountResponse.setNumber(accountEntity.getNumber());
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

    @PostMapping("/")
    public CommonResponse createAccount(@RequestBody AccountRequest accountRequest) {
        UUID uuid = UUID.randomUUID();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName(accountRequest.getName());
        accountEntity.setNumber(uuid.toString());
        accountEntity.setAmount(accountRequest.getAmount());

        accountRepository.save(accountEntity);

        return new CommonResponse("SUCCESS", "account created.");
    }
}
