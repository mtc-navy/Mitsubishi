DROP PROCEDURE "MTC_Get_InvPayments_DocEntry";
CREATE PROCEDURE "MTC_Get_InvPayments_DocEntry"
(
  DocEntry INTEGER --销售订单DocEntry
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  SELECT
    T0."DocEntry"
  FROM "ORCT" T0
  INNER JOIN "ORDR" T1 ON T1."DocNum"=T0."U_BaseRef"
  WHERE T1."DocEntry"=:DocEntry
  AND T0."Canceled"=N'N';

END;