DROP PROCEDURE "MTC_WEB_PM_GetCountryDropList";
CREATE PROCEDURE "MTC_WEB_PM_GetCountryDropList"
(
  UserID NVARCHAR(50)    --用户
)
AS
BEGIN

  SELECT
    T0."Code",
    T0."Name"
  FROM "OCRY" T0;
  
END;