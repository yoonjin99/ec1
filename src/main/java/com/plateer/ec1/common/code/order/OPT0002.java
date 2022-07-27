package com.plateer.ec1.common.code.order;

public enum OPT0002 {
    FO("10"),
    BO("20");

    OPT0002(String type) {
        this.type = type;
    }

    public String type;

    public String getType(){
        return type;
    }
}
