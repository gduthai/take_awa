package com.why.take_away.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.take_away.entitty.Employee;
import com.why.take_away.mapper.EmployeeMapper;
import com.why.take_away.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
