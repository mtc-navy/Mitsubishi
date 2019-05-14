CREATE PROCEDURE "MTC_PD_GetList"
(
  resultType NVARCHAR(10),--数据类型（S-分页信息,L-明细信息）
  loginUserName NVARCHAR(50),
  pageIndex INTEGER,
  pageSize INTEGER,
  queryParam1 NVARCHAR(50) DEFAULT '',
  queryParam2 NVARCHAR(50) DEFAULT '',
  queryParam3 NVARCHAR(50) DEFAULT '',
  queryParam4 NVARCHAR(50) DEFAULT ''
)
AS
BEGIN

  DECLARE CNT INTEGER; --总记录数
  DECLARE StartIndex INTEGER; --指定页第一条记录索引

  --控制INT类型的数据为空时默认为0
  IF :queryParam1='' THEN
    SELECT '0' INTO queryParam1 FROM DUMMY;
  END IF;

  --汇总记录数
  SELECT COUNT(1) INTO CNT FROM "ORDR"
  WHERE (:queryParam1=NULL OR queryParam1='0' OR "DocNum"=:queryParam1) AND "CardName" LIKE '%'||:queryParam2||'%';

  SELECT (:pageIndex-1)*:pageSize INTO StartIndex FROM DUMMY;

  --每页记录数为0时，显示出所有数据
  IF :pageSize=0 THEN
    SELECT :CNT INTO pageSize FROM DUMMY;
  END IF;

  --返回总记录数
  IF :resultType=N'S' THEN
  BEGIN
	  SELECT
	    :CNT AS "totalCount",--总记录数
	    :pageSize AS "pageSize",--页码
	    CASE WHEN :pageSize=0 THEN 1 ELSE CEIL(:CNT/:pageSize) END AS "totalPage",--总页数
	    :pageIndex AS "currPage",--当前页
	    'DOCENTRY,单号,客户代码,客户名称,分支编码,单据日期' AS "fieldList"--显示列
	  FROM DUMMY;
  END;
  ELSE
  BEGIN

	  --返回列表清单
	  SELECT
	    "DocEntry" AS "DOCENTRY",
	    "DocNum" AS "单号",
	    "CardCode" AS "客户代码",
	    "CardName" AS "客户名称",
	    "BPLId" AS "分支编码",
	    "DocDate" AS "单据日期"
	  FROM "ORDR"
	  WHERE (:queryParam1=NULL OR queryParam1='0' OR "DocNum"=:queryParam1) AND "CardName" LIKE '%'||:queryParam2||'%'
	  LIMIT :pageSize OFFSET :StartIndex;
  END;
  END IF;
END;