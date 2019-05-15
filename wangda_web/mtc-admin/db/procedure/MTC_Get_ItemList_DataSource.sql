DROP PROCEDURE "MTC_Get_ItemList_DataSource";
CREATE PROCEDURE "MTC_Get_ItemList_DataSource"
(
  CardCode NVARCHAR(50), --客户编码
  UserName NVARCHAR(50), --用户名
  BPLId NVARCHAR(10),--分支
  FilterValue NVARCHAR(100) DEFAULT '' -- 过滤条件
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  SELECT
    T2."U_ItemName" AS "ItemName",
    T2."U_ItemCode" AS "ItemCode",
    CAST(0 AS DECIMAL(19,6)) AS "FactPrice",--出厂价
    IFNULL(T2."U_DiscNum4",0) AS "CurrDisc",--现折
    N'N' AS "IsGiveGD", -- 是否赠料
    N' AS "WhsCode",--仓库代码
    N' AS "WhsName", --仓库名称
    T3."U_IsPackage", --是否标包
    T3."SalFactor1" --单包重
  FROM "@U_SODSR" T1
  INNER JOIN "@U_SDSR1" T2 ON T1."DocEntry" = T2."DocEntry" AND T1."U_DiscCode" ='Z005'
							AND (CASE WHEN IFNULL(T1."U_TCardCode",'') = '' THEN T1."U_CardCode" ELSE T1."U_TCardCode" END) = t1."U_CardCode"
							AND t2."U_IsActive" = 'Y'  --基础折扣有可能挂靠了别的客户，需要进行排除，且物料行IsActive 必须为活跃
  LEFT JOIN "OITM" T3 ON T2."U_ItemCode"=T3."ItemCode"
  LEFT JOIN
		(
			SELECT T1."ItemCode",SUM(T1."OpenQty") "OpenQty"
			FROM ORDR T0
			JOIN RDR1 T1 ON T0."DocEntry"=T1."DocEntry"
			WHERE T1."LineStatus"='O' AND T0."CANCELED"='N'
			GROUP BY T1."ItemCode"
		) T4 ON T3."ItemCode"=T4."ItemCode"
  WHERE T1."U_CardCode" = :CardCode AND T1."U_BPLId" = :BPLId
    AND T3."OnHand"-IFNULL(T4."OpenQty",0)>0;

END;
