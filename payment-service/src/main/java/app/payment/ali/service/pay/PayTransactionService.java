package app.payment.ali.service.pay;

import app.payment.ali.domain.AliPayTransaction;
import app.payment.ali.service.api.NotifyResponse;
import app.payment.ali.service.query.PayQueryService;
import app.payment.api.ali.pay.PagePayRequest;
import app.payment.api.ali.query.PayTransactionResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author mort
 */
public class PayTransactionService {
    @Inject
    Repository<AliPayTransaction> aliPayTransactionRepository;
    @Inject
    PayQueryService payQueryService;

    public PayTransactionResponse payTransaction(String id) {
        AliPayTransaction transaction = get(id);
        PayTransactionResponse response = new PayTransactionResponse();
        response.orderId = transaction.orderId;
        response.paymentId = transaction.paymentId;
        response.userId = transaction.userId;
        response.totalAmount = transaction.totalAmount;
        response.tradeNo = transaction.tradeNo;
        response.tradeStatus = transaction.tradeStatus;
        response.createdTime = transaction.createdTime;
        response.completedTime = transaction.completedTime;
        return response;
    }

    AliPayTransaction addAliPayTransaction(PagePayRequest payRequest) {
        AliPayTransaction transaction = new AliPayTransaction();
        transaction.id = UUID.randomUUID().toString().replace("-", "");
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

    AliPayTransaction get(String id) {
        return aliPayTransactionRepository.get(id).orElseThrow(() -> new NotFoundException("ali pay transaction not found, id=" + id));
    }

    void requestCompleted(AliPayTransaction transaction) {
        transaction.requestStatus = "COMPLETED";
        aliPayTransactionRepository.update(transaction);
    }

    void requestFailed(AliPayTransaction transaction, String errorCode) {
        transaction.requestStatus = errorCode;
        aliPayTransactionRepository.update(transaction);
    }

    AliPayTransaction updateAliPayTransaction(NotifyResponse notify, String requestedBy) {
        AliPayTransaction transaction = get(notify.outTradeNo);
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

    public void syncPaymentTransactions(String requestedBy) {
        List<AliPayTransaction> transactions = aliPayTransactionRepository.select("request_status = `COMPLETED` AND trade_no IS NULL");
        transactions.forEach(transaction -> {
            AlipayTradeQueryResponse queryResponse = payQueryService.query(transaction.id);
            transaction.buyerId = queryResponse.getBuyerUserId();
            transaction.tradeNo = queryResponse.getTradeNo();
            transaction.tradeStatus = queryResponse.getTradeStatus();
            transaction.completedTime = LocalDateTime.now();
            transaction.updatedTime = LocalDateTime.now();
            transaction.updatedBy = requestedBy;
            aliPayTransactionRepository.update(transaction);
        });
    }
}
