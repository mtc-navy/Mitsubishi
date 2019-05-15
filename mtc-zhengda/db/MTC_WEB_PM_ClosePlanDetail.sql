DROP PROCEDURE "MTC_WEB_PM_ClosePlanDetail";
CREATE PROCEDURE "MTC_WEB_PM_ClosePlanDetail"
(
  UserId NVARCHAR(50),    --用户ID(=>MTC_WUSR-DocEntry)
  DocEntry INTEGER,       --计划单主键
  LineNum INTEGER         --明细行号
)
AS
BEGIN

  IF :LineNum=1 THEN
    SELECT '' AS "Msg" FROM DUMMY;
  END IF;

  IF :LineNum=2 THEN
    SELECT '关闭失败' AS "Msg" FROM DUMMY;
  END IF;

END;
