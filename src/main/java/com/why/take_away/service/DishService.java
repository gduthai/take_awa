package com.why.take_away.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.take_away.dto.DishDto;
import com.why.take_away.entitty.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入口味数据
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);
    //更新菜品信息同时更新口味信息
    public void updateWithFlavor(DishDto dishDto);
}
