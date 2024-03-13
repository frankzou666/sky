package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);
    void add(EmployeeDTO employeeDTO);

    PageResult  findEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    Boolean lockEmployee(Integer status, Long id);


    //根据id返回员工信息
    Employee  findEmployeebyId(Long id);

    Boolean updateEmployee(Employee employee);


}
