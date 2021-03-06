package com.plateer.ec1.claim.creator.impl;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.CreatorType;
import com.plateer.ec1.claim.vo.ClaimProcessVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CancelDataCreatorImpl extends ClaimDataCreator {
    @Override
    public CreatorType getType() {
        return CreatorType.CANCEL;
    }

    @Override
    public ClaimProcessVo updateDataCreator(ClaimProcessVo vo) {
        log.info("취소 update 데이터 생성 로직 호출");
        return null;
    }

    @Override
    public ClaimProcessVo insertDataCreator(ClaimProcessVo vo) {
        log.info("취소 update 데이터 생성 로직 호출");
        return null;
    }
}

// 데이터가 극히 일부 다르므로 분기처리 해주면 될 것 같음