package com.middle.wcs.udi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 金蝶ERP模拟接口（开发测试用）
 * 模拟金蝶登录和物料查询接口，开发环境下无需连接真实金蝶服务器
 *
 * @author mock
 */
@Api(tags = "金蝶ERP模拟接口")
@RestController
@RequestMapping("/kingdee")
public class KingdeeController {

    @ApiOperation("模拟金蝶ERP登录")
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        result.put("LoginResultType", 1);
        result.put("Message", "模拟登录成功");
        result.put("Kdvcid", "mock-kdvcid");
        return result;
    }

    @ApiOperation("模拟金蝶ERP物料查询（BillQuery）")
    @PostMapping("/billQuery")
    public List<Map<String, Object>> billQuery(@RequestBody Map<String, Object> params) {
        // 构造mock数据，模拟金蝶BillQuery接口出参
        Map<String, Object> materialData = new HashMap<>();
        materialData.put("FMATERIALID", 100001);
        materialData.put("FNUMBER", "5ZA106000012");
        materialData.put("FNAME", "模拟物料");
        materialData.put("F_PAEZ_Qty", "0.0060150000");

        List<Map<String, Object>> resultList = new ArrayList<>();
        resultList.add(materialData);

        return resultList;
    }
}
