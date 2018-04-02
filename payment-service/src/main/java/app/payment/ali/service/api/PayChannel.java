package app.payment.ali.service.api;

/**
 * @author mort
 */
public class PayChannel {
    //余额
    public static final String BALANCE = "balance";

    //余额宝
    public static final String MONEY_FUND = "moneyFund";

    //红包
    public static final String COUPON = "coupon";

    //花呗
    public static final String PCREDIT = "pcredit";

    //花呗分期
    public static final String PCREDIT_PAY_INSTALLMENT = "pcreditpayInstallment";

    //信用卡
    public static final String CREDIT_CARD = "creditCard";

    //信用卡快捷
    public static final String CREDIT_CARD_EXPRESS = "creditCardExpress";

    //信用卡卡通
    public static final String CREDIT_CARD_CARTOON = "creditCardCartoon";

    //信用支付类型（包含信用卡卡通、信用卡快捷、花呗、花呗分期）
    public static final String CREDIT_GROUP = "credit_group";

    //借记卡快捷
    public static final String DEBIT_CARD_EXPRESS = "debitCardExpress";

    //商户预存卡
    public static final String MCARD = "mcard";

    //个人预存卡
    public static final String PCARD = "pcard";

    //优惠（包含实时优惠+商户优惠）
    public static final String PROMOTION = "promotion";

    //营销券
    public static final String VOUCHER = "voucher";

    //积分
    public static final String POINT = "point";

    //商户优惠
    public static final String MDISCOUNT = "mdiscount";

    //网银
    public static final String BANK_PAY = "bankPay";
}
