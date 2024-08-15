package payments_platform.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import payments_platform.transaction.dto.TransactionRequest;
import payments_platform.transaction.service.TransactionService;
@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    
    @PostMapping("/transactions")
    public ResponseEntity<String> performTransaction(@RequestBody @Valid TransactionRequest data) {
        return transactionService.performTransaction(data);
    }

}
