DROP PROCEDURE "MTC_Get_CustomerAmt";
CREATE PROCEDURE "MTC_Get_CustomerAmt"
(
  BPLId NVARCHAR(10),
  CardCode NVARCHAR(50)
)
AS
BEGIN

 --饲料
 --DECLARE Credit decimal(19,2);  --信用额度
 --DECLARE Advance decimal(19,2);  --预收款
 --DECLARE ActAdvance decimal(19,2);  --理论预收款
 --DECLARE Banlance decimal(19,2);   --欠款余额 
 DECLARE ActBalance DECIMAL(19,2); --理论欠款余额
 DECLARE MouthAmt DECIMAL(19,2) ;  --月折
 DECLARE YearAmt  DECIMAL(19,2) ;  --年折
 DECLARE QuarAmt  DECIMAL(19,2); --季折
 DECLARE RenWuAmt DECIMAL(19,2) ; --任务折
 DECLARE DiscAmt DECIMAL(19,2) ; --折扣金额 = 季折+月折+年折+任务折 
 DECLARE DiscPack DECIMAL(19,2) ;  --折扣公斤
 DECLARE CustAmt  DECIMAL(19,2); --理论可用余额

 --获取可用月折，年折,季折，任务折
 --月折
 SELECT TT.AMT - "U_F_OrdrUseAmt"(:BPLId,'-1',TT.FLD,:CardCode) INTO MouthAmt 
 FROM ( SELECT 
          IFNULL(MAX(D0."U_FieldValue"),'') AS FLD,
          IFNULL(SUM(T0."AvaiAmt"),0) AS AMT 
	    FROM U_SCBD1 T0 
	    INNER JOIN "@U_SODIC" D0 ON T0."DiscCode"=D0."Code" 
	    WHERE T0."CardCode"=:CardCode AND T0."BPLId" = :BPLId AND IFNULL(T0."AvaiAmt",0) <> 0 
		  AND IFNULL(D0."U_FieldValue",'')<>'' AND D0."U_DiscType" = 'M' 
	   ) TT  ;

 --年折
 SELECT TT.AMT - "U_F_OrdrUseAmt"(:BPLId,'-1',TT.FLD,:CardCode) INTO YearAmt 
 FROM ( SELECT 
          IFNULL(MAX(D0."U_FieldValue"),'') FLD,
          IFNULL(SUM(T0."AvaiAmt"),0)  AMT 
		FROM U_SCBD1 T0 
		INNER JOIN "@U_SODIC" D0 ON T0."DiscCode"=D0."Code" 
		WHERE T0."CardCode"=:CardCode AND T0."BPLId" = :BPLId AND IFNULL(T0."AvaiAmt",0) <> 0 
		  AND IFNULL(D0."U_FieldValue",'')<>'' AND D0."U_DiscType" = 'Y' 
	   ) TT  ;
			
  --季折
  SELECT TT.AMT - "U_F_OrdrUseAmt"(:BPLId,'-1',TT.FLD,:CardCode) INTO QuarAmt 
  FROM ( SELECT 
           IFNULL(MAX(D0."U_FieldValue"),'') FLD,
           IFNULL(Sum(T0."AvaiAmt"),0)  AMT 
		 FROM U_SCBD1 T0 
         INNER JOIN "@U_SODIC" D0 ON T0."DiscCode"=D0."Code" 
	     WHERE T0."CardCode"=:CardCode AND T0."BPLId" = :BPLId AND IFNULL(T0."AvaiAmt",0) <> 0 
		   AND IFNULL(D0."U_FieldValue",'')<>'' AND D0."U_DiscType" = 'Q' 
		) TT  ;
		
  ----任务折	 
  SELECT TT.AMT - "U_F_OrdrUseAmt"(:BPLId,'-1',TT.FLD,:CardCode) INTO RenWuAmt 
  FROM ( SELECT 
           IFNULL(MAX(D0."U_FieldValue"),'') FLD,
           IFNULL(SUM(T0."AvaiAmt"),0)  AMT 
		 FROM U_SCBD1 T0 
		 INNER JOIN "@U_SODIC" D0 ON T0."DiscCode"=D0."Code" 
		 WHERE T0."CardCode"=:CardCode AND T0."BPLId" = :BPLId AND IFNULL(T0."AvaiAmt",0) <> 0 
	       AND IFNULL(D0."U_FieldValue",'')<>'' AND D0."U_DiscType" = 'T' 
	    ) TT  ;

  --获取可用包数
  SELECT IFNULL(SUM( ROUND(t1."DiscPack",2) - IFNULL(t2."OpenCreQty" ,0)),0)  INTO DiscPack 
  FROM ( SELECT 
           "Order","DiscPack"+"DiscKG"/I0."SalFactor1" "DiscPack" 
         FROM U_SOADL A0 
         JOIN OITM I0 ON I0."ItemCode" = IFNULL(A0."DItemCode",A0."ItemCode")   
         WHERE A0."CardCode"= :CardCode AND A0."BPLId" = :BPLId AND ("DiscPack" <> 0 OR "DiscKG" <> 0 ) 
        ) t1  --可用余额表
  LEFT JOIN (SELECT 
               SUM(t1."OpenCreQty"/"Factor1") "OpenCreQty",
               t1."U_DiscOrder"
             FROM RDR1 t1 
             LEFT JOIN ORDR t2 ON t1."DocEntry" = t2."DocEntry" 
             WHERE t1."LineStatus" = 'O' AND T2.CANCELED = 'N' and t1."U_Realdisc" = '是'
             GROUP BY t1."U_DiscOrder"
             ) t2 ON t1."Order" = t2."U_DiscOrder" ; 
 
  --折扣金额 = 季折+月折+年折+任务折 
  SELECT :MouthAmt+:YearAmt+:QuarAmt+RenWuAmt INTO DiscAmt FROM DUMMY ; 			

  --获取理论欠款余额  
  SELECT
    IFNULL(SUM(A."PAY"),0) INTO ActBalance 
    --CASE WHEN IFNULL(SUM(A."PAY"),0)<0 THEN 0 ELSE IFNULL(SUM(A."PAY"),0) END INTO ActBalance
  FROM (
         SELECT 
           T0."ShortName", T1."CardName", 
           CASE WHEN SUM(T0."Debit"-T0."Credit")>0 THEN 0 ELSE -SUM(T0."Debit"-T0."Credit") END "PAY"
         FROM JDT1 T0  JOIN  OCRD T1  ON  T0."ShortName"=T1."CardCode" 
         WHERE T1."CardType" ='C'  AND T0."ShortName"=:CardCode AND T0."BPLId"=:BPLId AND T0."Account" NOT IN ('122102','224104')
         GROUP BY T0."ShortName", T1."CardName" 

         UNION ALL
           
         SELECT 
           "CardCode" "ShortName" ,"CardName", 
           (CASE WHEN "U_Z040" = '往来款' THEN -IFNULL("U_Z028",0) ELSE 0 END +
	        CASE WHEN "U_Z041" = '往来款' THEN -IFNULL("U_Z030",0) ELSE 0 END +
	        CASE WHEN "U_Z042" = '往来款' THEN -IFNULL("U_Z032",0) ELSE 0 END +
	        CASE WHEN "U_Z043" = '往来款' THEN -IFNULL("U_Z034",0) ELSE 0 END +
	        CASE WHEN "U_Z044" = '往来款' THEN -IFNULL("U_Z026",0) ELSE 0 END )  
	     FROM ORDR 
	     WHERE "CANCELED"='N' AND "CardCode"=:CardCode AND "BPLId"=:BPLId AND "DocStatus"='O'

         UNION ALL 
         
         SELECT 
           "CardCode" "ShortName" ,"CardName", 
           (CASE WHEN "U_Z040" = '往来款' THEN IFNULL("U_Z028",0) ELSE 0 END +
	        CASE WHEN "U_Z041" = '往来款' THEN IFNULL("U_Z030",0) ELSE 0 END +
	        CASE WHEN "U_Z042" = '往来款' THEN IFNULL("U_Z032",0) ELSE 0 END +
	        CASE WHEN "U_Z043" = '往来款' THEN IFNULL("U_Z034",0) ELSE 0 END +
	        CASE WHEN "U_Z044" = '往来款' THEN IFNULL("U_Z026",0) ELSE 0 END  - IFNULL("U_Z037",0) )  
	     FROM ORDR 
	     WHERE "CANCELED"='N' AND "CardCode"=:CardCode AND "BPLId"=:BPLId AND "DocStatus"='O'	 

         UNION ALL
        
         SELECT 
           "CardCode" "ShortName" ,"CardName",
           -IFNULL("U_Z037",0)  
         FROM ODLN 
         WHERE "DocStatus"='O' AND "CardCode"=:CardCode AND "BPLId"=:BPLId 	

         UNION ALL
         
         SELECT 
           "CardCode" "ShortName" ,"CardName","DocTotal" 
         FROM ORDN 
         WHERE "DocStatus"='O' AND "CardCode"=:CardCode AND "BPLId"=:BPLId 	  
	 )A; 
	 
  --获取理论可用余额
  SELECT :ActBalance+:DiscAmt INTO CustAmt FROM DUMMY;
    
  SELECT 
    :DiscPack "DiscPack", --折扣公斤
    :ActBalance "ActBalance", --理论欠款余额（往来款）
    :DiscAmt "DiscAmt", --折扣金额 = 季折+月折+年折+任务折 （折扣余额）
    :CustAmt "CustAmt" --理论可用余额（可用余额）
  FROM DUMMY;
 
END;