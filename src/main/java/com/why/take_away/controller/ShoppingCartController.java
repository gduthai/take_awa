package com.why.take_away.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.why.take_away.common.BaseContext;
import com.why.take_away.common.R;
import com.why.take_away.entitty.ShoppingCart;
import com.why.take_away.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车数据：{}", shoppingCart);

        //set user id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        //if in shoppingCart num+1
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);

        if(dishId != null){
            //add is dish
            queryWrapper.eq(ShoppingCart::getDishId,dishId);



        }else{
            //add is setmeal
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart carServiceOne = shoppingCartService.getOne(queryWrapper);


        //in num+1
        if(carServiceOne != null){
            Integer number = carServiceOne.getNumber();
            carServiceOne.setNumber(number+1);
            shoppingCartService.updateById(carServiceOne);
        }else{
            //no in  default=1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            carServiceOne = shoppingCart;
        }
        return R.success(carServiceOne);
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> getAll(){
        log.info("查看购物车");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }
}
