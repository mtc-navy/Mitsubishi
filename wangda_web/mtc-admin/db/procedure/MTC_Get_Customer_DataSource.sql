DROP PROCEDURE "MTC_Get_Customer_DataSource";
CREATE PROCEDURE "MTC_Get_Customer_DataSource"
(
  UserId NVARCHAR(50), --登录用户ID
  DataType NVARCHAR(10), --数据类型：A-新增,S-查询
  FilterValue NVARCHAR(100) DEFAULT '' --过滤条件
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  -- 查询模式下，只需要客户编号和名称
  IF :DataType=N'S' THEN
  BEGIN

    SELECT T0."CardCode",T0."CardName",T0."ShortName" AS "ShortName"
    FROM "MTC_F_GetCustomerList"(:UserId) T0
    WHERE T0."CardCode"||T0."CardName" LIKE '%'||:FilterValue||'%';

  END;

  -- 新增模式下，需要连带客户信息
  ELSEIF :DataType=N'A' THEN

    SELECT
      T0."CardCode",
      T0."CardName",
      T0."ShortName" AS "ShortName",
      T2."CntctCode" AS "CntctPrsn", -- 收货人（联系人）
      T2."Name" AS "CntctName", -- 收货人名称
      IFNULL(T1."CreditLine",0) AS "CreditLine", -- 可用额度（取业务主数据信用额度）
      CAST(0 AS DECIMAL(19,6)) AS "InOutAmt",-- 往来款
      CAST(0 AS DECIMAL(19,6)) AS "DiscAmt",-- 折扣余额
      CAST(0 AS DECIMAL(19,6)) AS "CanUseAmt" -- 可用金额
    FROM "MTC_F_GetCustomerList"(:UserId) T0
    INNER JOIN "OCRD" T1 ON T1."CardCode"=T0."CardCode"
    LEFT JOIN "OCPR" T2 ON T2."CardCode"=T1."CardCode" AND CAST(T2."Name" AS NVARCHAR(50))=CAST(T1."CntctPrsn" AS NVARCHAR(50)) --关联收货人
    WHERE T0."CardCode"||T0."CardName" LIKE '%'||:FilterValue||'%';

  END IF;
END;
