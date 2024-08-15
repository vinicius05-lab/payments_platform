package payments_platform.transaction.service;

import org.springframework.http.ResponseEntity;

import payments_platform.transaction.dto.TransactionRequest;

public interface TransactionService {
    
    ResponseEntity<String> performTransaction(TransactionRequest data);
}
