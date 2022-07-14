package com.plateer.ec1.common.code.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OPT0009Code {
    VRACCOUNT("10"),
    POINT("20");
    
    private String type;
    
    public String getType(){
        return type;
    }
}
