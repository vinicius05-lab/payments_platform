package payments_platform.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import payments_platform.transaction.dto.TransactionRequest;
import payments_platform.user.model.User;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    public Transaction(TransactionRequest data, User sender, User receiver){
        this.amount = data.amount();
        this.sender = sender;
        this.receiver = receiver;
        this.transactionDate = LocalDateTime.now();
        
        this.sender.setBalance(this.sender.getBalance().subtract(amount));
        this.receiver.setBalance(this.receiver.getBalance().add(amount));
    }
}
