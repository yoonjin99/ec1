package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.enums.history.OPT0012Type;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Setter
@Builder
public class OrderVo {
    private OPT0012Type procCcd;
    // 주문기본 테이블
    private OpOrdBaseModel opOrdBaseModel;
    // 주문상품 테이블
    private List<OpGoodsInfo> opGoodsInfoList;
    // 주문클레임 테이블
    private List<OpClmInfoModel> opClmInfoModelList;
    // 주문배송지
    private List<OpDvpAreaInfo> opDvpAreaInfo;
    // 주문배송정보
    private List<OpDvpInfo> opDvpInfoList;
    // 주문비용 테이블
    private List<OpOrdCostInfoModel> opOrdCostInfoModelList;
    // 주문혜택 테이블
    private List<OpOrdBnfInfoModel> opOrdBnfInfoModelList;
    // 주문혜택 관계 테이블
    private List<OpOrdBnfRelInfoModel> opOrdBnfRelInfoModelList;
}
