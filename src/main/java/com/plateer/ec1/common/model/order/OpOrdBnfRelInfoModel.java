package com.plateer.ec1.common.model.order;

import com.plateer.ec1.order.vo.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class OpOrdBnfRelInfoModel {
    private String ordNo;
    private Integer ordSeq;
    private Integer procSeq;
    private String ordBnfNo;
    private String aplyCnclCcd;
    private Long aplyAmt;
    private String clmNo;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;

    // TODO : 주문혜택 배분처리 다시 개발
    public static List<OpOrdBnfRelInfoModel> createGeneralData(OrderRequestVo orderRequest){

        Map<String, Long> priceMap = new HashMap<>();
        List<OpOrdBnfRelInfoModel> opOrdBnfRelInfoModelList = new ArrayList<>();

        for(OrdGoodsInfoVo goods : orderRequest.getOrdGoodsInfoVo()){ // 상품쿠폰
            Long price = goods.getPrmPrc() > 0 ? goods.getPrmPrc() : goods.getSalePrc();
            for(OrdBnfInfoVo bnf : goods.getOrdBnfInfoVo()){
                OpOrdBnfRelInfoModel opOrdBnfRelInfoModel = OpOrdBnfRelInfoModel.builder()
                        .aplyCnclCcd("10")
                        .aplyAmt(bnf.getDcPrice()) //할인금액도 넘어옴
                        .build();
                opOrdBnfRelInfoModelList.add(opOrdBnfRelInfoModel);
                priceMap.put(goods.getOrdGoodsNo() + goods.getOrdItemNo(), price - bnf.getDcPrice()); // 상품결제금액
            }

            if(goods.getOrdBnfInfoVo().size() < 1){
                priceMap.put(goods.getOrdGoodsNo() + goods.getOrdItemNo(), price); // 상품결제금액
            }
        }

        for(OrdBnfInfoVo bnfInfoVo : orderRequest.getOrdBnfInfoVo()){ // 장바구니 쿠폰
            Long totalPrice = 0L;
            for(GoodsInfoVo goodsInfoVo : bnfInfoVo.getBnfApplyGoods()){ // 장바구니 쿠폰에 해당하는 상품 금액 총합
                if(priceMap.containsKey(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo())){
                    totalPrice += priceMap.get(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo());
                }
            }

            for(GoodsInfoVo goodsInfoVo : bnfInfoVo.getBnfApplyGoods()){
                if(priceMap.containsKey(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo())){
                    OpOrdBnfRelInfoModel opOrdBnfRelInfoModel = OpOrdBnfRelInfoModel.builder()
                            .aplyCnclCcd("10")
                            .aplyAmt(bnfInfoVo.getDcPrice() * (priceMap.get(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo()) / totalPrice)) // 총혜택금액 * (상품결제금액 / 총결제금액)
                            .build();
                    opOrdBnfRelInfoModelList.add(opOrdBnfRelInfoModel);
                }
            }
        }

        return opOrdBnfRelInfoModelList;
    }

}
