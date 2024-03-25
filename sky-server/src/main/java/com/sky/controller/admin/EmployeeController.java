package com.sky.controller.admin;

import com.alibaba.fastjson.JSON;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags="后台运营人员controller")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    RedisTemplate redisTemplateForEmployee;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping()
    @ApiOperation(value = "员工新增")
    public Result  add(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工,{}",employeeDTO);
        employeeService.add(employeeDTO);
        return  Result.success();

    }

    @PutMapping()
    @ApiOperation(value = "员工修改")
    public Result  updateEmployee(@RequestBody Employee employee){
        log.info("修改员工,{}",employee);
        Boolean result = employeeService.updateEmployee(employee);
        //修改失败
        if (!result) {
            return Result.error("修改员工失败: " + employee.getId().toString());
        }
        return  Result.success();

    }

    @GetMapping("/page")
    @ApiOperation(value = "员工分页查询")
    public Result<PageResult>  getEmployee(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工查询页,{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.findEmployee(employeePageQueryDTO);
        return Result.success(pageResult);

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id员工查询")
    public Result<Employee> getEmployeeById(@PathVariable("id") Long id){
        log.info("根据id员工查询,{}",id);
        Employee employee = employeeService.findEmployeebyId(id);
        employee.setPassword("*****************");
        return Result.success(employee);

    }

    @PostMapping("/redis/{id}")
    @ApiOperation(value = "员工存放到redis")
    public Result  toRedistoEmployee(@PathVariable("id") Long id){
        Employee employee =  employeeService.findEmployeebyId(id);
        String jsonString = JSON.toJSONString(employee);
        redisTemplateForEmployee.opsForHash().put("maps",id.toString(),jsonString);
        return Result.success();

    }

    @GetMapping("/redis/{id}")
    @ApiOperation(value = "员工存放到redis")
    public Result  getRedistoEmployee(@PathVariable("id") Long id){

        String jsonString = (String)redisTemplateForEmployee.opsForValue().get(id.toString());
        Employee employee = (Employee)JSON.parseObject(jsonString,Employee.class);
        return Result.success(employee);

    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "员工启用禁用")
    public Result  lockEmployee(@PathVariable("status") Integer status,@RequestParam  Long id){
        log.info("员工启用禁用,{}",status);
        Boolean result =  employeeService.lockEmployee(status,id);
        if (!result) {
            return Result.error("启用或禁用失败,ID:{}"+id);
        }
        return Result.success();

    }



}
