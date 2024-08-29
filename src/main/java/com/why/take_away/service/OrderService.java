package com.why.take_away.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.take_away.entitty.Orders;

public interface OrderService extends IService<Orders> {
    //下单
    public void submit(Orders orders);
}
