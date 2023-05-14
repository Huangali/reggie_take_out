package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.entity.Employee;
import com.hulon.reggie.mapper.EmployeeMapper;
import com.hulon.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author Hulon
 * @date 2023/5/14
 * @className EmployeeServiceImpl
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
