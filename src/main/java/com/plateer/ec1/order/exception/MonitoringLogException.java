package com.plateer.ec1.order.exception;

import com.plateer.ec1.common.code.order.OPT0012Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonitoringLogException extends RuntimeException{
    private final OPT0012Type errorCode;
}
