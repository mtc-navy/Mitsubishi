DROP PROCEDURE "MTC_Get_ItemPrice";
CREATE PROCEDURE "MTC_Get_ItemPrice"
(
  CardCode NVARCHAR(50),
  ItemCode NVARCHAR(50),
  DocDate DATE,
  BPLId NVARCHAR(10)
)
AS
BEGIN

    SELECT
      T0."ItemCode",
      T1."Price" AS "Price",--出厂价
      T2."CurrDisc",--现折
      T0."U_IsPackage" AS "IsPackage", --是否标包
      T0."SalFactor1", --单包重
      T3."WhsCode", --仓库代码
      T4."WhsName" --仓库名称
    FROM "OITM" T0
    LEFT JOIN
    (
	    /* 销售订单单价获取:外部客户取客户对应价格清单，内部客户取自定义表分支对应价格清单里面的物料价格 */
	    SELECT IFNULL(I1."Price",0) AS "Price",I1."ItemCode" AS "ItemCode"
	    FROM OCRD C0
	    JOIN OPLN P0 ON P0."ListNum" = C0."ListNum" AND C0."QryGroup2" <> 'Y' AND C0."QryGroup3" <> 'Y'
	    JOIN ITM1 I1 ON I1."PriceList" = P0."ListNum"
	    WHERE C0."CardCode" = :CardCode  AND I1."ItemCode" = :ItemCode

	    UNION ALL

	    SELECT IFNULL(I1."Price",0) AS "Price",I1."ItemCode" AS "ItemCode"
	    FROM "@U_CRD8" C8
	    JOIN OCRD C0 ON C0."CardCode" = C8."U_CardCode"
	    JOIN OPLN P0 ON P0."ListNum" = C8."U_ListNum" AND C8."U_BPLId" = :BPLId AND (C0."QryGroup2" = 'Y' OR C0."QryGroup3" = 'Y')
	    JOIN ITM1 I1 ON I1."PriceList" = C8."U_ListNum"
	    WHERE C0."CardCode" = :CardCode
	      AND I1."ItemCode" = :ItemCode
	      AND C8."U_ListNum" <>0
	      AND C0."CardCode"||I1."ItemCode"||C8."U_BPLId"
	          NOT  IN (SELECT T0."U_CardCode"||T1."U_ItemCode"||T0."U_BPLId"
	                   FROM "@U_ASSESSP" T0
	                   JOIN "@U_ASSESSP1" T1 ON T0."DocEntry"=T1."DocEntry"
	                   JOIN OFPR T2 ON T0."U_DurDate"=T2."Code"
	                   WHERE :DocDate BETWEEN T2."F_RefDate" AND T2."T_RefDate")

	    UNION ALL

	    SELECT CASE WHEN IFNULL(T1."U_LossCost",0)=0 AND C0."QryGroup2" = 'Y' AND  C0."QryGroup3" <> 'Y'
	                  THEN (IFNULL(U0."RMCost",0)+IFNULL(T1."U_ProductCost",0)+IFNULL(T1."U_ComCost",0)+IFNULL(T1."U_QtyCost",0)+IFNULL(T1."U_AddCost",0))/1000
	                WHEN IFNULL(T1."U_LossCost",0)<>0 AND C0."QryGroup2" = 'Y' AND  C0."QryGroup3" <> 'Y'
	                  THEN (IFNULL(U0."RMCost",0)+IFNULL(U0."RMCost",0)*(IFNULL(T1."U_LossCost",0)/100)+IFNULL(T1."U_ProductCost",0)+IFNULL(T1."U_ComCost",0)+IFNULL(T1."U_QtyCost",0)+IFNULL(T1."U_AddCost",0))/1000
	                WHEN IFNULL(T1."U_LossCost",0)=0 AND C0."QryGroup2" <> 'Y' AND  C0."QryGroup3" = 'Y'
	                  THEN (IFNULL(U0."RMCOST2",0)+IFNULL(T1."U_ProductCost",0)+IFNULL(T1."U_ComCost",0)+IFNULL(T1."U_QtyCost",0)+IFNULL(T1."U_AddCost",0))/1000
	                WHEN IFNULL(T1."U_LossCost",0)<>0 AND C0."QryGroup2" <> 'Y' AND  C0."QryGroup3" = 'Y'
	                  THEN (IFNULL(U0."RMCOST2",0)+IFNULL(U0."RMCost",0)*(IFNULL(T1."U_LossCost",0)/100)+IFNULL(T1."U_ProductCost",0)+IFNULL(T1."U_ComCost",0)+IFNULL(T1."U_QtyCost",0)+IFNULL(T1."U_AddCost",0))/1000
	           END AS "Price",T1."U_ItemCode" AS "ItemCode"
	    FROM "@U_ASSESSP" T0
	    JOIN "@U_ASSESSP1" T1 ON T0."DocEntry"=T1."DocEntry"
	    JOIN "OFPR" T2 ON T0."U_DurDate"=T2."Code"
	    JOIN (SELECT "BPLId","RMCost","ItemCode","RMCOST2"
	          FROM "MTC_CO_RealTimeCost"
	          WHERE "DocDate"= ADD_DAYS(:DocDate,-1))U0 ON T0."U_BPLId"=U0."BPLId" AND T1."U_ItemCode"=U0."ItemCode"
	    JOIN OCRD C0 ON T0."U_CardCode"=C0."CardCode"
	    WHERE :DocDate BETWEEN T2."F_RefDate" AND T2."T_RefDate"
	      AND T0."U_BPLId"= :BPLId AND T0."U_CardCode"=:CardCode
	      AND (C0."QryGroup2" = 'Y' OR C0."QryGroup3" = 'Y')
	      AND T1."U_ItemCode"=:ItemCode
	      AND C0."CardCode"||T1."U_ItemCode"||T0."U_BPLId"
	          IN (SELECT T0."U_CardCode"||T1."U_ItemCode"||T0."U_BPLId"
	              FROM "@U_ASSESSP" T0
	              JOIN "@U_ASSESSP1" T1 ON T0."DocEntry"=T1."DocEntry")
    )T1 ON T1."ItemCode"=T0."ItemCode"
    LEFT JOIN
    (
	    SELECT T2."U_ItemCode" AS "ItemCode",IFNULL(T2."U_DiscNum4",0) AS "CurrDisc"
	    FROM "@U_SODSR" T1
	    INNER JOIN "@U_SDSR1" T2 ON T1."DocEntry" = T2."DocEntry" AND T1."U_DiscCode" ='Z005'
	    WHERE T2."U_ItemCode"=:ItemCode AND T1."U_CardCode"=:CardCode AND T1."U_BPLId"=:BPLId
    )T2 ON T2."ItemCode"=T0."ItemCode"
    LEFT JOIN
    (
        SELECT
          CASE WHEN IFNULL(T1."U_WhsCode",'')='' THEN T2."U_RDR1" ELSE T1."U_WhsCode" END "WhsCode"
        FROM "OCRD" T0
        LEFT JOIN "OPLN" T1 ON T0."ListNum"=T1."ListNum"
        LEFT JOIN
	    (
	      SELECT T2."U_RDR1"
	      FROM "OITM" T0
	      LEFT JOIN "@U_CIWS1" T2 ON T0."ItmsGrpCod"  = T2."U_ItmsGrpCod"
	      LEFT JOIN "OBPL" T3 ON T3."TaxIdNum"=T2."Code"
	      WHERE T0."ItemCode" = :ItemCode AND T3."BPLId" = :BPLId
	    ) T2 ON 1=1
        WHERE T0."CardCode"= :CardCode LIMIT 1
    )T3 ON 1=1
    LEFT JOIN "OWHS" T4 ON T4."WhsCode"=T3."WhsCode"
    WHERE T0."ItemCode"=:ItemCode
    ;

END;
