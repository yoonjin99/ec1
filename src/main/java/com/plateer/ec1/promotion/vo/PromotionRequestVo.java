package com.plateer.ec1.promotion.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class PromotionRequestVo {
    @NotNull(message = "회원번호가 존재하지 않습니다.")
    private String mbrNo;
    private List<ProductVo> products;
}
