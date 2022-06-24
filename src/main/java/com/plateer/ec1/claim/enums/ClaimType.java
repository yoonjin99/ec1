package com.plateer.ec1.claim.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum ClaimType {
    GCC(ProcessorType.COMPLETE, Arrays.asList(),Arrays.asList(), true, "","",Arrays.asList()),
    MCA(ProcessorType.ACCEPTWITHDRWAL, Arrays.asList(),Arrays.asList(), true, "","",Arrays.asList()),
    MCC(ProcessorType.COMPLETE, Arrays.asList(),Arrays.asList(), true, "","",Arrays.asList()),
    RA(ProcessorType.ACCEPTWITHDRWAL, Arrays.asList(),Arrays.asList(), true, "","",Arrays.asList()),
    RC(ProcessorType.COMPLETE, Arrays.asList(),Arrays.asList(), true, "","",Arrays.asList()),
    RW(ProcessorType.ACCEPTWITHDRWAL, Arrays.asList(),Arrays.asList(), true, "","",Arrays.asList()),
    EA(ProcessorType.ACCEPTWITHDRWAL, Arrays.asList(),Arrays.asList(), true, "","",Arrays.asList()),
    EW(ProcessorType.ACCEPTWITHDRWAL, Arrays.asList(),Arrays.asList(), true, "","",Arrays.asList());

    private ProcessorType processorType;
    private List<String> validStatusList;
    private List<String> productType;
    private Boolean claimNoFlag;
    private String claimCode;
    private String orderStateCode;
    private List<String> deliveryCode;

    public ProcessorType getProcessor(){
        return processorType;
    }

}
