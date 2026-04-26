package com.middle.wcs.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.middle.wcs.order.entity.dto.OrderInfoPageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.middle.wcs.order.entity.po.OrderInfo;

import java.util.List;

/**
 * (OrderInfo)表数据库访问层
 *
 * @author makejava
 * @since 2024-12-28 23:59:48
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    List<OrderInfo> queryHistoryOrderList(OrderInfoPageDTO dto);

    /**
     * 查询今日最新已下货数据
     * @param unloadPort 下货口编号，可为空
     * @return 订单信息
     */
    OrderInfo getLastUnloadGoods(@Param("unloadPort") String unloadPort);
}
