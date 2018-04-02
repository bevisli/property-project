package app.payment.ali.service.pay;

import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.NotFoundException;
import app.payment.ali.domain.AliPayTransaction;
import app.payment.ali.service.api.NotifyResponse;
import app.payment.api.ali.pay.PagePayRequest;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author mort
 */
public class TransactionService {
    @Inject
    Repository<AliPayTransaction> aliPayTransactionRepository;

    public AliPayTransaction addAliPayTransaction(PagePayRequest payRequest) {
        AliPayTransaction transaction = new AliPayTransaction();
        transaction.id = UUID.randomUUID().toString();
        transaction.orderId = payRequest.orderId;
        transaction.paymentId = payRequest.paymentId;
        transaction.userId = payRequest.userId;
        transaction.totalAmount = payRequest.amount;
        transaction.requestStatus = "INIT";
        transaction.createdTime = LocalDateTime.now();
        transaction.createdBy = payRequest.requestedBy;
        aliPayTransactionRepository.insert(transaction);
        return transaction;
    }

    void requestCompleted(AliPayTransaction transaction) {
        transaction.requestStatus = "COMPLETED";
        aliPayTransactionRepository.update(transaction);
    }

    void requestFailed(AliPayTransaction transaction, String errorCode) {
        transaction.requestStatus = errorCode;
        aliPayTransactionRepository.update(transaction);
    }

    public AliPayTransaction updateAliPayTransaction(NotifyResponse notify, String requestedBy) {
        String transactionId = notify.passBackParams;
        AliPayTransaction transaction = aliPayTransactionRepository.get(transactionId)
            .orElseThrow(() -> new NotFoundException("ali pay transaction not found, transactionId=" + transactionId));
        transaction.appId = notify.appId;
        transaction.buyerId = notify.buyerId;
        transaction.tradeNo = notify.tradeNo;
        transaction.tradeStatus = notify.tradeStatus;
        transaction.sellerId = notify.sellerId;
        transaction.completedTime = LocalDateTime.now();
        transaction.updatedTime = LocalDateTime.now();
        transaction.updatedBy = requestedBy;
        aliPayTransactionRepository.update(transaction);
        return transaction;
    }
}
