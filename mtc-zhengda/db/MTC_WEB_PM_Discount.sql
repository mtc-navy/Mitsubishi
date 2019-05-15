DROP PROCEDURE "MTC_WEB_PM_Discount";
CREATE PROCEDURE "MTC_WEB_PM_Discount"
(
  UserId NVARCHAR(50),   --用户ID(=>MTC_WUSR-DocEntry)
  CardCode NVARCHAR(50), --客户代码
  DocEntry NVARCHAR(50)  --选择单据主键
)
AS
BEGIN

  SELECT
    :CardCode AS "CardCode",--客户代码
    CAST(1000 AS DECIMAL(19,6)) AS "Credit",--信用额度
    CAST(0 AS DECIMAL(19,6)) AS "Arrears",--累计欠款
    CAST(0 AS DECIMAL(19,6)) AS "Rebate"--可用返利
  FROM DUMMY;

END;