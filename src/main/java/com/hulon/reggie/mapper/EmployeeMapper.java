package com.hulon.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hulon.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hulon
 * @date 2023/5/14
 * @className EmployeeMapper
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
