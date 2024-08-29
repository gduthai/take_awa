package com.why.take_away.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.take_away.common.R;
import com.why.take_away.entitty.Employee;
import com.why.take_away.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //将提交的密码加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> QueryWrapper=new LambdaQueryWrapper<Employee>();
        QueryWrapper.eq(Employee::getUsername,employee.getUsername());//wrapper.eq("实体类::查询字段", "条件值"); //相当于where条件
        Employee emp=employeeService.getOne(QueryWrapper);

        if (emp==null){
            return R.error("登录失败");

        }
        if (!password.equals(emp.getPassword())){
            return R.error("登录失败");

        }
        if(emp.getStatus() == 0){
            return R.error("账号已被禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    /*
    * 员工推出*/
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /*
    * 新增员工*/
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}",employee.toString());
        //设置初始密码123456，需要md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //获得当前用户id
        Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);
        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /*
    * 分页查询*/
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page = {},pageSize = {}, name = {}",page,pageSize,name);
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);

    }
    /*
    * 根据id修改员工信息*/

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());
        Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /*
    * 根据id查询员工信息*/
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("分局id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到员工信息");


    }
}
