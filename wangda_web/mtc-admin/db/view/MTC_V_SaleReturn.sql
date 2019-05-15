CREATE VIEW "MTC_V_SaleReturn"
AS
SELECT
  T0."DocEntry",-- 单据ID
  T0."DocNum",-- 退货单号
  T0."TaxDate",-- 单据日期
  T0."CardCode",-- 客户编码
  T0."CardName", -- 客户名称
  CAST(T0."CntctCode" AS NVARCHAR(100)) AS "CntctCode",-- 收货人（联系人）
  T1."Name" AS "CntctName",-- 收货人名称
  T0."U_TakeNo" AS "TakeGoodsNo", -- 提货单号
  T0."U_WORDREntry" AS "WeighNo",-- 过磅单号
  T0."U_CarNo" AS "CarNo",-- 车牌号
  T2."LineTotal" AS "TotalAmt",-- 总计
  CASE WHEN T0."CANCELED"=N'Y' THEN N'C'
       WHEN IFNULL(CAST(T4."DocNum" AS NVARCHAR(50)),'')!=N'' THEN N'Y'
       WHEN T0."DocStatus"=N'O' THEN N'W'
       WHEN T0."DocStatus"=N'C' THEN N'D'
       ELSE N'' END "DocStatus",
  CASE WHEN T0."CANCELED"=N'Y' THEN N'已取消'
       WHEN IFNULL(CAST(T4."DocNum" AS NVARCHAR(50)),'')!=N'' THEN N'已退货'
       WHEN T0."DocStatus"=N'O' THEN N'未清'
       WHEN T0."DocStatus"=N'C' THEN N'已结算' ELSE N'' END AS "DocStatsName"
FROM "ORDN" T0
LEFT JOIN "OCPR" T1 ON T1."CntctCode"=T0."CntctCode"
LEFT JOIN (SELECT "DocEntry", SUM("LineTotal") AS "LineTotal"
	       FROM "RDN1"
	       GROUP BY "DocEntry")T2 ON T2."DocEntry"=T0."DocEntry" --汇总金额
LEFT JOIN (SELECT "U_FID", MAX("DocNum") AS "DocNum"
	       FROM "ORIN"
   	       WHERE "CANCELED"='N'
	       GROUP BY "U_FID") T4 ON CAST(T4."U_FID" AS NVARCHAR(50))=CAST(T0."DocNum" AS NVARCHAR(50)) --获取发货单号
WHERE T0."U_TrsType" = N'W'
AND LOCATE(IFNULL(T0."JrnlMemo",''),'抵销')=0;