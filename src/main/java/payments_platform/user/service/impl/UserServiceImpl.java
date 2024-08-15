package payments_platform.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import payments_platform.transaction.model.Transaction;
import payments_platform.transaction.repository.TransactionRepository;
import payments_platform.user.dto.UserRequest;
import payments_platform.user.model.User;
import payments_platform.user.repository.UserRepository;
import payments_platform.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<String> registerUser(UserRequest data) {
        try {
            if(userRepository.findByEmail(data.email()) != null){
                return ResponseEntity.status(409).build();
            }
            
            var hash = passwordEncoder.encode(data.password());
            User user = new User(data);
            user.setPassword(hash);
            userRepository.save(user);

            return ResponseEntity.ok("Dados salvos no banco de dados");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            if(users.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);

            if(userOptional.isPresent()){
                User user = userOptional.get();

                return ResponseEntity.ok(user);
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<List<Transaction>> transactionsMade(){
        try {
            User user = getUserAuthenticate();

            if(user == null){
                return ResponseEntity.notFound().build();
            }

            List<Transaction> transactionsMade = transactionRepository.findBySenderId(user.getId());

            if(transactionsMade.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(transactionsMade);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<List<Transaction>> receivedTransactions(){
        try {

            User user = getUserAuthenticate();

            if(user == null){
                return ResponseEntity.notFound().build();
            }

            List<Transaction> receivedTransactions = transactionRepository.findByReceiverId(user.getId());

            if(receivedTransactions.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(receivedTransactions);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    private User getUserAuthenticate(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();

        return userRepository.findByEmail(email);
    }
    
}