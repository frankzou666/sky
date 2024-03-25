package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.service.impl.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api(tags = "管理端店铺相关控制器")
@RequestMapping("/admin/shop")
public class AdminShopController {

    @Autowired
    ShopService shopService;

    @GetMapping("/status")
    @ApiOperation("获取店铺状态")
    public  Result<Integer> getShopStatus() {
        int status;

        status = shopService.getShopStatus();
        return Result.success(status);

    }

    @ApiOperation("获取店铺状态")
    @PutMapping("/{status}")
    public  Result updateShopStatus(@PathVariable int status) {
        Boolean result;
        log.info("设置店铺状态:{}",status==1?"启用":"禁用");
        result = shopService.updateShopStatus(status);
        return Result.success(status);

    }
}
