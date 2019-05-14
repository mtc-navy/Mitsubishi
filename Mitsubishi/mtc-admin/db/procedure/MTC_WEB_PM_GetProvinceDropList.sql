DROP PROCEDURE "MTC_WEB_PM_GetProvinceDropList";
CREATE PROCEDURE "MTC_WEB_PM_GetProvinceDropList"
(
  UserID NVARCHAR(50),    --用户
  Country NVARCHAR(50)    --国家
)
AS
BEGIN
  
  SELECT 
    T0."Code",
    T0."Name",*
  FROM "OCST" T0
  WHERE T0."Country"=:Country;
  
END;


 