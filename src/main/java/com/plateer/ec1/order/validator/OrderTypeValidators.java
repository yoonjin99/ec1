package com.plateer.ec1.order.validator;

import com.plateer.ec1.common.model.product.PrGoodsBaseModel;
import com.plateer.ec1.order.vo.OrderValidationVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderTypeValidators {

    // 이쿠폰상품인지 확인
    public static Predicate<OrderValidationVo> isEcouponProduct = (vo) -> {
        log.info("OrderTypeValidators 모바일 쿠폰 상품확인 : {}", vo);
        return vo.getPrGoodsBaseModel().stream().allMatch(goodsBaseModel -> goodsBaseModel.getGoodsTpCd().equals("20"));
    };

    // 모바일 쿠폰 필수 데이터 확인
    public static Predicate<OrderValidationVo> isEcouponValid = (vo) -> {
        log.info("OrderTypeValidators 모바일 쿠폰 필수 데이터 확인: {}", vo);
        // 배송지, 휴대폰 번호가 있는지
        return vo.getOrderRequestVo().getOrdDvpAreaInfoVo()
                .stream()
                .noneMatch(infoVo -> infoVo.getRmtiAddr().equals("") || infoVo.getRmtiHpNo().equals(""));
    };

    public static Predicate<OrderValidationVo> isEcouponOrdDvpValid = (vo) -> {
        log.info("OrderTypeValidators 모바일 쿠폰 필수 데이터 확인: {}", vo);
        return vo.getOrderRequestVo().getOrdDvpAreaInfoVo().size() == 1
                || vo.getOrderRequestVo().getOrdDvpAreaInfoVo().size() == vo.getOrderRequestVo().getOrdGoodsInfoVo().get(0).getOrdCnt();
    };

    // 일반 상품에 일반 배송 상품인지 확인
    public static Predicate<OrderValidationVo> isGeneralProduct = (vo) -> {
        log.info("OrderTypeValidator 일반주문 상품확인: {}", vo);
        return vo.getPrGoodsBaseModel().stream().allMatch(goodsBaseModel -> goodsBaseModel.getGoodsTpCd().equals("10"));
    };

    // 일반주문 필수 데이터 확인
    public static Predicate<OrderValidationVo> isGeneralValid = (vo) -> {
        log.info("OrderTypeValidator 일반주문 필수 데이터 확인: {}", vo);
        // 수취인 주소, 후대폰, 수취인명 유무 확인
        return vo.getOrderRequestVo().getOrdDvpAreaInfoVo()
                .stream()
                .noneMatch(infoVo -> infoVo.getRmtiNm().equals("") || infoVo.getRmtiAddr().equals("") || infoVo.getRmtiHpNo().equals(""));
    };
}
