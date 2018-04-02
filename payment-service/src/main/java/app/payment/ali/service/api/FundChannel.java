package app.payment.ali.service.api;

import jdk.nashorn.internal.objects.annotations.Property;

/**
 * @author mort
 */
public enum FundChannel {
    //支付宝红包
    @Property(name = "COUPON")
    COUPON,

    //支付宝余额
    @Property(name = "ALIPAYACCOUNT")
    ALIPAYACCOUNT,

    //集分宝
    @Property(name = "POINT")
    POINT,

    //折扣券
    @Property(name = "DISCOUNT")
    DISCOUNT,

    //预付卡
    @Property(name = "PCARD")
    PCARD,

    //余额宝
    @Property(name = "FINANCEACCOUNT")
    FINANCEACCOUNT,

    //商家储值卡
    @Property(name = "MCARD")
    MCARD,

    //商户优惠券
    @Property(name = "MDISCOUNT")
    MDISCOUNT,

    //商户红包
    @Property(name = "MCOUPON")
    MCOUPON,

    //蚂蚁花呗
    @Property(name = "PCREDIT")
    PCREDIT
}
