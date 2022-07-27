package com.plateer.ec1.order.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrdDvpAreaInfoVo {
    private int dvpSeq;
    private String rmtiNm;
    private String rmtiHpNo;
    @NotEmpty
    private String rmtiAddr;
    @NotEmpty
    private String rmtiAddrDtl;
    private List<OrdDvpInfo> ordDvpInfo;
}
