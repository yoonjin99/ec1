package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReturnDataCreatorImpl extends ClaimDataCreator {
    @Override
    public CreatorType getType() {
        return CreatorType.RETURN;
    }

    @Override
    public ClaimProcessVo updateDataCreator(ClaimProcessVo vo) {
        log.info("반품 update 데이터 생성 로직 호출");
        return null;
    }

    @Override
    public ClaimProcessVo insertDataCreator(ClaimProcessVo vo) {
        log.info("반품 insert 데이터 생성 로직 호출");
        return null;
    }

}
