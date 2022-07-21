package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpOrdBaseModel;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestVo {
    private String orderType;
    private String systemType;
    private Long price;
    private String productId;
    private String orderNo;
    private String paymentType;

    private String ordNo;
    private OrdBaseVo ordBaseVo;
    private List<OrdGoodsInfoVo> ordGoodsInfoVo;
    private List<OrdBnfInfoVo> ordBnfInfoVo;
    private List<OrdDvpAreaInfoVo> ordDvpAreaInfoVo;

}
