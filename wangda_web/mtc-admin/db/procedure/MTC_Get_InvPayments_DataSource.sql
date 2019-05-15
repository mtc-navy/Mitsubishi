DROP PROCEDURE "MTC_Get_InvPayments_DataSource";
CREATE PROCEDURE "MTC_Get_InvPayments_DataSource"
(
  DocEntry INTEGER --销售订单DocEntry
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  -- 收款方式1
  SELECT
    T0."CardCode",--客户编码
    T0."CardName",--客户名称
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "DocDate",--过账日期
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "DocDueDate",--到期日
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "TaxDate",--单据日期
    T0."BPLId" AS "BPLID",--分支
    T1."TaxIdNum" AS "VATRegNum",--分支登记号
    CASE WHEN T6."U_PayMethType"='1' THEN T6."U_DefAcct" ELSE N'' END AS "TransferAccount",--银行转账科目
    CASE WHEN T6."U_PayMethType"='2' THEN T6."U_DefAcct" ELSE N'' END AS "CashAccount",--现金科目
    T2."DebPayAcct" AS "ControlAccount",--控制科目
    CASE WHEN T6."U_PayMethType"='1' THEN T0."U_Z028" ELSE CAST(0 AS DECIMAL(19,6)) END AS "TransferSum",--应付总额
    CASE WHEN T6."U_PayMethType"='2' THEN T0."U_Z028" ELSE CAST(0 AS DECIMAL(19,6)) END AS "CashSum",--应付总额
    CASE WHEN T7."Finanse"='Y' THEN T8."CfwInDflt" ELSE 0 END AS "CashFlowLineItemID",--现金流项目
    T0."U_Z040" AS "PayType",--收款方式
    T0."DocNum" AS "BaseRef", --基本参考
    T6."U_BankID" AS "BankCard",--银行账号
    T6."U_BankName" AS "BankName",--开户银行
    T6."U_ZHBankName" AS "ZHBankName", --支行名称
    MAP(T6."U_PayMethType",'1','pmtBankTransfer','2','pmtCash') AS "PaymentMeans" --付款类型
  FROM "ORDR" T0
  INNER JOIN "OBPL" T1 ON T1."BPLId"=T0."BPLId"
  INNER JOIN "OCRD" T2 ON T2."CardCode"=T0."CardCode"
  LEFT JOIN "@U_CRD8" T3 ON T3."U_CardCode"=T0."CardCode" AND T3."U_BPLId"=T0."BPLId"
  LEFT JOIN "OPLN" T4 ON T4."ListNum"=T3."U_ListNum" --关联价格清单
  LEFT JOIN "@U_BANKINFO1" T5 ON T5."Code"=T0."BPLId" --关联银行账号信息
  LEFT JOIN "@U_BANKINFO2" T6 On T6."Code"=T5."Code" AND T6."U_PayMethName"=T0."U_Z040" AND T6."U_CMethType"=N'1'
    AND T6."U_PayMethName" IS NOT NULL AND T6."U_BankID" IS NOT NULL
    AND IFNULL(T3."U_ListNum",0)=IFNULL(T6."U_FFCCode",IFNULL(T3."U_ListNum",0))
  LEFT JOIN "OACT" T7 ON T7."AcctCode"=T6."U_DefAcct"  --检查是否为现金科目
  LEFT JOIN "OADM" T8 ON 1=1 -- 获取常规设定的收款现金流项目
  WHERE T0."DocEntry"=:DocEntry AND T0."U_Z028">0 AND IFNULL(T0."U_Z050",'')=N''

  UNION ALL

  -- 收款方式2
  SELECT
    T0."CardCode",--客户编码
    T0."CardName",--客户名称
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "DocDate",--过账日期
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "DocDueDate",--到期日
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "TaxDate",--单据日期
    T0."BPLId" AS "BPLID",--分支
    T1."TaxIdNum" AS "VATRegNum",--分支登记号
    CASE WHEN T6."U_PayMethType"='1' THEN T6."U_DefAcct" ELSE N'' END AS "TransferAccount",--银行转账科目
    CASE WHEN T6."U_PayMethType"='2' THEN T6."U_DefAcct" ELSE N'' END AS "CashAccount",--现金科目
    T2."DebPayAcct" AS "ControlAccount",--控制科目
    CASE WHEN T6."U_PayMethType"='1' THEN T0."U_Z030" ELSE CAST(0 AS DECIMAL(19,6)) END AS "TransferSum",--应付总额
    CASE WHEN T6."U_PayMethType"='2' THEN T0."U_Z030" ELSE CAST(0 AS DECIMAL(19,6)) END AS "CashSum",--应付总额
    CASE WHEN T7."Finanse"='Y' THEN T8."CfwInDflt" ELSE 0 END AS "CashFlowLineItemID",--现金流项目
    T0."U_Z041" AS "PayType",--收款方式
    T0."DocNum" AS "BaseRef", --基本参考
    T6."U_BankID" AS "BankCard",--银行账号
    T6."U_BankName" AS "BankName",--开户银行
    T6."U_ZHBankName" AS "ZHBankName", --支行名称
    MAP(T6."U_PayMethType",'1','pmtBankTransfer','2','pmtCash') AS "PaymentMeans" --付款类型
  FROM "ORDR" T0
  INNER JOIN "OBPL" T1 ON T1."BPLId"=T0."BPLId"
  INNER JOIN "OCRD" T2 ON T2."CardCode"=T0."CardCode"
  LEFT JOIN "@U_CRD8" T3 ON T3."U_CardCode"=T0."CardCode" AND T3."U_BPLId"=T0."BPLId"
  LEFT JOIN "OPLN" T4 ON T4."ListNum"=T3."U_ListNum" --关联价格清单
  LEFT JOIN "@U_BANKINFO1" T5 ON T5."Code"=T0."BPLId" --关联银行账号信息
  LEFT JOIN "@U_BANKINFO2" T6 On T6."Code"=T5."Code" AND T6."U_PayMethName"=T0."U_Z041" AND T6."U_CMethType"=N'1'
    AND T6."U_PayMethName" IS NOT NULL AND T6."U_BankID" IS NOT NULL
    AND IFNULL(T3."U_ListNum",0)=IFNULL(T6."U_FFCCode",IFNULL(T3."U_ListNum",0))
  LEFT JOIN "OACT" T7 ON T7."AcctCode"=T6."U_DefAcct"  --检查是否为现金科目
  LEFT JOIN "OADM" T8 ON 1=1 -- 获取常规设定的收款现金流项目
  WHERE T0."DocEntry"=:DocEntry AND T0."U_Z030">0 AND IFNULL(T0."U_Z051",'')=N''

  UNION ALL

  -- 收款方式3
  SELECT
    T0."CardCode",--客户编码
    T0."CardName",--客户名称
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "DocDate",--过账日期
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "DocDueDate",--到期日
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "TaxDate",--单据日期
    T0."BPLId" AS "BPLID",--分支
    T1."TaxIdNum" AS "VATRegNum",--分支登记号
    CASE WHEN T6."U_PayMethType"='1' THEN T6."U_DefAcct" ELSE N'' END AS "TransferAccount",--银行转账科目
    CASE WHEN T6."U_PayMethType"='2' THEN T6."U_DefAcct" ELSE N'' END AS "CashAccount",--现金科目
    T2."DebPayAcct" AS "ControlAccount",--控制科目
    CASE WHEN T6."U_PayMethType"='1' THEN T0."U_Z032" ELSE CAST(0 AS DECIMAL(19,6)) END AS "TransferSum",--应付总额
    CASE WHEN T6."U_PayMethType"='2' THEN T0."U_Z032" ELSE CAST(0 AS DECIMAL(19,6)) END AS "CashSum",--应付总额
    CASE WHEN T7."Finanse"='Y' THEN T8."CfwInDflt" ELSE 0 END AS "CashFlowLineItemID",--现金流项目
    T0."U_Z042" AS "PayType",--收款方式
    T0."DocNum" AS "BaseRef", --基本参考
    T6."U_BankID" AS "BankCard",--银行账号
    T6."U_BankName" AS "BankName",--开户银行
    T6."U_ZHBankName" AS "ZHBankName", --支行名称
    MAP(T6."U_PayMethType",'1','pmtBankTransfer','2','pmtCash') AS "PaymentMeans" --付款类型
  FROM "ORDR" T0
  INNER JOIN "OBPL" T1 ON T1."BPLId"=T0."BPLId"
  INNER JOIN "OCRD" T2 ON T2."CardCode"=T0."CardCode"
  LEFT JOIN "@U_CRD8" T3 ON T3."U_CardCode"=T0."CardCode" AND T3."U_BPLId"=T0."BPLId"
  LEFT JOIN "OPLN" T4 ON T4."ListNum"=T3."U_ListNum" --关联价格清单
  LEFT JOIN "@U_BANKINFO1" T5 ON T5."Code"=T0."BPLId" --关联银行账号信息
  LEFT JOIN "@U_BANKINFO2" T6 On T6."Code"=T5."Code" AND T6."U_PayMethName"=T0."U_Z042" AND T6."U_CMethType"=N'1'
    AND T6."U_PayMethName" IS NOT NULL AND T6."U_BankID" IS NOT NULL
    AND IFNULL(T3."U_ListNum",0)=IFNULL(T6."U_FFCCode",IFNULL(T3."U_ListNum",0))
  LEFT JOIN "OACT" T7 ON T7."AcctCode"=T6."U_DefAcct"  --检查是否为现金科目
  LEFT JOIN "OADM" T8 ON 1=1 -- 获取常规设定的收款现金流项目
  WHERE T0."DocEntry"=:DocEntry AND T0."U_Z032">0 AND IFNULL(T0."U_Z052",'')=N''

  UNION ALL

  -- 收款方式4
  SELECT
    T0."CardCode",--客户编码
    T0."CardName",--客户名称
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "DocDate",--过账日期
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "DocDueDate",--到期日
    CAST(CURRENT_DATE AS NVARCHAR(10)) AS "TaxDate",--单据日期
    T0."BPLId" AS "BPLID",--分支
    T1."TaxIdNum" AS "VATRegNum",--分支登记号
    CASE WHEN T6."U_PayMethType"='1' THEN T6."U_DefAcct" ELSE N'' END AS "TransferAccount",--银行转账科目
    CASE WHEN T6."U_PayMethType"='2' THEN T6."U_DefAcct" ELSE N'' END AS "CashAccount",--现金科目
    T2."DebPayAcct" AS "ControlAccount",--控制科目
    CASE WHEN T6."U_PayMethType"='1' THEN T0."U_Z034" ELSE CAST(0 AS DECIMAL(19,6)) END AS "TransferSum",--应付总额
    CASE WHEN T6."U_PayMethType"='2' THEN T0."U_Z034" ELSE CAST(0 AS DECIMAL(19,6)) END AS "CashSum",--应付总额
    CASE WHEN T7."Finanse"='Y' THEN T8."CfwInDflt" ELSE 0 END AS "CashFlowLineItemID",--现金流项目
    T0."U_Z043" AS "PayType",--收款方式
    T0."DocNum" AS "BaseRef", --基本参考
    T6."U_BankID" AS "BankCard",--银行账号
    T6."U_BankName" AS "BankName",--开户银行
    T6."U_ZHBankName" AS "ZHBankName", --支行名称
    MAP(T6."U_PayMethType",'1','pmtBankTransfer','2','pmtCash') AS "PaymentMeans" --付款类型
  FROM "ORDR" T0
  INNER JOIN "OBPL" T1 ON T1."BPLId"=T0."BPLId"
  INNER JOIN "OCRD" T2 ON T2."CardCode"=T0."CardCode"
  LEFT JOIN "@U_CRD8" T3 ON T3."U_CardCode"=T0."CardCode" AND T3."U_BPLId"=T0."BPLId"
  LEFT JOIN "OPLN" T4 ON T4."ListNum"=T3."U_ListNum" --关联价格清单
  LEFT JOIN "@U_BANKINFO1" T5 ON T5."Code"=T0."BPLId" --关联银行账号信息
  LEFT JOIN "@U_BANKINFO2" T6 On T6."Code"=T5."Code" AND T6."U_PayMethName"=T0."U_Z043" AND T6."U_CMethType"=N'1'
    AND T6."U_PayMethName" IS NOT NULL AND T6."U_BankID" IS NOT NULL
    AND IFNULL(T3."U_ListNum",0)=IFNULL(T6."U_FFCCode",IFNULL(T3."U_ListNum",0))
  LEFT JOIN "OACT" T7 ON T7."AcctCode"=T6."U_DefAcct"  --检查是否为现金科目
  LEFT JOIN "OADM" T8 ON 1=1 -- 获取常规设定的收款现金流项目
  WHERE T0."DocEntry"=:DocEntry AND T0."U_Z034">0 AND IFNULL(T0."U_Z053",'')=N'';

END;
