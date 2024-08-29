package com.why.take_away.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.take_away.entitty.OrderDetail;
import com.why.take_away.mapper.OrderDetailMapper;
import com.why.take_away.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}