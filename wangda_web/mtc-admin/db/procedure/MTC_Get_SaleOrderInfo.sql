DROP PROCEDURE "MTC_Get_SaleOrderInfo";
CREATE PROCEDURE "MTC_Get_SaleOrderInfo"
(
  DocEntry INTEGER,
  IsMaster NVARCHAR(10), -- Y:查询主数据 N：查询明细数据
  IsDraft NVARCHAR(10) --Y:草稿 N:正式单据
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  --查询销售订单
  IF :IsDraft=N'N' THEN

    --查询主数据
    IF :IsMaster=N'Y' THEN
    BEGIN
      SELECT
        --表头信息
        T0."DocEntry",--DocEntry
        T0."DocNum",--单据号
        T0."CardCode",--客户代码
        T0."CardName",--客户名称
        CAST(T0."CntctCode" AS NVARCHAR(100)) AS "CntctPrsn",--收货人编码
        T2."Name" AS "CntctName",--收货人名称
        T0."TaxDate",--单据日期
        CASE WHEN T0."CANCELED"=N'Y' THEN N'C'
             WHEN IFNULL(CAST(T1."DocNum" AS NVARCHAR(50)),'')!=N'' THEN N'Y'
             WHEN T0."DocStatus"=N'O' THEN N'W'
             ELSE N'' END "DocStatus", -- 单据状态
        CASE WHEN T0."CANCELED"=N'Y' THEN N'取消'
             WHEN IFNULL(CAST(T1."DocNum" AS NVARCHAR(50)),'')!=N'' THEN N'已发货'
             WHEN T0."DocStatus"=N'O' THEN N'未发货'
             ELSE N'' END AS "DocStatsName",

         --常规信息
        T0."U_Creator" AS "Creator",--制单人
        LEFT(T0."U_CreateTime",10) AS "CreateDate",--制单日期
        RIGHT(T0."U_CreateTime",8) AS "CreateTime",--制单时间
        T0."U_Printor" AS "Printor",--打印人
        LEFT(T0."U_PrintTime",10) AS "PrintDate",--打印日期
        RIGHT(T0."U_PrintTime",8) AS "PrintTime",--打印时间
        T0."U_PrintNum" AS "PrintNum",--打印次数

        --销售信息
        T0."U_CarNo" AS "CarNo",--车牌号
        T0."U_Pound" AS "BoundNo",--过磅单号
        T0."BPLId",--销售分支
        T3."BPLName",--销售分支名称
        T0."VATRegNum" AS "TaxIdNum",--销售分支登记号
        T0."U_TakeBPLId" AS "TakeBPLId",--提货分支
        T4."BPLName" AS "TakeBPLName",--提货分支名称
        CAST(T0."U_SrcNum" AS NVARCHAR(50)) AS "SrcNum",--源单编号

        IFNULL(T5."CreditLine",0) AS "CanUseCost",--可用额度
        CAST(0 AS DECIMAL) AS "InOutAmt",--往来款
        CAST(0 AS DECIMAL) AS "DiscAmt",--折扣余额
        CAST(0 AS DECIMAL) AS "CanUseAmt",--可用金额
        T0."U_InlineNo" AS "InlineNo",--内联单号
        T1."DocNum" AS "InvDocNum",--发货单号
        T0."U_TakeNo" AS "TakeNum",--提货单号

        --收款信息
        T0."U_Z040" AS "Z040",--收款方式1
        T0."U_Z028" AS "Z028",--收款方式1金额
        T0."U_Z041" AS "Z041",--收款方式2
        T0."U_Z030" AS "Z030",--收款方式2金额
        T0."U_Z042" AS "Z042",--收款方式3
        T0."U_Z032" AS "Z032",--收款方式3金额
        T0."U_Z043" AS "Z043",--收款方式4
        T0."U_Z034" AS "Z034",--收款方式4金额
        T0."Comments" AS "Remark"--备注
      FROM "ORDR" T0
      LEFT JOIN (SELECT "U_SrcNum",MAX("DocNum") AS "DocNum" FROM "ODLN" GROUP BY "U_SrcNum") T1
           ON CAST(T1."U_SrcNum" AS NVARCHAR(50))=CAST(T0."DocNum" AS NVARCHAR(50)) --获取开票单号
      LEFT JOIN "OCPR" T2 ON T2."CntctCode"=T0."CntctCode" -- 获取收货人
      LEFT JOIN "OBPL" T3 ON T3."BPLId"=T0."BPLId"
      LEFT JOIN "OBPL" T4 ON T4."BPLId"=T0."U_TakeBPLId"
      LEFT JOIN "OCRD" T5 ON T5."CardCode"=T0."CardCode"
      WHERE T0."DocEntry"=:DocEntry;
    END;
    END IF;

    --查询明细数据
    IF :IsMaster=N'N' THEN
    BEGIN
      SELECT
        T0."LineNum",--行号
        T0."ItemCode",--物料代码
        T0."Dscription" AS "ItemName",--物料名称
        T0."Quantity",--数量
        T0."Factor1" AS "SalFactor1",--单包重
        T0."Factor2" AS "PackNum",--包数
        T0."Price",--出厂价
        IFNULL(T0."U_BaseDisc",0) AS "CurrDisc",--现折
        T0."U_IsStdPkg" AS "IsPackage",--是否标包
        T0."U_DiscOrder" AS "DiscOrder",--赠包唯一序列号
        IFNULL(T0."Quantity",0.0)*(IFNULL(T0."Price",0.0)-IFNULL(T0."U_BaseDisc",0.0)) AS "PayAmt",--结算金额
        T0."U_Realdisc" AS "Realdisc",--是否赠包
        T0."WhsCode" AS "WarehouseCode",--仓库
        T0."U_DItemCode" AS "DitemCode",--折扣规则物料或物料组
        T0."U_DiscEntry" AS "DiscEntry",--赠包单号
        T0."U_WhsName"--仓库名称
      FROM "RDR1" T0
      INNER JOIN "ORDR" T1 ON T1."DocEntry"=T0."DocEntry"
      WHERE T1."DocEntry"=:DocEntry;
    END;
    END IF;

  --查询销售订单草稿数据
  ELSE

  BEGIN
    --查询主数据
    IF :IsMaster=N'Y' THEN
    BEGIN
      SELECT
        --表头信息
        T0."DocEntry",--DocEntry
        T0."DocNum",--单据号
        T0."CardCode",--客户代码
        T0."CardName",--客户名称
        CAST(T0."CntctCode" AS NVARCHAR(100)) AS "CntctPrsn",--收货人编码
        T2."Name" AS "CntctName",--收货人名称
        T0."TaxDate",--单据日期
        N'D' AS "DocStatus", -- 单据状态
        N'草稿' AS "DocStatsName",

         --常规信息
        T0."U_Creator" AS "Creator",--制单人
        LEFT(T0."U_CreateTime",10) AS "CreateDate",--制单日期
        RIGHT(T0."U_CreateTime",8) AS "CreateTime",--制单时间
        T0."U_Printor" AS "Printor",--打印人
        LEFT(T0."U_PrintTime",10) AS "PrintDate",--打印日期
        RIGHT(T0."U_PrintTime",8) AS "PrintTime",--打印时间
        T0."U_PrintNum" AS "PrintNum",--打印次数

        --销售信息
        T0."U_CarNo" AS "CarNo",--车牌号
        T0."U_Pound" AS "BoundNo",--过磅单号
        T0."BPLId",--销售分支
        T3."BPLName",--销售分支名称
        T0."VATRegNum" AS "TaxIdNum",--销售分支登记号
        T0."U_TakeBPLId" AS "TakeBPLId",--提货分支
        T4."BPLName" AS "TakeBPLName",--提货分支名称
        CAST(T0."U_SrcNum" AS NVARCHAR(50)) AS "SrcNum",--源单编号

        IFNULL(T5."CreditLine",0) AS "CanUseCost",--可用额度
        CAST(0 AS DECIMAL) AS "InOutAmt",--往来款
        CAST(0 AS DECIMAL) AS "DiscAmt",--折扣余额
        CAST(0 AS DECIMAL) AS "CanUseAmt",--可用金额
        T0."U_InlineNo" AS "InlineNo",--内联单号
        T1."DocNum" AS "InvDocNum",--发货单号
        T0."U_TakeNo" AS "TakeNum",--提货单号

        --收款信息
        T0."U_Z040" AS "Z040",--收款方式1
        T0."U_Z028" AS "Z028",--收款方式1金额
        T0."U_Z041" AS "Z041",--收款方式2
        T0."U_Z030" AS "Z030",--收款方式2金额
        T0."U_Z042" AS "Z042",--收款方式3
        T0."U_Z032" AS "Z032",--收款方式3金额
        T0."U_Z043" AS "Z043",--收款方式4
        T0."U_Z034" AS "Z034",--收款方式4金额
        T0."Comments" AS "Remark"--备注
      FROM "ODRF" T0
      LEFT JOIN "ODLN" T1 ON CAST(T1."U_SrcNum" AS NVARCHAR(50))=CAST(T0."DocNum" AS NVARCHAR(50)) --获取开票单号
      LEFT JOIN "OCPR" T2 ON T2."CntctCode"=T0."CntctCode" -- 获取收货人
      LEFT JOIN "OBPL" T3 ON T3."BPLId"=T0."BPLId"
      LEFT JOIN "OBPL" T4 ON T4."BPLId"=T0."U_TakeBPLId"
      LEFT JOIN "OCRD" T5 ON T5."CardCode"=T0."CardCode"
      WHERE T0."DocEntry"=:DocEntry;
    END;
    END IF;

    --查询明细数据
    IF :IsMaster=N'N' THEN
    BEGIN
      SELECT
        T0."LineNum",--行号
        T0."ItemCode",--物料代码
        T0."Dscription" AS "ItemName",--物料名称
        T0."Quantity",--数量
        T0."Factor1" AS "SalFactor1",--单包重
        T0."Factor2" AS "PackNum",--包数
        T0."Price",--出厂价
        IFNULL(T0."U_BaseDisc",0) AS "CurrDisc",--现折
        T0."U_IsStdPkg" AS "IsPackage",--是否标包
        T0."U_DiscOrder" AS "DiscOrder",--赠包唯一序列号
        IFNULL(T0."Quantity",0.0)*(IFNULL(T0."Price",0.0)-IFNULL(T0."U_BaseDisc",0.0)) AS "PayAmt",--结算金额
        T0."U_Realdisc" AS "Realdisc",--是否赠包
        T0."WhsCode" AS "WarehouseCode",--仓库
        T0."U_DItemCode" AS "DitemCode",--折扣规则物料或物料组
        T0."U_DiscEntry" AS "DiscEntry",--赠包单号
        T0."U_WhsName"--仓库名称
      FROM "DRF1" T0
      INNER JOIN "ODRF" T1 ON T1."DocEntry"=T0."DocEntry"
      WHERE T1."DocEntry"=:DocEntry;
    END;
    END IF;
  END;
  END IF;

END;
