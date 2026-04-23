package com.middle.wcs.order.controller;

import com.middle.wcs.hander.ResponseResult;
import com.middle.wcs.order.entity.po.OrderInfo;
import com.middle.wcs.order.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 下货接口控制器
 */
@Api(tags = "下货接口")
@RestController
@RequestMapping("/unload")
public class UnloadController {

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 获取今日最新已下货数据
     * @return 订单信息
     */
    @ApiOperation("获取今日最新已下货数据")
    @GetMapping("/getLastGoods")
    public ResponseResult<OrderInfo> getLastGoods() {
        return ResponseResult.success(this.orderInfoService.getLastUnloadGoods());
    }
}
