package com.middle.wcs.order.entity.po;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * (OrderInfo)实体类
 *
 * @author makejava
 * @since 2024-12-28 23:59:48
 */
@Data
@TableName("order_info")
public class OrderInfo {
    /**
    * 主键
    */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
    * 插入时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date insertTime;

    /**
    * 生产单批次订单ID
    */
    private String batchId;

    /**
    * 产品名称
    */
    private String productName;

    /**
     * 托盘号
     */
    private String trayCode;

    /**
    * 托盘状态，1执行中2已组批3已称重4已下货
    */
    private String trayStatus;

    /**
     * 作废标识，0未作废，1作废
     */
    private String invalidFlag;

    /**
     * 规格
     */
    private String spec;
    /**
     * 订单完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;

    /**
     * 称重重量
     */
    private String weight;

    /**
     * 组批数量
     */
    private String batchNum;

    /**
     * 来源
     */
    private String source;

    /**
     * 物料编码
     */
    private String productCode;

    /**
     * 生产订单号
     */
    private String orderId;
}
