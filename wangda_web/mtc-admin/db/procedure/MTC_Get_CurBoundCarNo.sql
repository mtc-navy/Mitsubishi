DROP PROCEDURE "MTC_Get_CurBoundCarNo";
CREATE PROCEDURE "MTC_Get_CurBoundCarNo"
(
  CardCode NVARCHAR(50),
  CarNo NVARCHAR(50),
  TaxDate NVARCHAR(50)
)
AS
BEGIN

  /*
	取可用车辆：当天当前客户匹配车辆，过了轻磅(皮重)的车辆未被引用的，且状态为O
  */
  SELECT DISTINCT T0."U_CarNo" AS "CarNo"
  FROM "@U_WORDR" T0
  WHERE DAYS_BETWEEN("U_CheckTime",:TaxDate) = 0
    AND "Status"='O' AND "U_Tare">0
    AND NOT EXISTS(SELECT 1 FROM ORDR T1 WHERE "DocStatus"!='C' AND T1."U_CarNo"=T0."U_CarNo")
    AND EXISTS(SELECT * FROM "@U_WRDR1" T2 WHERE T2."DocEntry"=T0."DocEntry" AND T2."U_CardCode"=:CardCode)
    AND T0."U_CarNo" LIKE '%'||:CarNo||'%'
  ORDER BY "U_CarNo";

END;
