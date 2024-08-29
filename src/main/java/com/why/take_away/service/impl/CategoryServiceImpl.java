package com.why.take_away.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.take_away.common.CustomException;
import com.why.take_away.entitty.Category;
import com.why.take_away.entitty.Dish;
import com.why.take_away.entitty.Setmeal;
import com.why.take_away.mapper.CategoryMapper;
import com.why.take_away.service.CategoryService;
import com.why.take_away.service.DishService;
import com.why.take_away.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    private DishService dishService;

    private SetmealService setmealService;


    public void remove(Long id){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count>0){
            //已经关联，抛出一个异常
            throw new CustomException("当前分类关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1=setmealService.count(setmealLambdaQueryWrapper);
        if(count1>0){
            //已经关联，抛出一个异常
            throw new CustomException("当前分类关联了套餐，不能删除");
        }
        super.removeById(id);




    }

}
