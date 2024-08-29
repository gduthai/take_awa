package com.why.take_away.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.take_away.entitty.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
