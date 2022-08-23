package com.plateer.ec1.claim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum ClaimType {
    GCC("GCC",ProcessorType.COMPLETE, CreatorType.C, Arrays.asList(),Arrays.asList(), true, ""),
    MCA("MCA",ProcessorType.ACCEPTWITHDRWAL, CreatorType.C, Arrays.asList(),Arrays.asList(), true, ""),
    MCC("MCC",ProcessorType.COMPLETE, CreatorType.C, Arrays.asList(),Arrays.asList(), true, ""),
    RA("RA",ProcessorType.ACCEPTWITHDRWAL, CreatorType.R, Arrays.asList(),Arrays.asList(), true, ""),
    RW("RW",ProcessorType.ACCEPTWITHDRWAL, CreatorType.R, Arrays.asList(),Arrays.asList(), true, ""),
    EA("EA",ProcessorType.ACCEPTWITHDRWAL, CreatorType.EX, Arrays.asList(),Arrays.asList(), true, "");

    private String type;
    private ProcessorType processorType;
    private CreatorType creatorType;
    private List<String> validStatusList;
    private List<String> productType;
    private Boolean claimNoFlag;
    private String claimCode;
}
