package com.middle.wcs.udi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UDI模拟接口（开发测试用）
 *
 * @author mock
 */
@Api(tags = "UDI模拟接口")
@RestController
@RequestMapping("/udi")
public class UdiController {

    @ApiOperation("模拟查询UDI信息")
    @PostMapping("/getUdi")
    public Map<String, Object> getUdi(@RequestBody Map<String, String> params) {
        String udi = params.get("udi");

        // 构造mock数据，模拟ERP接口出参
        Map<String, Object> udiData = new HashMap<>();
        udiData.put("udi", udi != null ? udi : "(01)56945060548156(11)260417(17)270416(10)C8260417012");
        udiData.put("productionLineCodeWMS", "A1");
        udiData.put("productName", "血液透析浓缩液");
        udiData.put("specMode", "SXG-Y-A");
        udiData.put("produceBatchNo", "C8260417012");
        udiData.put("productCode", "5ZA106000012");
        udiData.put("orderNo", "SG072604170002");

        List<Map<String, Object>> dataList = new ArrayList<>();
        dataList.add(udiData);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("success", true);
        result.put("msg", "ok");
        result.put("data", dataList);

        return result;
    }
}
