package middleware.payment.wx.domain;

import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author mort
 */
@Table(name = "wx_pay_transactions")
public class WXPayTransaction {
    @PrimaryKey
    @Column(name = "id")
    public String id;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Column(name = "order_id")
    public String orderId;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Column(name = "payment_id")
    public String paymentId;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Column(name = "user_id")
    public String userId;

    @NotEmpty
    @Length(max = 100)
    @Column(name = "prepay_id")
    public String prepayId;

    @NotEmpty
    @Length(max = 100)
    @Column(name = "transaction_id")
    public String transactionId;

    @NotNull
    @Column(name = "total_amount")
    public Double totalAmount;

    @NotEmpty
    @Length(max = 150)
    @Column(name = "open_id")
    public String openId;

    @NotEmpty
    @Length(max = 50)
    @Column(name = "app_id")
    public String appId;

    @NotEmpty
    @Length(max = 50)
    @Column(name = "merchant_id")
    public String merchantId;

    @NotEmpty
    @Length(max = 50)
    @Column(name = "trade_type")
    public String tradeType;

    @NotEmpty
    @Length(max = 50)
    @Column(name = "trade_status")
    public String tradeStatus;

    @NotEmpty
    @Length(max = 150)
    @Column(name = "error_code")
    public String errorCode;

    @NotEmpty
    @Length(max = 50)
    @Column(name = "bank_type")
    public String bankType;

    @Column(name = "cash_amount")
    public Double cashAmount;

    @Column(name = "completed_time")
    public LocalDateTime completedTime;

    @NotNull
    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @NotNull
    @Column(name = "created_by")
    public String createdBy;

    @Column(name = "updated_time")
    public LocalDateTime updatedTime;

    @Column(name = "updated_by")
    public String updatedBy;
}
