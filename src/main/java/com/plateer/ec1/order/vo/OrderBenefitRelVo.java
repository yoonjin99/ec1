package com.plateer.ec1.order.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

import java.util.*;


@Getter
@Setter
@ToString
@Builder
@Slf4j
public class OrderBenefitRelVo {
    private String ordNo;
    private Integer ordSeq;
    private Integer procSeq;
    private String ordBnfNo;
    private String aplyCnclCcd;
    private Long aplyAmt;
    private String clmNo;
    private Long cpnIssNo;

    public static List<OrderBenefitRelVo> createGeneralData(OrdGoodsInfoVo goodsInfoVo, OrderRequestVo orderRequest, int cnt){
        List<OrderBenefitRelVo> opOrdBnfRelInfoModelList = new ArrayList<>();
        Pair<Map<String, Long>, List<OrderBenefitRelVo>> prdCouponList = prdCoupon(goodsInfoVo, orderRequest.getOrdNo(), cnt);

        opOrdBnfRelInfoModelList.addAll(prdCouponList.getSecond());
        opOrdBnfRelInfoModelList.addAll(cartCoupon(orderRequest, goodsInfoVo, prdCouponList.getFirst(), cnt));

        return opOrdBnfRelInfoModelList;
    }

    private static Pair<Map<String, Long>,List<OrderBenefitRelVo>> prdCoupon(OrdGoodsInfoVo goodsInfoVo, String ordNo, int cnt){
        Map<String, Long> priceMap = new HashMap<>();
        List<OrderBenefitRelVo> opOrdBnfRelInfoModelList = new ArrayList<>();

        Long price = goodsInfoVo.getPrmPrc() > 0 ? goodsInfoVo.getPrmPrc() : goodsInfoVo.getSalePrc();

        priceMap.put(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo(), price);

        if(!Objects.isNull(goodsInfoVo.getOrdBnfInfoVo())){
            for(OrdBnfInfoVo bnf : goodsInfoVo.getOrdBnfInfoVo()){
                opOrdBnfRelInfoModelList.add(OrderBenefitRelVo.builderData(ordNo, bnf.getCpnIssNo() ,bnf.getDcPrice(), cnt));
                priceMap.put(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo(), price - bnf.getDcPrice()); // 상품결제금액
            }
        }

        return Pair.of(priceMap, opOrdBnfRelInfoModelList);
    }

    private static List<OrderBenefitRelVo> cartCoupon(OrderRequestVo orderRequest, OrdGoodsInfoVo goodsInfoVo, Map<String, Long> priceMap, int cnt){
        List<OrderBenefitRelVo> opOrdBnfRelInfoModelList = new ArrayList<>();
        if(!Objects.isNull(orderRequest.getOrdBnfInfoVo())){
            for(OrdBnfInfoVo bnfInfoVo : orderRequest.getOrdBnfInfoVo()){
                // 장바구니 쿠폰
                Long totalPrice = 0L; // todo :  long type x
                for(GoodsInfoVo bnfGoods : bnfInfoVo.getBnfApplyGoods()){ // 장바구니 쿠폰에 해당하는 상품 금액 총합
                    if(priceMap.containsKey(bnfGoods.getOrdGoodsNo() + bnfGoods.getOrdItemNo())){
                        totalPrice += priceMap.get(bnfGoods.getOrdGoodsNo() + bnfGoods.getOrdItemNo());
                    }
                }

                for(GoodsInfoVo vo : bnfInfoVo.getBnfApplyGoods()){
                    if(vo.getOrdGoodsNo().equals(goodsInfoVo.getOrdGoodsNo()) && vo.getOrdItemNo().equals(goodsInfoVo.getOrdItemNo())){
                        if(priceMap.containsKey(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo())){
                            opOrdBnfRelInfoModelList.add(OrderBenefitRelVo.builderData(orderRequest.getOrdNo(), bnfInfoVo.getCpnIssNo(),bnfInfoVo.getDcPrice() * (priceMap.get(goodsInfoVo.getOrdGoodsNo() + goodsInfoVo.getOrdItemNo()) / totalPrice), cnt)); // 총혜택금액 * (상품결제금액 / 총결제금액)
                        }
                    }
                }
            }
        }

        return opOrdBnfRelInfoModelList;
    }

    public static OrderBenefitRelVo builderData(String ordNo, Long cpnIssNo, Long price, int ordSeq){
        return OrderBenefitRelVo.builder()
                .ordNo(ordNo)
                .cpnIssNo(cpnIssNo)
                .ordSeq(ordSeq)
                .procSeq(1)
                .aplyCnclCcd("10")
                .aplyAmt(price)
                .build();
    }
}
