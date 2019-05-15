DROP PROCEDURE "MTC_WEB_PM_GetPlanList";
CREATE PROCEDURE "MTC_WEB_PM_GetPlanList"
(
  UserId NVARCHAR(50),    --用户ID(=>MTC_WUSR-DocEntry)
  PageIndex INTEGER,      --页码
  PageSize INTEGER,       --每页记录数
  StartDate NVARCHAR(10), --开始日期
  EndDate NVARCHAR(10),   --结束日期
  CardCode NVARCHAR(50),  --客户编码
  CardName NVARCHAR(100), --客户名称
  DocStatus NVARCHAR(10)  --单据状态
)
AS
BEGIN

  SELECT
    T0.*
  FROM "MTC_WUQT" T0;

END;
