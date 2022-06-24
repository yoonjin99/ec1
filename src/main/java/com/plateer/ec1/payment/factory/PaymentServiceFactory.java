package com.plateer.ec1.payment.factory;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PaymentServiceFactory {

    private final Map<PaymentType, PaymentService> map = new HashMap();

    public PaymentServiceFactory(List<PaymentService> paymentServices) {
        paymentServices.forEach(paymentService -> map.put(paymentService.getType(), paymentService));
    }

    public PaymentService getPaymentService(PaymentType type){
        log.info("-----------------PaymentServiceFactory start");
        return map.get(type);
    }
}
