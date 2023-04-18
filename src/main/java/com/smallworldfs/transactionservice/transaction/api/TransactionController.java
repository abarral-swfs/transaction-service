package com.smallworldfs.transactionservice.transaction.api;

import com.smallworldfs.transactionservice.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    @GetMapping("/{id}")
    public void getTransaction(@PathVariable Integer id) {
        service.getTransaction(id);
    }

}
