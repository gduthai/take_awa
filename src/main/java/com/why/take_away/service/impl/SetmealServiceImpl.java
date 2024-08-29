package com.why.take_away.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.take_away.common.CustomException;
import com.why.take_away.dto.SetmealDto;
import com.why.take_away.entitty.Setmeal;
import com.why.take_away.entitty.SetmealDish;
import com.why.take_away.mapper.SetmealMapper;
import com.why.take_away.service.SetmealDishService;
import com.why.take_away.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存setmeal
        this.save(setmealDto);

        List<SetmealDish> setmealDishs=setmealDto.getSetmealDishes();

        setmealDishs.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //操作setmeal——dish
        setmealDishService.saveBatch(setmealDishs);
    }
    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态，是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count=this.count(queryWrapper);
        if(count>0){
            throw new CustomException("正在售卖中，不能删除");
        }


        //可以删除
        this.removeByIds(ids);

        //删除关系表中的数据
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(lambdaQueryWrapper);

    }
}
