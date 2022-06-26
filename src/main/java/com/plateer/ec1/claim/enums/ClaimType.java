package com.plateer.ec1.claim.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum ClaimType {
    GCC(ProcessorType.COMPLETE, CreatorType.CANCEL, Arrays.asList(),Arrays.asList(), true, ""),
    MCA(ProcessorType.ACCEPTWITHDRWAL, CreatorType.CANCEL, Arrays.asList(),Arrays.asList(), true, ""),
    RA(ProcessorType.ACCEPTWITHDRWAL, CreatorType.RETURN, Arrays.asList(),Arrays.asList(), true, ""),
    RW(ProcessorType.ACCEPTWITHDRWAL, CreatorType.RETURN, Arrays.asList(),Arrays.asList(), true, ""),
    EA(ProcessorType.ACCEPTWITHDRWAL, CreatorType.EXCHANGE, Arrays.asList(),Arrays.asList(), true, "");

    private ProcessorType processorType;
    private CreatorType creatorType;
    private List<String> validStatusList;
    private List<String> productType;
    private Boolean claimNoFlag;
    private String claimCode;

    public ProcessorType getProcessor(){
        return processorType;
    }

    public CreatorType getCreatorType(){
        return creatorType;
    }

}
