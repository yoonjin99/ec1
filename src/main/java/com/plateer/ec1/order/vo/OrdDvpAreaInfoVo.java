package com.plateer.ec1.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrdDvpAreaInfoVo {
    private int dvpSeq;
    private String rmtiNm;
    private String rmtiHpNo;
    private String rmtiAddr;
    private String rmtiAddrDtl;
    private List<OrdDvpInfo> ordDvpInfo;
}
