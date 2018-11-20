package com.netcracker.edu.backend.controller;

import com.netcracker.edu.backend.entity.Account;
import com.netcracker.edu.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccountById(@PathVariable(name = "id") Integer id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.isPresent() ? ResponseEntity.ok(account.get()) : null;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccountByLogin(@RequestParam(name = "login") String login) {
        Optional<Account> account = accountService.getAccountByLogin(login);
        return account.isPresent() ? ResponseEntity.ok(account.get()) : ResponseEntity.ok(new Account());
    }
}
