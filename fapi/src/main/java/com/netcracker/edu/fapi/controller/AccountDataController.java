package com.netcracker.edu.fapi.controller;

import com.netcracker.edu.fapi.models.AccountViewModel;
import com.netcracker.edu.fapi.service.AccountDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ba-account")
public class AccountDataController {

    private AccountDataService accountDataService;

    @Autowired
    public AccountDataController(AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AccountViewModel> getAccountById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(accountDataService.getAccountById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<AccountViewModel>> getAllAccounts() {
        return ResponseEntity.ok(accountDataService.getAllAccounts());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<AccountViewModel> saveAccount(AccountViewModel account) {
        return ResponseEntity.ok(accountDataService.saveAccount(account));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAccount(@PathVariable(name = "id") Integer id) {
        accountDataService.deleteAccount(id);
    }

}
