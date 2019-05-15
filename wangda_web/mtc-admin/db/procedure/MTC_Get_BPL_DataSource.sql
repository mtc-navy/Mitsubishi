DROP PROCEDURE "MTC_Get_BPL_DataSource";
CREATE PROCEDURE "MTC_Get_BPL_DataSource"
(
  CardCode NVARCHAR(50), --客户编码
  DataType NVARCHAR(10), --数据类型：S-销售分支,T-提货分支
  UserName NVARCHAR(50), --用户名
  FilterValue NVARCHAR(100) DEFAULT '0' -- 过滤条件
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  /* 根据用户管理中设定的用户类型，分别获取对应客户数据 */
  DECLARE UserType NVARCHAR(10);
  SELECT "USER_TYPE" INTO UserType FROM "SYS_USER" WHERE "USERNAME"=:UserName;

  -- 销售分支
  IF :DataType=N'S' THEN

    -- 基地开票员
    IF :UserType='1' THEN

      TMP=
      SELECT
        T0."BPLId",
        T0."BPLName",
        T0."TaxIdNum"
      FROM "OBPL" T0
      WHERE 1=1
      --过滤客户权限分支
      AND EXISTS (SELECT T01."BPLId"
                  FROM "OCRD" T00
                  INNER JOIN "CRD8" T01 ON T01."CardCode"=T00."CardCode"
                  WHERE T00."CardCode"=:CardCode AND T01."BPLId"=T0."BPLId")
      --过滤用户权限分支
      AND EXISTS (SELECT T11."BPLId"
                  FROM "OUSR" T10
                  INNER JOIN "USR6" T11 ON T10."USER_CODE"=T11."UserCode"
                  WHERE T10."USER_CODE"=:UserName AND T11."BPLId"=T0."BPLId");

    --办事处开票员
    ELSEIF :UserType='2' THEN

      TMP=
      SELECT
        T0."BPLId",
        T0."BPLName",
        T0."TaxIdNum"
      FROM "OBPL" T0
      WHERE 1=1
      --过滤客户权限分支
      AND EXISTS (SELECT T01."BPLId"
                  FROM "OCRD" T00
                  INNER JOIN "CRD8" T01 ON T01."CardCode"=T00."CardCode"
                  WHERE T00."CardCode"=:CardCode AND T01."BPLId"=T0."BPLId")
      --过滤用户权限分支
      AND EXISTS (SELECT T11."BPLId"
                  FROM "OUSR" T10
                  INNER JOIN "USR6" T11 ON T10."USER_CODE"=T11."UserCode"
                  WHERE T10."USER_CODE"=:UserName AND T11."BPLId"=T0."BPLId"
                  --办事处分支
                  AND T10."U_IsOffice"=N'Y');

    --客户
    ELSE

      TMP=
      SELECT
        T0."BPLId",
        T0."BPLName",
        T0."TaxIdNum"
      FROM "OBPL" T0
      WHERE 1=1
      --过滤客户权限分支
      AND EXISTS (SELECT T01."BPLId"
                  FROM "OCRD" T00
                  INNER JOIN "CRD8" T01 ON T01."CardCode"=T00."CardCode"
                  WHERE T00."CardCode"=:CardCode AND T01."BPLId"=T0."BPLId")
      --过滤用户权限分支
      AND EXISTS (SELECT T11."BPLId"
                  FROM "OUSR" T10
                  INNER JOIN "USR6" T11 ON T10."USER_CODE"=T11."UserCode"
                  WHERE T10."USER_CODE"=:UserName AND T11."BPLId"=T0."BPLId");

    END IF;

  -- 提货基地
  ELSEIF :DataType=N'T' THEN

    TMP=
     SELECT
       T0."BPLId",
       T0."BPLName",
       T0."TaxIdNum"
     FROM "OBPL" T0
     INNER JOIN "OCRD" T1 ON T1."U_TakeBPLId"=T0."BPLId"
     WHERE T1."CardCode"=:CardCode;

  END IF;

  SELECT DISTINCT
    T0."BPLId",
    T0."BPLName",
    T0."TaxIdNum"
  FROM(
    SELECT
      T0."BPLId",
      T0."BPLName",
      T0."TaxIdNum"
    FROM :TMP T0
    UNION ALL
    SELECT
      T0."BPLId",
      T0."BPLName",
      T0."TaxIdNum"
    FROM "OBPL" T0
    WHERE "BPLId"=:FilterValue
  )T0;

END;
