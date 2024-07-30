package com.pureproduce.ecommerce_oil.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentLinkResponse {

    private String payment_link_url;
    private String payment_link_id;

}
