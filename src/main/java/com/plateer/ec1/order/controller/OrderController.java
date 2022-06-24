package com.plateer.ec1.order.controller;

import com.plateer.ec1.order.service.OrderService;
import com.plateer.ec1.order.vo.OrderRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public void order(OrderRequestVo orderRequest){
        orderService.order(orderRequest);
    }

}
