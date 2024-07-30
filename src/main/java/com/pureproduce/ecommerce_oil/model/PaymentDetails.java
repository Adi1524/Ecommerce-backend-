package com.pureproduce.ecommerce_oil.model;

import com.pureproduce.ecommerce_oil.user.domain.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetails {

    private String paymentMethod;
    private PaymentStatus status;
    private String paymentId;
    private String razorPaymentLinkld;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;
}
