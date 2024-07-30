package com.pureproduce.ecommerce_oil.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddItemRequest {

    private Long productId;
    private String size;
    private Integer quantity;
    private Integer price;

}
