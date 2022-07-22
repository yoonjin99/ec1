package com.plateer.ec1.promotion.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PointRequestVo {
    @NotEmpty
    private String memberNo;
    @NotEmpty
    private Long pointAmt;
    @NotEmpty
    private String saveUseCcd;
}
