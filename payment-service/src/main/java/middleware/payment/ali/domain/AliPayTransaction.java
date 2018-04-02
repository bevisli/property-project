package middleware.payment.ali.domain;

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
@Table(name = "ali_pay_transactions")
public class AliPayTransaction {
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
    @Column(name = "trade_no")
    public String tradeNo;

    @NotNull
    @Column(name = "total_amount")
    public Double totalAmount;

    @NotNull
    @Length(max = 50)
    @Column(name = "request_status")
    public String requestStatus;

    @NotEmpty
    @Length(max = 50)
    @Column(name = "trade_status")
    public String tradeStatus;

    @NotEmpty
    @Length(max = 50)
    @Column(name = "app_id")
    public String appId;

    @Length(max = 50)
    @Column(name = "buyer_id")
    public String buyerId;

    @Length(max = 50)
    @Column(name = "seller_id")
    public String sellerId;

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
