DROP PROCEDURE "MTC_Get_SO_PrintType";
CREATE PROCEDURE "MTC_Get_SO_PrintType"
(
  DocEntry INTEGER --销售订单DocEntry
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  SELECT
    T0."CardCode",
    T0."CardName",
    IFNULL(T1."U_PrintType",'0') AS "PrintType"
  FROM "ORDR" T0
  INNER JOIN "OCRD" T1 ON T1."CardCode"=T0."CardCode"
  WHERE T0."DocEntry"=:DocEntry;

END;