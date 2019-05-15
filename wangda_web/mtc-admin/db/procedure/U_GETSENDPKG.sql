DROP PROCEDURE "U_GETSENDPKG";
CREATE PROCEDURE "U_GETSENDPKG"
( 
  CardCode NVARCHAR(30),
  BPLId INT,
  IsReport NVARCHAR(10) DEFAULT 'Y' --是否报表模式（SAP用Y，Web页面用N）
)
AS
BEGIN
  
  IF :IsReport=N'N' THEN
  BEGIN
    select 
      '' as "Select",
      t1."Order" as "Order",
      t1."ItemCode" as "ItemCode",
      t1."ItemName" as "ItemName",
      t1."SourceDN" as "SourceDN",
      --t1."SourceNum" as "SourceNum",
      --t1."CardCode" as "CardCode",
      t1."DiscCode" as "DiscCode",
      t1."DisctName" as "DisctName",
      Round(t1."DiscPack",2) as "DiscPack", 
      t2."OpenCreQty" "OpenCreQty", 
      0.00 "UseQty",
      '    ' as "StockName"
    from
    (  select "Order" ,I0."ItemCode" ,I0."ItemName",A0."CardCode" ,"DiscCode" ,"DisctName" ,
              "DiscPack"+"DiscKG"/I0."SalFactor1" "DiscPack","SourceDN" ,
              "SourceNum"  --当为临时赠包时，DItemCode 为空
       from "U_SOADL" A0 
       JOIN "OITM" I0 ON I0."ItemCode" = IFNULL(A0."DItemCode",A0."ItemCode")   
       where A0."CardCode"= :CardCode and A0."BPLId" = :BPLId and  ("DiscPack" <> 0 OR "DiscKG" <> 0 ) 
    ) t1  --可用余额表
    left join  
    (  select sum(t1."OpenCreQty"/"Factor1") "OpenCreQty",t1."U_DiscOrder"
       from "RDR1" t1 
       left join "ORDR" t2 on t1."DocEntry" = t2."DocEntry" 
       where t1."LineStatus" = 'O' AND T2."CANCELED" = 'N' and t1."U_Realdisc" = '是'
       group by t1."U_DiscOrder") t2 on t1."Order" = t2."U_DiscOrder" 
    where IFNULL(t1."DiscPack",0) -IFNULL(t2."OpenCreQty",0) > 0;
  END;
  ELSE
  BEGIN
     select 
      '' as "选择",
      t1."Order" as "唯一序号",
      t1."ItemCode" as "物料编码",
      t1."ItemName" as "物料名称",
      t1."SourceDN" as "赠包类型",
      --t1."SourceNum" as "单据编号",
      --t1."CardCode" as "客户代码",
      t1."DiscCode" as "折扣项代码",
      t1."DisctName" as "折扣项名称",
      Round(t1."DiscPack",2) as "可用赠包数", 
      t2."OpenCreQty" "已承诺赠包数", 
      0.00 "使用赠包数",
      '    ' as "仓库"
    from
    (  select "Order" ,I0."ItemCode" ,I0."ItemName",A0."CardCode" ,"DiscCode" ,"DisctName" ,
              "DiscPack"+"DiscKG"/I0."SalFactor1" "DiscPack","SourceDN" ,
              "SourceNum"  --当为临时赠包时，DItemCode 为空
       from "U_SOADL" A0 
       JOIN "OITM" I0 ON I0."ItemCode" = IFNULL(A0."DItemCode",A0."ItemCode")   
       where A0."CardCode"= :CardCode and A0."BPLId" = :BPLId and  ("DiscPack" <> 0 OR "DiscKG" <> 0 ) 
    ) t1  --可用余额表
    left join  
    (  select sum(t1."OpenCreQty"/"Factor1") "OpenCreQty",t1."U_DiscOrder"
       from "RDR1" t1 
       left join "ORDR" t2 on t1."DocEntry" = t2."DocEntry" 
       where t1."LineStatus" = 'O' AND T2."CANCELED" = 'N' and t1."U_Realdisc" = '是'
       group by t1."U_DiscOrder") t2 on t1."Order" = t2."U_DiscOrder" 
    where IFNULL(t1."DiscPack",0) -IFNULL(t2."OpenCreQty",0) > 0;
  END;
  END IF;

END;