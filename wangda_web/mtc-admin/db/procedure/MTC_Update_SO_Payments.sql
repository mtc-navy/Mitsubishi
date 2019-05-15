DROP PROCEDURE "MTC_Update_SO_Payments";
CREATE PROCEDURE "MTC_Update_SO_Payments"
(
  DocEntry INTEGER --销售订单
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  SELECT
    T0."U_Z050",T1."DocNum",
    T0."U_Z051",T2."DocNum",
    T0."U_Z052",T3."DocNum",
    T0."U_Z053",T4."DocNum"
  FROM "ORDR" T0
  LEFT JOIN "ORCT" T1 ON T0."DocNum"=T1."U_BaseRef" AND T1."Canceled"='N' AND T1."U_PayType"=T0."U_Z040"--收款方式1
  LEFT JOIN "ORCT" T2 ON T0."DocNum"=T2."U_BaseRef" AND T2."Canceled"='N' AND T2."U_PayType"=T0."U_Z041"--收款方式2
  LEFT JOIN "ORCT" T3 ON T0."DocNum"=T3."U_BaseRef" AND T3."Canceled"='N' AND T3."U_PayType"=T0."U_Z042"--收款方式3
  LEFT JOIN "ORCT" T4 ON T0."DocNum"=T4."U_BaseRef" AND T4."Canceled"='N' AND T4."U_PayType"=T0."U_Z043"--收款方式4
  WHERE T0."DocEntry"=:DocEntry;

  --更新
  UPDATE T0
  SET
    T0."U_Z050"=T1."DocNum",
    T0."U_Z051"=T2."DocNum",
    T0."U_Z052"=T3."DocNum",
    T0."U_Z053"=T4."DocNum"
  FROM "ORDR" T0
  LEFT JOIN "ORCT" T1 ON T0."DocNum"=T1."U_BaseRef" AND T1."Canceled"='N' AND T1."U_PayType"=T0."U_Z040"--收款方式1
  LEFT JOIN "ORCT" T2 ON T0."DocNum"=T2."U_BaseRef" AND T2."Canceled"='N' AND T2."U_PayType"=T0."U_Z041"--收款方式2
  LEFT JOIN "ORCT" T3 ON T0."DocNum"=T3."U_BaseRef" AND T3."Canceled"='N' AND T3."U_PayType"=T0."U_Z042"--收款方式3
  LEFT JOIN "ORCT" T4 ON T0."DocNum"=T4."U_BaseRef" AND T4."Canceled"='N' AND T4."U_PayType"=T0."U_Z043"--收款方式4
  WHERE T0."DocEntry"=:DocEntry;
  
END;