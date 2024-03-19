package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //  后期需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }


    @Transactional
    public void add(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        String passwordStr = PasswordConstant.DEFAULT_PASSWORD;
        String defaultPassword=DigestUtils.md5DigestAsHex(passwordStr.getBytes());

        employee.setPassword(defaultPassword);
        employee.setIdNumber(employeeDTO.getIdNumber());
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setStatus(StatusConstant.ENABLE);

        //
        Long empId = BaseContext.getCurrentId();
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);
        employeeMapper.insert(employee);

    }

    @Override
    public PageResult findEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        //组装分页对像
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.findEmployee(employeePageQueryDTO);
        //page对像里面就包含了分页的相关对像

        List<Employee> records = page.getResult();
        Long total = page.getTotal();
        return  new PageResult(total, records);


    }

    @Override
    @Transactional
    public Boolean lockEmployee(Integer status, Long id) {
        //查询当前用户
        Employee employeeObj = employeeMapper.getById(id);

        if (employeeObj==null){
            throw  new AccountLockedException("不存在用户ID: "+id);
        }

        //更改状态
        Long empId = BaseContext.getCurrentId();
        employeeObj.setStatus(status);

        //保存到数据库中
        try{
            employeeMapper.updateEmployee(employeeObj);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return  true;
    }

    @Override
    public Employee findEmployeebyId(Long id) {
        Employee employee = employeeMapper.getById(id);



        if (employee==null){
            throw  new AccountLockedException("不存在用户ID: "+id);
        }
        return employee;
    }

    @Override
    @Transactional
    public Boolean updateEmployee(Employee employee) {
        //更改状态
        Long empId = BaseContext.getCurrentId();
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
        try{
            employeeMapper.updateEmployee(employee);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return  true;
    }

}
