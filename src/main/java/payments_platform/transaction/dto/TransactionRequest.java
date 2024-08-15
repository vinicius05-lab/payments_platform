package payments_platform.transaction.dto;

import java.math.BigDecimal;

public record TransactionRequest(
    BigDecimal amount,

    Long senderId,

    Long receiverId
) {}