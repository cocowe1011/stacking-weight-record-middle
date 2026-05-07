-- 用户表结构升级SQL (SQL Server版本)

-- 1. 添加用户角色字段
ALTER TABLE user_info ADD user_role NVARCHAR(20) DEFAULT 'OPERATOR';
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户角色（ADMIN-管理员，OPERATOR-操作员）', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'user_info', @level2type=N'COLUMN', @level2name=N'user_role';

-- 2. 添加登录失败次数字段
ALTER TABLE user_info ADD login_fail_count INT DEFAULT 0;
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'登录失败次数', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'user_info', @level2type=N'COLUMN', @level2name=N'login_fail_count';

-- 3. 添加是否锁定字段
ALTER TABLE user_info ADD is_locked INT DEFAULT 0;
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否锁定（0-否，1-是）', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'user_info', @level2type=N'COLUMN', @level2name=N'is_locked';

-- 5. 添加更新时间字段
ALTER TABLE user_info ADD update_time DATETIME DEFAULT GETDATE();
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'更新时间', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'user_info', @level2type=N'COLUMN', @level2name=N'update_time';

-- 6. 添加user_code唯一性约束（如果不存在）
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'UK_user_code' AND object_id = OBJECT_ID('user_info'))
BEGIN
ALTER TABLE user_info ADD CONSTRAINT UK_user_code UNIQUE (user_code);
END

-- 7. 插入默认管理员账号（如果不存在）
IF NOT EXISTS (SELECT 1 FROM user_info WHERE user_code = 'admin')
BEGIN
    INSERT INTO user_info (user_id, user_code, user_password, user_name, user_role, login_fail_count, is_locked, create_time, update_time)
    VALUES (1, 'admin', 'admin', N'系统管理员', 'ADMIN', 0, 0, GETDATE(), GETDATE());
END

-- 8. 更新现有用户为操作员角色
UPDATE user_info SET user_role = 'OPERATOR' WHERE user_code != 'admin' AND user_role IS NULL;

-- 9. order_info表添加下货口编号字段
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('order_info') AND name = 'unload_port')
BEGIN
    ALTER TABLE order_info ADD unload_port NVARCHAR(10) DEFAULT NULL;
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下货口编号（1-下货口1，2-下货口2）', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'unload_port';
END

-- 10. order_info表添加分录ID字段（对应金蝶ERP的FTreeEntity_FEntryId）
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('order_info') AND name = 'fentry_id')
BEGIN
    ALTER TABLE order_info ADD fentry_id NVARCHAR(50) DEFAULT NULL;
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分录ID（对应金蝶ERP的FTreeEntity_FEntryId字段）', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'fentry_id';
END

-- 11. order_info表添加UDI条码字段
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('order_info') AND name = 'udi_code')
BEGIN
    ALTER TABLE order_info ADD udi_code NVARCHAR(255) DEFAULT NULL;
    EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'UDI条码', @level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'order_info', @level2type=N'COLUMN', @level2name=N'udi_code';
END

--更改订单id字段类型为varchar(255)，以适应更长的订单ID
ALTER TABLE [dbo].[order_info] ALTER COLUMN [order_id] varchar(255) NULL

-- ============================================================
-- order_info 表索引优化（解决 queryHistoryOrderList 查询超时）
-- ============================================================

-- 1. 核心复合索引：覆盖 invalid_flag 等值过滤 + insert_time 排序
--    查询固定条件 invalid_flag='0'，且按 insert_time DESC 排序
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_order_info_invalid_flag_insert_time' AND object_id = OBJECT_ID('order_info'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_order_info_invalid_flag_insert_time
    ON dbo.order_info (invalid_flag, insert_time DESC);
END

-- 2. 托盘状态索引：tray_status 是高频等值过滤条件
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_order_info_tray_status' AND object_id = OBJECT_ID('order_info'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_order_info_tray_status
    ON dbo.order_info (tray_status);
END

-- 3. 物料编码索引：product_code 模糊查询（前缀模糊时可走索引）
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_order_info_product_code' AND object_id = OBJECT_ID('order_info'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_order_info_product_code
    ON dbo.order_info (product_code);
END

-- 4. 生产订单号索引：order_id 模糊查询
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_order_info_order_id' AND object_id = OBJECT_ID('order_info'))
BEGIN
    CREATE NONCLUSTERED INDEX IX_order_info_order_id
    ON dbo.order_info (order_id);
END