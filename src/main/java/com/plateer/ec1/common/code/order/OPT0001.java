package com.plateer.ec1.common.code.order;

public enum OPT0001 {
    GENERAL("10"),
    ECOUPON("20");

    OPT0001(String type) {
        this.type = type;
    }

    public String type;

    public String getType(){
        return type;
    }
}
