package com.pureproduce.ecommerce_oil.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PaymentInformation {

    private String paymentMethod;
    private String cardNumber;

}

