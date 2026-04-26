package com.middle.wcs.order.controller;

import com.middle.wcs.hander.ResponseResult;
import com.middle.wcs.order.entity.po.OrderInfo;
import com.middle.wcs.order.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
     * @param unloadPort 下货口编号（1-下货口1，2-下货口2），不传则不限
     * @return 订单信息
     */
    @ApiOperation("获取今日最新已下货数据")
    @GetMapping("/getLastGoods")
    public ResponseResult<OrderInfo> getLastGoods(
            @ApiParam(value = "下货口编号（1-下货口1，2-下货口2）", required = true) String unloadPort) {
        return ResponseResult.success(this.orderInfoService.getLastUnloadGoods(unloadPort));
    }
}
