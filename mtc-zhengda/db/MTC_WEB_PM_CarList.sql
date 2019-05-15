DROP PROCEDURE "MTC_WEB_PM_CarList";
CREATE PROCEDURE "MTC_WEB_PM_CarList"
(
  UserId NVARCHAR(50)  --用户ID(=>MTC_WUSR-DocEntry)
)
AS
BEGIN

  SELECT
    '沪A0001' AS "CarNum",--车牌号
    '司机' AS "Driver",--司机
    'YS001' AS "AgentCode",--运输商编码
    '运输商' AS "AgentName"--运输商名称
  FROM DUMMY;

END;