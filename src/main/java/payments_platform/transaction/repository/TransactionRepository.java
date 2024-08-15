package payments_platform.transaction.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import payments_platform.transaction.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
    List<Transaction> findBySenderId(Long id);

    List<Transaction> findByReceiverId(Long id);
}
