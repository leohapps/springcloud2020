package com.sohu.cms.springcloud.cloudcommon.api.payment;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloudcommon.api.payment.OrderPayment
 * @description 订单支付
 * @date 2020/12/24 4:33 下午
 */
public interface OrderPayment {

    String payment(Long orderId);
}
