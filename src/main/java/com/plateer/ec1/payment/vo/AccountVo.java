package com.plateer.ec1.payment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountVo {
    @Builder.Default
    private String type = "Pay";
    @Builder.Default
    private String paymethod = "Vacct";
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime timestamp;
    @Builder.Default
    private String mid = "INIpayTest";
    private String moid;
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDateTime dtInput;
    @JsonFormat(pattern = "HHmm")
    private LocalDateTime tmInput;
    private String ordNo;
    private String goodName;
    private String buyerName;
    private String buyerEmail;
    private long price;
    private String bankCode;
    private String nmInput;
    @Builder.Default
    private String clientIp = clientIpCheck();

    private static String clientIpCheck(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();
        return ip;
    }
}
