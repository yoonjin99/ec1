package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.ClaimType;
import com.plateer.ec1.claim.enums.CreatorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DataCreatorFactory {
    private final Map<CreatorType, ClaimDataCreator> map = new HashMap<>();

    public DataCreatorFactory(List<ClaimDataCreator> claimDataCreators) {
        claimDataCreators.forEach(claimDataCreator -> map.put(claimDataCreator.getType(), claimDataCreator));
    }

    public ClaimDataCreator getClaimDataCreator(CreatorType type){
        return map.get(type);
    }
}
