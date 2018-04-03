package app.payment.wx.service.pay;

import app.payment.api.wx.pay.NativePayRequest;
import app.payment.api.wx.query.PayTransactionResponse;
import app.payment.wx.domain.WXPayTransaction;
import app.payment.wx.service.api.NotifyResponse;
import app.payment.wx.service.api.PaymentQueryResponse;
import app.payment.wx.service.query.PaymentQueryService;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mort
 */
public class PayTransactionService {
    @Inject
    Repository<WXPayTransaction> payTransactionRepository;
    @Inject
    PaymentQueryService paymentQueryService;

    public PayTransactionResponse payTransaction(String id) {
        WXPayTransaction transaction = get(id);
        PayTransactionResponse response = new PayTransactionResponse();
        response.orderId = transaction.orderId;
        response.paymentId = transaction.paymentId;
        response.userId = transaction.userId;
        response.tradeNo = transaction.transactionId;
        response.totalAmount = transaction.totalAmount;
        response.createdTime = transaction.createdTime;
        response.completedTime = transaction.completedTime;
        response.tradeStatus = transaction.tradeStatus;
        return response;
    }

    public WXPayTransaction addWXPayTransaction(String id, NativePayRequest request) {
        WXPayTransaction transaction = new WXPayTransaction();
        transaction.id = id;
        transaction.orderId = request.orderId;
        transaction.paymentId = request.paymentId;
        transaction.userId = request.userId;
        transaction.totalAmount = request.totalAmount;
        transaction.createdTime = LocalDateTime.now();
        transaction.createdBy = request.requestedBy;
        payTransactionRepository.insert(transaction);
        return transaction;
    }

    void requestCompleted(WXPayTransaction transaction, String prepayId) {
        transaction.requestStatus = "COMPLETED";
        transaction.prepayId = prepayId;
        payTransactionRepository.update(transaction);
    }

    void requestFailed(WXPayTransaction transaction, String errorCode) {
        transaction.requestStatus = errorCode;
        payTransactionRepository.update(transaction);
    }

    WXPayTransaction get(String id) {
        return payTransactionRepository.get(id)
            .orElseThrow(() -> new NotFoundException("wx pay transaction not found, id=" + id));
    }

    WXPayTransaction updateWXPayTransaction(NotifyResponse notification, String requestedBy) {
        WXPayTransaction transaction = get(notification.outTradeNo);
        transaction.appId = notification.appId;
        transaction.openId = notification.openId;
        transaction.merchantId = notification.merchantId;
        transaction.transactionId = notification.transactionId;
        transaction.tradeType = notification.tradeType;
        transaction.bankType = notification.bankType;
        transaction.cashAmount = notification.cashFee;
        transaction.errorCode = notification.errorCode;
        transaction.completedTime = LocalDateTime.now();
        transaction.tradeStatus = notification.returnOK() && notification.resultOK() ? "COMPLETED" : "FAILED";
        transaction.updatedBy = requestedBy;
        transaction.updatedTime = LocalDateTime.now();
        payTransactionRepository.update(transaction);
        return transaction;
    }

    public void syncPaymentTransactions(String requestedBy) {
        List<WXPayTransaction> transactions = payTransactionRepository.select("request_status = `COMPLETED` AND trade_no IS NULL");
        transactions.forEach(transaction -> {
            PaymentQueryResponse queryResponse = paymentQueryService.query(transaction.id);
            transaction.appId = queryResponse.appId;
            transaction.openId = queryResponse.openId;
            transaction.merchantId = queryResponse.merchantId;
            transaction.transactionId = queryResponse.transactionId;
            transaction.tradeType = queryResponse.tradeType;
            transaction.bankType = queryResponse.bankType;
            transaction.cashAmount = queryResponse.cashFee;
            transaction.errorCode = queryResponse.errorCode;
            transaction.completedTime = LocalDateTime.now();
            transaction.tradeStatus = queryResponse.returnOK() && queryResponse.resultOK() ? "COMPLETED" : "FAILED";
            transaction.updatedBy = requestedBy;
            transaction.updatedTime = LocalDateTime.now();
            payTransactionRepository.update(transaction);
        });
    }
}
