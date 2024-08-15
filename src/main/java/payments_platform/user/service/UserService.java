package payments_platform.user.service;

import org.springframework.http.ResponseEntity;

import payments_platform.transaction.model.Transaction;
import payments_platform.user.dto.UserRequest;
import payments_platform.user.model.User;

import java.util.List;
public interface UserService {

    ResponseEntity<String> registerUser(UserRequest data);

    ResponseEntity<User> getUserById(Long id);

    ResponseEntity<List<User>> getAllUsers();

    ResponseEntity<List<Transaction>> transactionsMade();

    ResponseEntity<List<Transaction>> receivedTransactions();

}