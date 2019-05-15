DROP PROCEDURE "MTC_Get_CntctPrsn_DataSource";
CREATE PROCEDURE "MTC_Get_CntctPrsn_DataSource"
(
  CardCode NVARCHAR(50), --客户编码
  UserName NVARCHAR(50), --用户名
  FilterValue NVARCHAR(100) DEFAULT '''' -- 过滤条件
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  SELECT
    T0."CardCode",
    T0."CntctCode" AS "CntctPrsn",
    T0."Name" AS "CntctName"
  FROM "OCPR" T0
  WHERE T0."CardCode"=:CardCode;

END;