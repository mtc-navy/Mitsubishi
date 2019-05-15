CREATE VIEW "MTC_V_SaleInvoice_Inline" ( "DocEntry",
	 "DocNum",
	 "TaxDate",
	 "CardCode",
	 "CardName",
	 "CntctCode",
	 "CntctName",
	 "InvDocEntry",
	 "TakeGoodsNo",
	 "WeighNo",
	 "CarNo",
	 "TotalAmt",
	 "ArrearsAmt",
	 "InlineNo",
	 "DocStatus",
	 "DocStatsName",
	 "BPLId",
	 "TakeBPLId" ) AS SELECT
	 T0."DocEntry",
	 -- 单据ID
 T0."DocNum",
	 -- 单据编号
 T0."TaxDate",
	 -- 单据日期
 T0."CardCode",
	 -- 客户编码
 T0."CardName",
	 -- 客户名称
 CAST(T0."CntctCode" AS NVARCHAR(100)) AS "CntctCode",
	 -- 收货人（联系人）
 T1."Name" AS "CntctName",
	 -- 收货人名称
 T0."DocEntry" AS "InvDocEntry",
	 -- 开票单ID
 T0."DocNum" AS "TakeGoodsNo",
	 -- 提货单号（开票单号）
 T0."U_Pound" AS "WeighNo",
	 -- 过磅单号
 T0."U_CarNo" AS "CarNo",
	 -- 车牌号
 T2."LineTotal" AS "TotalAmt",
	 -- 总计
 --总的结算金额-收款方式金额-使用的折扣
 T2."LineTotal" - (IFNULL(T0."U_Z028",
	0)+IFNULL(T0."U_Z030",
	0)+IFNULL(T0."U_Z032",
	0)+IFNULL(T0."U_Z034",
	0)) - (IFNULL(T0."U_Z008",
	0)+IFNULL(T0."U_Z010",
	0)+IFNULL(T0."U_Z003",
	0)) AS "ArrearsAmt",
	 -- 欠款
 T0."U_InlineNo" AS "InlineNo",
	 -- 内联交易单号
 CASE WHEN T0."CANCELED"=N'Y' 
THEN N'C' WHEN T0."DocStatus"=N'C' 
THEN N'Y' WHEN T0."DocStatus"=N'O' 
THEN N'W' 
ELSE N'' 
END "DocStatus",
	 -- 单据状态
 CASE WHEN T0."CANCELED"=N'Y' 
THEN N'取消' WHEN T0."DocStatus"=N'C' 
THEN N'已发货' WHEN T0."DocStatus"=N'O' 
THEN N'未发货' 
ELSE N'' 
END AS "DocStatsName",
	 T3."BPLId",
	--销售分支
 T3."U_TakeBPLId" AS "TakeBPLId"--提货分支

FROM "OINV" T0 
LEFT JOIN "OCPR" T1 ON T1."CntctCode"=T0."CntctCode" -- 获取收货人

LEFT JOIN (SELECT
	 "DocEntry",
	 SUM("LineTotal") AS "LineTotal" 
	FROM "RDR1" 
	GROUP BY "DocEntry")T2 ON T2."DocEntry"=T0."DocEntry" --汇总金额

LEFT JOIN "ORDR" T3 ON T3."DocNum"=T0."U_SrcNum" 
WHERE 1=1 WITH READ ONLY