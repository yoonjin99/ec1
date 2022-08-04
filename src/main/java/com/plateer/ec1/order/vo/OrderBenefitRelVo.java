package com.plateer.ec1.order.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@ToString
@Builder
public class OrderBenefitRelVo {
    private String ordNo;
    private Integer ordSeq;
    private Integer procSeq;
    private String ordBnfNo;
    private String aplyCnclCcd;
    private Long aplyAmt;
    private String clmNo;
    private Long prmNo;

    public static List<OrderBenefitRelVo> createGeneralData(OrderRequestVo orderRequest){
        List<OrderBenefitRelVo> opOrdBnfRelInfoModelList = new ArrayList<>();
        Pair<Map<String, Long>, List<OrderBenefitRelVo>> prdCouponList = prdCoupon(orderRequest);

        opOrdBnfRelInfoModelList.addAll(prdCouponList.getSecond());
        opOrdBnfRelInfoModelList.addAll(cartCoupon(orderRequest, prdCouponList.getFirst()));

        return opOrdBnfRelInfoModelList;
    }

    private static Pair<Map<String, Long>,List<OrderBenefitRelVo>> prdCoupon(OrderRequestVo orderRequest){
        Map<String, Long> priceMap = new HashMap<>();
        List<OrderBenefitRelVo> opOrdBnfRelInfoModelList = new ArrayList<>();

        for(OrdGoodsInfoVo goods : orderRequest.getOrdGoodsInfoVo()){ // 상품쿠폰
            Long price = goods.getPrmPrc() > 0 ? goods.getPrmPrc() : goods.getSalePrc();
            for(OrdBnfInfoVo bnf : goods.getOrdBnfInfoVo()){
                opOrdBnfRelInfoModelList.add(builderData(orderRequest, bnf.getPrmNo() ,bnf.getDcPrice(), 1));
                priceMap.put(goods.getOrdGoodsNo() + goods.getOrdItemNo(), price - bnf.getDcPrice()); // 상품결제금액
            }

            if(goods.getOrdBnfInfoVo().size() < 1){
                priceMap.put(goods.getOrdGoodsNo() + goods.getOrdItemNo(), price); // 상품결제금액
            }
        }
        return Pair.of(priceMap, opOrdBnfRelInfoModelList);
    }

    private static List<OrderBenefitRelVo> cartCoupon(OrderRequestVo orderRequest, Map<String, Long> priceMap){
        List<OrderBenefitRelVo> opOrdBnfRelInfoModelList = new ArrayList<>();
        int ordSeq = 1;
        for(OrdBnfInfoVo bnfInfoVo : orderRequest.getOrdBnfInfoVo()){ // 장바구니 쿠폰
            Long totalPrice = 0L;
            for(GoodsInfoVo goodsInfoVo : bnfInfoVo.getBnfApplyGoods()){ // 장바구니 쿠폰에 해당하는 상품 금액 총합
                if(priceMap.containsKey(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo())){
                    totalPrice += priceMap.get(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo());
                }
            }

            for(GoodsInfoVo goodsInfoVo : bnfInfoVo.getBnfApplyGoods()){
                if(priceMap.containsKey(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo())){
                    opOrdBnfRelInfoModelList.add(builderData(orderRequest, bnfInfoVo.getPrmNo(),bnfInfoVo.getDcPrice() * (priceMap.get(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo()) / totalPrice), ordSeq)); // 총혜택금액 * (상품결제금액 / 총결제금액)
                }
            }
            ordSeq++;
        }
        return opOrdBnfRelInfoModelList;
    }

    private static OrderBenefitRelVo builderData(OrderRequestVo vo,Long prmNo, Long price, int ordSeq){
        return OrderBenefitRelVo.builder()
                .ordNo(vo.getOrdNo())
                .prmNo(prmNo)
                .ordSeq(ordSeq)
                .procSeq(1)
                .aplyCnclCcd("10")
                .aplyAmt(price)
                .build();
    }
}
