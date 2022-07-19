package com.plateer.ec1.payment.vo;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import lombok.Data;

@Data
public class CancelInfoVo {
    private OpPayInfoModel opPayInfoModel;
    private String ordNm;
    private String rfndAcctNo;
    private String rfndAcctOwnNm;
    private String rfndBnkCk;
    private String goodsNm;
}

