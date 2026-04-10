package com.middle.wcs.order.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 订单分页查询入参（与 {@link com.middle.wcs.order.entity.po.OrderInfo} 字段对应）
 */
@Data
public class OrderInfoPageDTO {

    /**
     * 起始页数
     */
    @NotNull(message = "起始页数不能为空")
    private Integer pageNum;

    /**
     * 每页大小
     */
    @NotNull(message = "每页大小不能为空")
    private Integer pageSize;

    /**
     * 生产单批次订单ID（模糊）
     */
    private String batchId;

    /**
     * 产品名称（模糊）
     */
    private String productName;

    /**
     * 托盘号（模糊）
     */
    private String trayCode;

    /**
     * 托盘状态：1执行中 2已组批 3已称重 4已下货
     */
    private String trayStatus;

    /**
     * 作废标识：0未作废 1作废
     */
    private String invalidFlag;

    /**
     * 规格（模糊）
     */
    private String spec;

    /**
     * 来源
     */
    private String source;

    /**
     * 生产日期（对应 insert_time 的日期，格式 yyyy-MM-dd，按自然日筛选）
     */
    private String productionDate;
}
