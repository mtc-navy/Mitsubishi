DROP PROCEDURE "MTC_Get_InvPayment_DataSource";
CREATE PROCEDURE "MTC_Get_InvPayment_DataSource"
(
  BPLId NVARCHAR(10), --分支
  DocNum NVARCHAR(10), --销售订单号
  FromOrder NVARCHAR(10) DEFAULT 'N'
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  IF :FromOrder='N' THEN
      SELECT
        N'A00'||ROW_NUMBER() OVER() AS "PayCode",--收款方式码
        T0."PayName" AS "PayName",--收款方式名称
        T0."PayAmt" AS "PayAmt",--金额
        T0."PayDocNum" AS "PayDocNum",--收款单号
        T1."DocEntry" AS "PayDocEntry"--收款单标识
      FROM
      (
          SELECT DISTINCT
            T1."U_PayMethName" "PayName",--收款方式名称
            CASE WHEN T2."U_Z040" IS NOT NULL THEN IFNULL(T2."U_Z028",CAST(0 AS DECIMAL(19,6)))
                 WHEN T3."U_Z041" IS NOT NULL THEN IFNULL(T3."U_Z030",CAST(0 AS DECIMAL(19,6)))
                 WHEN T4."U_Z042" IS NOT NULL THEN IFNULL(T4."U_Z032",CAST(0 AS DECIMAL(19,6)))
                 WHEN T5."U_Z043" IS NOT NULL THEN IFNULL(T5."U_Z034",CAST(0 AS DECIMAL(19,6)))
                 ELSE CAST(0 AS DECIMAL(19,6)) END AS "PayAmt",--金额
            CASE WHEN T2."U_Z040" IS NOT NULL THEN IFNULL(T2."U_Z050",CAST(0 AS DECIMAL(19,6)))
                 WHEN T3."U_Z041" IS NOT NULL THEN IFNULL(T3."U_Z051",CAST(0 AS DECIMAL(19,6)))
                 WHEN T4."U_Z042" IS NOT NULL THEN IFNULL(T4."U_Z052",CAST(0 AS DECIMAL(19,6)))
                 WHEN T5."U_Z043" IS NOT NULL THEN IFNULL(T5."U_Z053",CAST(0 AS DECIMAL(19,6)))
                 ELSE CAST(0 AS DECIMAL(19,6)) END AS "PayDocNum"--收款单号
          FROM "@U_BANKINFO1" T0
          INNER JOIN "@U_BANKINFO2" T1 ON T0."Code" = T1."Code"
          LEFT JOIN "OINV" T2 ON T2."DocNum"=:DocNum AND T2."U_Z040"=T1."U_PayMethName"
          LEFT JOIN "OINV" T3 ON T3."DocNum"=:DocNum AND T3."U_Z041"=T1."U_PayMethName"
          LEFT JOIN "OINV" T4 ON T4."DocNum"=:DocNum AND T4."U_Z042"=T1."U_PayMethName"
          LEFT JOIN "OINV" T5 ON T5."DocNum"=:DocNum AND T5."U_Z043"=T1."U_PayMethName"
          WHERE T0."Code"=:BPLId AND T1."U_PayMethName" IS NOT NULL
      )T0
      LEFT JOIN "ORCT" T1 ON T1."DocNum"=T0."PayDocNum";
  END IF;

  IF :FromOrder='Y' THEN
      SELECT
        N'A00'||ROW_NUMBER() OVER() AS "PayCode",--收款方式码
        T0."PayName" AS "PayName",--收款方式名称
        T0."PayAmt" AS "PayAmt",--金额
        T0."PayDocNum" AS "PayDocNum",--收款单号
        T1."DocEntry" AS "PayDocEntry"--收款单标识
      FROM
      (
          SELECT DISTINCT
            T1."U_PayMethName" "PayName",--收款方式名称
            CASE WHEN T2."U_Z040" IS NOT NULL THEN IFNULL(T2."U_Z028",CAST(0 AS DECIMAL(19,6)))
                 WHEN T3."U_Z041" IS NOT NULL THEN IFNULL(T3."U_Z030",CAST(0 AS DECIMAL(19,6)))
                 WHEN T4."U_Z042" IS NOT NULL THEN IFNULL(T4."U_Z032",CAST(0 AS DECIMAL(19,6)))
                 WHEN T5."U_Z043" IS NOT NULL THEN IFNULL(T5."U_Z034",CAST(0 AS DECIMAL(19,6)))
                 ELSE CAST(0 AS DECIMAL(19,6)) END AS "PayAmt",--金额
            CASE WHEN T2."U_Z040" IS NOT NULL THEN IFNULL(T2."U_Z050",CAST(0 AS DECIMAL(19,6)))
                 WHEN T3."U_Z041" IS NOT NULL THEN IFNULL(T3."U_Z051",CAST(0 AS DECIMAL(19,6)))
                 WHEN T4."U_Z042" IS NOT NULL THEN IFNULL(T4."U_Z052",CAST(0 AS DECIMAL(19,6)))
                 WHEN T5."U_Z043" IS NOT NULL THEN IFNULL(T5."U_Z053",CAST(0 AS DECIMAL(19,6)))
                 ELSE CAST(0 AS DECIMAL(19,6)) END AS "PayDocNum"--收款单号
          FROM "@U_BANKINFO1" T0
          INNER JOIN "@U_BANKINFO2" T1 ON T0."Code" = T1."Code"
          LEFT JOIN "ORDR" T2 ON T2."DocNum"=:DocNum AND T2."U_Z040"=T1."U_PayMethName"
          LEFT JOIN "ORDR" T3 ON T3."DocNum"=:DocNum AND T3."U_Z041"=T1."U_PayMethName"
          LEFT JOIN "ORDR" T4 ON T4."DocNum"=:DocNum AND T4."U_Z042"=T1."U_PayMethName"
          LEFT JOIN "ORDR" T5 ON T5."DocNum"=:DocNum AND T5."U_Z043"=T1."U_PayMethName"
          WHERE T0."Code"=:BPLId AND T1."U_PayMethName" IS NOT NULL
      )T0
      LEFT JOIN "ORCT" T1 ON T1."DocNum"=T0."PayDocNum";
  END IF;
END;
