package com.why.take_away.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.take_away.entitty.ShoppingCart;
import com.why.take_away.mapper.ShoppingCartMapper;
import com.why.take_away.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
