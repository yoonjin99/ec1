package com.plateer.ec1.payment.factory;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PaymentTypeServiceFactory {

    private final Map<PaymentType, PaymentTypeService> map = new HashMap();

    public PaymentTypeServiceFactory(List<PaymentTypeService> paymentServices) {
        paymentServices.forEach(paymentService -> map.put(paymentService.getType(), paymentService));
    }

    public PaymentTypeService getPaymentService(PaymentType type){
        log.info("-----------------PaymentServiceFactory start");
        return map.get(type);
    }
}
