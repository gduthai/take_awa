package com.why.take_away.dto;


import com.why.take_away.entitty.Setmeal;
import com.why.take_away.entitty.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
