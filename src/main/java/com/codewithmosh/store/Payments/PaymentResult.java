package com.codewithmosh.store.Payments;

import com.codewithmosh.store.Payments.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;   //here we renamed OrderStatus Enum to PaymentStatus.
    /**
     * now in the PaymentGateway lets define a method which takes a WebhookRequest and return a PaymentResult.
     */
}
