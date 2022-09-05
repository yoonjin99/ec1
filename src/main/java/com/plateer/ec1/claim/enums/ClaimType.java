package com.plateer.ec1.claim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum ClaimType {
    GCC("GCC",ProcessorType.COMPLETE, CreatorType.C),
    MCA("MCA",ProcessorType.ACCEPTWITHDRWAL, CreatorType.C),
    MCC("MCC",ProcessorType.COMPLETE, CreatorType.C),
    RA("RA",ProcessorType.ACCEPTWITHDRWAL, CreatorType.R),
    RW("RW",ProcessorType.ACCEPTWITHDRWAL, CreatorType.RW),
    EA("EA",ProcessorType.ACCEPTWITHDRWAL, CreatorType.EX);

    private String type;
    private ProcessorType processorType;
    private CreatorType creatorType;
}
