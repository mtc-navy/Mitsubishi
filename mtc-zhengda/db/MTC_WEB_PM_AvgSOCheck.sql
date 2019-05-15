DROP PROCEDURE "MTC_WEB_PM_AvgSOCheck";
CREATE PROCEDURE "MTC_WEB_PM_AvgSOCheck"
(
  UserId NVARCHAR(50),    --用户ID(=>MTC_WUSR-DocEntry)
  CardCode NVARCHAR(50),  --客户编码
  ItemCode NVARCHAR(100), --物料代码
  DocDate NVARCHAR(10),   --日期
  Quantity DECIMAL(19,6)  --数量
)
AS
BEGIN

  IF :Quantity IS NULL THEN
    SELECT '' AS "Msg" FROM DUMMY;
  END IF;

  IF :Quantity IS NOT NULL THEN
    SELECT '计划单下达量超出 最近3个月平均销售量*1.2' AS "Msg" FROM DUMMY;
  END IF;

END;
