ALTER VIEW "MTC_V_StockTransfer"
AS

SELECT
  'Z' AS "TransferType",--单据类型: Z  转储, R 申请
  '库存转储' AS "TransferTypeName",
  T0."DocEntry",-- 单据ID
  T0."DocNum",-- 退货单号
  T0."TaxDate",-- 单据日期
  T0."BPLId",--分支
  T0."BPLName",--分支名称
  T0."Filler" AS "FromWhsCode",--源仓库
  T1."WhsName" AS "FromWhsName",-- 源仓库
  T0."ToWhsCode",
  T2."WhsName" AS "ToWhsName",-- 目标仓库
  CASE WHEN T0."CANCELED"=N'Y' THEN N'C'
	   ELSE T0."DocStatus" END "DocStatus",-- 单据状态
  CASE WHEN T0."CANCELED"=N'Y' THEN N'取消'
       WHEN T0."DocStatus"=N'O' THEN N'未清'
       WHEN T0."DocStatus"=N'C' THEN N'已结算'
	   ELSE N'' END AS "DocStatusName",-- 单据状态名称
  T0."U_Creator" AS "Creator",
   T0."Comments"
FROM "OWTR" T0
LEFT JOIN "OWHS" T1 ON T1."WhsCode"=T0."Filler"
LEFT JOIN "OWHS" T2 ON T2."WhsCode"=T0."ToWhsCode"
WHERE T0."U_TrsType"='W'

UNION ALL

SELECT
  'R' AS "TransferType",
  '库存转储请求' AS "TransferTypeName",
  T0."DocEntry",-- 单据ID
  T0."DocNum",-- 退货单号
  T0."TaxDate",-- 单据日期
  T0."BPLId",--分支
  T0."BPLName",--分支名称
  T0."Filler" AS "FromWhsCode",--源仓库
  T1."WhsName" AS "FromWhsName",-- 源仓库
  T0."ToWhsCode",
  T2."WhsName" AS "ToWhsName",-- 目标仓库
  T0."DocStatus" AS "DocStatus",-- 单据状态
  CASE WHEN T0."CANCELED"=N'Y' THEN N'取消'
       WHEN T0."DocStatus"=N'O' THEN N'未清'
       WHEN T0."DocStatus"=N'C' THEN N'已结算'
	   ELSE N'' END AS "DocStatusName",-- 单据状态名称
   T0."U_Creator" AS "Creator",
   T0."Comments"
FROM "OWTQ" T0
LEFT JOIN "OWHS" T1 ON T1."WhsCode"=T0."Filler"
LEFT JOIN "OWHS" T2 ON T2."WhsCode"=T0."ToWhsCode"
WHERE T0."U_TrsType"='W';