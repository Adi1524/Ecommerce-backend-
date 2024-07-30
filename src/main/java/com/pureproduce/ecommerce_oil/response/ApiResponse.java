package com.pureproduce.ecommerce_oil.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private boolean status;
    
    public ApiResponse(String message, boolean status){
        super();
        this.message= message;
        this.status= status;
    }

    

}
