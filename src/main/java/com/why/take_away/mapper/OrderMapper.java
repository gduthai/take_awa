package com.why.take_away.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.take_away.entitty.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}