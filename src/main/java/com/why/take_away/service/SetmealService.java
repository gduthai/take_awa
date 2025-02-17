package com.why.take_away.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.take_away.dto.SetmealDto;
import com.why.take_away.entitty.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除菜品关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
