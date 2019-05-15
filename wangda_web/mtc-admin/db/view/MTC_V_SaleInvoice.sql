ALTER VIEW "MTC_V_SaleInvoice"  AS
SELECT
	 T0."DocEntry",-- 单据ID
     T0."DocNum",-- 单据编号
     T0."TaxDate",-- 单据日期
     T0."CardCode",-- 客户编码
     T0."CardName",-- 客户名称
     CAST(T0."CntctCode" AS NVARCHAR(100)) AS "CntctCode",-- 收货人（联系人）
     T1."Name" AS "CntctName",-- 收货人名称
     T3."DocEntry" AS "InvDocEntry",-- 开票单ID
     T4."DocNum" AS "TakeGoodsNo",-- 发货单号
     T0."U_WORDREntry" AS "WeighNo",-- 过磅单号
     T0."U_CarNo" AS "CarNo",-- 车牌号
     T2."LineTotal" AS "TotalAmt",-- 总计
     --总的结算金额-收款方式金额-使用的折扣
     T2."LineTotal" - (IFNULL(T0."U_Z028",0)+IFNULL(T0."U_Z030",0)+IFNULL(T0."U_Z032",0)
                      +IFNULL(T0."U_Z034",0)) AS "ArrearsAmt",-- 欠款
     CASE WHEN T0."CANCELED"=N'Y' THEN N'C'
          WHEN IFNULL(CAST(T4."DocNum" AS NVARCHAR(50)),'')!=N'' THEN N'Y'
          WHEN T0."DocStatus"=N'O' THEN N'W' ELSE N'' END "DocStatus", -- 单据状态
     CASE WHEN T0."CANCELED"=N'Y' THEN N'取消'
          WHEN IFNULL(CAST(T4."DocNum" AS NVARCHAR(50)),'')!=N'' THEN N'已发货'
          WHEN T0."DocStatus"=N'O' THEN N'未发货'
		  ELSE N'' END AS "DocStatsName",
     T0."U_DesOrderNum" AS "DesOrderNum",--订料单号
     T0."U_PriceOrderNum" AS "PriceOrderNum",--保价单号
     MAP("U_TranType",'A','自运','B','代运') AS "TranType",-- 运输方式
     MAP("U_BusiType",'A','工厂司机拉货','B','客户拉货','C','无运费') AS "BusiType",-- 业务类型
	 T0."BPLId",--销售分支
     T0."BPLId" AS "TakeBPLId"--提货分支
FROM "ORDR" T0
LEFT JOIN "OCPR" T1 ON T1."CntctCode"=T0."CntctCode" -- 获取收货人
LEFT JOIN (SELECT "DocEntry", SUM("LineTotal") AS "LineTotal"
		   FROM "RDR1"
		   GROUP BY "DocEntry")T2 ON T2."DocEntry"=T0."DocEntry" --汇总金额
LEFT JOIN (SELECT MAX("DocEntry") AS "DocEntry", "U_SrcNum"
		   FROM "OINV"
		   GROUP BY "U_SrcNum") T3 ON T3."U_SrcNum"=T0."DocNum"
LEFT JOIN (SELECT "U_FID", MAX("DocNum") AS "DocNum"
		   FROM "ODLN"
		   WHERE "CANCELED"='N'
		   GROUP BY "U_FID") T4 ON CAST(T4."U_FID" AS NVARCHAR(50))=CAST(T0."DocNum" AS NVARCHAR(50)) --获取发货单号
WHERE T0."U_TrsType" = N'W'

UNION ALL
SELECT
	 T0."DocEntry",-- 单据ID
     T0."DocNum",  -- 单据编号
     T0."TaxDate", -- 单据日期
     T0."CardCode",-- 客户编码
     T0."CardName",-- 客户名称
     CAST(T0."CntctCode" AS NVARCHAR(100)) AS "CntctCode",-- 收货人（联系人）
     T1."Name" AS "CntctName",-- 收货人名称
     T3."DocEntry" AS "InvDocEntry",-- 开票单ID
     T4."DocNum" AS "TakeGoodsNo",-- 发货单号
     T0."U_WORDREntry" AS "WeighNo",-- 过磅单号
     T0."U_CarNo" AS "CarNo",-- 车牌号
     T2."LineTotal" AS "TotalAmt",-- 总计
     --总的结算金额-收款方式金额-使用的折扣
     T2."LineTotal" - (IFNULL(T0."U_Z028",0)+IFNULL(T0."U_Z030",0)+IFNULL(T0."U_Z032",0)
                      +IFNULL(T0."U_Z034",0)) AS "ArrearsAmt",-- 欠款
     N'D' AS "DocStatus",-- 单据状态
     N'草稿' AS "DocStatsName",
     T0."U_DesOrderNum" AS "DesOrderNum",--订料单号
     T0."U_PriceOrderNum" AS "PriceOrderNum",--保价单号
     MAP("U_TranType",'A','自运','B','代运') AS "TranType",-- 运输方式
     MAP("U_BusiType",'A','工厂司机拉货','B','客户拉货','C','无运费') AS "BusiType",-- 业务类型
	 T0."BPLId",--销售分支
     T0."BPLId" AS "TakeBPLId"--提货分支
FROM "ODRF" T0
LEFT JOIN "OCPR" T1 ON T1."CntctCode"=T0."CntctCode" -- 获取收货人
LEFT JOIN (SELECT "DocEntry", SUM("LineTotal") AS "LineTotal"
		   FROM "DRF1"
		   GROUP BY "DocEntry")T2 ON T2."DocEntry"=T0."DocEntry" --汇总金额
LEFT JOIN (SELECT MAX("DocEntry") AS "DocEntry","U_SrcNum"
		   FROM "OINV"
		   GROUP BY "U_SrcNum") T3 ON T3."U_SrcNum"=T0."DocNum"
LEFT JOIN (SELECT "U_FID",MAX("DocNum") AS "DocNum"
		   FROM "ODLN"
	       WHERE "CANCELED"='N'
		   GROUP BY "U_FID") T4 ON CAST(T4."U_FID" AS NVARCHAR(50))=CAST(T0."DocNum" AS NVARCHAR(50)) --获取发货单号
WHERE T0."ObjType"=N'17' AND T0."DocStatus"=N'O'
AND T0."CANCELED"=N'N' AND T0."U_TrsType" = N'W'
AND NOT EXISTS (SELECT "draftKey" FROM "ORDR" WHERE "draftKey" =T0."DocEntry");