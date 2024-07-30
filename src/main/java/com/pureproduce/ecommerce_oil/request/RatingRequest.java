package com.pureproduce.ecommerce_oil.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {

    private Long productId;
    private Double rating;


}
