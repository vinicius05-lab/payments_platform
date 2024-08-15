package payments_platform.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import payments_platform.transaction.model.Transaction;
import payments_platform.user.dto.UserRequest;
import payments_platform.user.model.User;
import payments_platform.user.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRequest data) {
        return userService.registerUser(data);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/my-transactions")
    ResponseEntity<List<Transaction>> transactionsMade(){
        return userService.transactionsMade();
    }

    @GetMapping("/received-transactions")
    ResponseEntity<List<Transaction>> receivedTransactions(){
        return userService.receivedTransactions();
    }

}