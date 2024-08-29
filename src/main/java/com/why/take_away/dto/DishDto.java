package com.why.take_away.dto;



import com.why.take_away.entitty.Dish;
import com.why.take_away.entitty.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
