DROP PROCEDURE "MTC_Get_Discount_DataSource";
CREATE PROCEDURE "MTC_Get_Discount_DataSource"
(
  CardCode NVARCHAR(50), --客户编码
  UserName NVARCHAR(50), --用户名
  BPLId NVARCHAR(10) DEFAULT '0',--分支
  DocEntry NVARCHAR(10) DEFAULT '',--销售单号
  FilterValue NVARCHAR(100) DEFAULT '' -- 过滤条件
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  IF :DocEntry=N'' THEN
  BEGIN
      --新增状态下带出余额数据
	  SELECT
	    N'B00'||ROW_NUMBER() OVER() AS "DiscCode",--折扣代码
		TT."DiscTypeDes" AS "DiscName",--折扣名称
		TT."AvaiAmt" - CASE WHEN "ID" = 1 THEN TT."UsedAmt" ELSE 0 END AS "CanUserAmt",--可用余额
        0 AS "ThisUserAmt"--本次使用
	  FROM
	  (
		  SELECT
		    ROW_NUMBER() OVER(PARTITION BY D0."U_DiscType" ORDER BY D0."U_DiscType") AS "ID",
		    (CASE D0."U_DiscType" WHEN '-' THEN T0."DiscCode" ELSE D0."U_DiscType" END) AS "DiscType",
		    CASE D0."U_DiscType" WHEN 'M' THEN '月折'
		                         WHEN 'Y' THEN '年折'
		                         WHEN 'Q' THEN '季折'
		                         WHEN 'D' THEN '现折' ELSE D0."Name" END "DiscTypeDes",
		    T0."AvaiAmt",
		    "U_F_OrdrUseAmt"(T0."BPLId",-1, T0."DiscCode", T0."CardCode" ) AS "UsedAmt"
		  FROM U_SCBD1 T0
		  INNER JOIN "@U_SODIC" D0 ON T0."DiscCode" = D0."Code"
		  WHERE T0."CardCode" = :CardCode
		    AND T0."BPLId" = :BPLId
		    AND IFNULL( T0."AvaiAmt", 0 ) <> 0
		    AND IFNULL( D0."U_FieldValue", '' ) <> ''
	  ) TT;

  END;
  ELSE
  BEGIN
    -- 修改模式下带出对应的数据

    --Setp1:获取余额信息
    TMP_DISC =
    SELECT
	  TT."DiscTypeDes" AS "DiscName",--折扣名称
	  TT."AvaiAmt" - CASE WHEN "ID" = 1 THEN TT."UsedAmt" ELSE 0 END AS "CanUserAmt"--可用余额
	FROM
	(
	  SELECT
	    ROW_NUMBER() OVER(PARTITION BY D0."U_DiscType" ORDER BY D0."U_DiscType") AS "ID",
		(CASE D0."U_DiscType" WHEN '-' THEN T0."DiscCode" ELSE D0."U_DiscType" END) AS "DiscType",
		CASE D0."U_DiscType" WHEN 'M' THEN '月折'
			                 WHEN 'Y' THEN '年折'
			                 WHEN 'Q' THEN '季折'
			                 WHEN 'D' THEN '现折' ELSE D0."Name" END "DiscTypeDes",
		T0."AvaiAmt",
		"U_F_OrdrUseAmt"(T0."BPLId",:DocEntry, T0."DiscCode", T0."CardCode" ) AS "UsedAmt"
	  FROM U_SCBD1 T0
	  INNER JOIN "@U_SODIC" D0 ON T0."DiscCode" = D0."Code"
	  WHERE T0."CardCode" = :CardCode
	    AND T0."BPLId" = :BPLId
		AND IFNULL( T0."AvaiAmt", 0 ) <> 0
		AND IFNULL( D0."U_FieldValue", '' ) <> ''
	) TT;

    --获取订单折扣信息
    TMP_ORDR =
    SELECT
       N'B00'||ROW_NUMBER() OVER() AS "DiscCode",--折扣代码
       MAIN."DiscName",--折扣名称
       IFNULL(TT."CanUserAmt", MAIN."CanUserAmt") AS "CanUserAmt",--可用余额
       MAIN."ThisUserAmt"--本次使用
    FROM(
  	   SELECT
  	     N'随单折' AS "DiscName",--折扣名称
         IFNULL(T0."U_Z006",0) AS "CanUserAmt",--可用余额
         IFNULL(T0."U_Z006",0) AS "ThisUserAmt"--本次使用
  	   FROM "ORDR" T0
  	   WHERE T0."DocEntry"=:DocEntry AND IFNULL(T0."U_Z006",0)>0
  	   UNION ALL
  	   SELECT
  	     N'年折' AS "DiscName",--折扣名称
         IFNULL(T0."U_Z008",0) AS "CanUserAmt",--可用余额
         IFNULL(T0."U_Z008",0) AS "ThisUserAmt"--本次使用
  	   FROM "ORDR" T0
  	   WHERE T0."DocEntry"=:DocEntry AND IFNULL(T0."U_Z008",0)>0
  	   UNION ALL
  	   SELECT
  	     N'月折' AS "DiscName",--折扣名称
         IFNULL(T0."U_Z009",0) AS "CanUserAmt",--可用余额
         IFNULL(T0."U_Z009",0) AS "ThisUserAmt"--本次使用
  	   FROM "ORDR" T0
  	   WHERE T0."DocEntry"=:DocEntry AND IFNULL(T0."U_Z009",0)>0
  	   UNION ALL
  	   SELECT
  	     N'季折' AS "DiscName",--折扣名称
         IFNULL(T0."U_Z010",0) AS "CanUserAmt",--可用余额
         IFNULL(T0."U_Z010",0) AS "ThisUserAmt"--本次使用
  	   FROM "ORDR" T0
  	   WHERE T0."DocEntry"=:DocEntry AND IFNULL(T0."U_Z010",0)>0
  	   UNION ALL
  	   SELECT
  	     N'现金折扣' AS "DiscName",--折扣名称
         IFNULL(T0."U_Z012",0) AS "CanUserAmt",--可用余额
         IFNULL(T0."U_Z012",0) AS "ThisUserAmt"--本次使用
  	   FROM "ORDR" T0
  	   WHERE T0."DocEntry"=:DocEntry AND IFNULL(T0."U_Z012",0)>0
  	   UNION ALL
  	   SELECT
  	     N'任务折' AS "DiscName",--折扣名称
         IFNULL(T0."U_Z003",0) AS "CanUserAmt",--可用余额
         IFNULL(T0."U_Z003",0) AS "ThisUserAmt"--本次使用
  	   FROM "ORDR" T0
  	   WHERE T0."DocEntry"=:DocEntry AND IFNULL(T0."U_Z003",0)>0
  	   )MAIN
  	 LEFT JOIN :TMP_DISC TT ON TT."DiscName"=MAIN."DiscName";

	 --拼接未发生折扣且有余额的信息
     SELECT
        N'B00'||ROW_NUMBER() OVER() AS "DiscCode",--折扣代码
       "DiscName","CanUserAmt","ThisUserAmt"
     FROM (
     SELECT "DiscName","CanUserAmt","ThisUserAmt"
     FROM :TMP_ORDR
     UNION ALL
     SELECT "DiscName","CanUserAmt",0 AS "ThisUserAmt"
     FROM :TMP_DISC T0
     WHERE T0."DiscName" NOT IN(SELECT "DiscName" FROM :TMP_ORDR))MAIN;

  END;
  END IF;
END;
