package com.why.take_away.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.take_away.entitty.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}
