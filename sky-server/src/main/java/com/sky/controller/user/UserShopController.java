package com.sky.controller.user;


import com.sky.result.Result;
import com.sky.service.impl.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api(tags = "用户端店铺相关控制器")
@RequestMapping("/user/shop")
public class UserShopController {

    @Autowired
    ShopService shopService;

    @GetMapping("/status")
    @ApiOperation("获取店铺状态")
    public  Result<Integer> getShopStatus() {
        int status;
        status = shopService.getShopStatus();
        return Result.success(status);
    }

}
