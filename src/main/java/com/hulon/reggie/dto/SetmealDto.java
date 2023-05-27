package com.hulon.reggie.dto;

import com.hulon.reggie.entity.Setmeal;
import com.hulon.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
