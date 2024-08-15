package payments_platform.transaction.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import payments_platform.transaction.dto.TransactionRequest;
import payments_platform.transaction.model.Transaction;
import payments_platform.transaction.repository.TransactionRepository;
import payments_platform.transaction.service.TransactionService;
import payments_platform.user.model.User;
import payments_platform.user.repository.UserRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired 
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<String> performTransaction(TransactionRequest data) {
        try {

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long authenticatedUserId;

            if (principal instanceof UserDetails) {
                String email = ((UserDetails) principal).getUsername();
                authenticatedUserId = userRepository.findByEmail(email).getId();
            } else {
                authenticatedUserId = Long.parseLong(principal.toString());
            }

            if (!authenticatedUserId.equals(data.senderId())) {
                return ResponseEntity.status(403).body("Você não tem permissão para realizar esta transação.");
            }

            Optional<User> senderOptional = userRepository.findById(data.senderId());

            if(senderOptional.isPresent()){
                User sender = senderOptional.get();

                if(sender.getBalance().compareTo(data.amount()) >= 0){
                    Optional<User> receiverOptional = userRepository.findById(data.receiverId());

                    if(receiverOptional.isPresent()){
                        User receiver = receiverOptional.get();
                        
                        transactionRepository.save(new Transaction(data, sender, receiver));

                        return ResponseEntity.ok("Transação feita");
                    }

                    return ResponseEntity.status(404).body("Não foi possível realizar transação. Destinatário inexistente");
                }

                return ResponseEntity.status(409).body("Não foi possível realizar transação. Saldo insuficiente, seu saldo: " + sender.getBalance());
            }

            return ResponseEntity.status(404).body("Não foi possível realizar transação. Remetente inexistente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao realizar a transação");
        }
    }


    
}