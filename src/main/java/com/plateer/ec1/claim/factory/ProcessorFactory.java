package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ProcessorFactory {
    private final Map<ProcessorType, ClaimProcessor> map = new HashMap<>();

    public ProcessorFactory(List<ClaimProcessor> claimProcessors) {
        claimProcessors.forEach(cp -> {
            map.put(cp.getType(), cp);
        });
    }

    public ClaimProcessor getClaimProcessor(ProcessorType type){
        return map.get(type);
    }
}
