CREATE FUNCTION "MTC_F_GetCustomerList"
(
  UserId NVARCHAR(50) --登录用户ID
)
RETURNS TABLE ("CardCode" NVARCHAR(50),"CardName" NVARCHAR(100),"ShortName" NVARCHAR(50))
LANGUAGE SQLSCRIPT
AS
BEGIN
   /* 根据用户管理中设定的用户类型，分别获取对应客户数据 */
  DECLARE UserType NVARCHAR(10);
  SELECT "USER_TYPE" INTO UserType FROM "SYS_USER" WHERE "USERNAME"=:UserId;

  -- 基地开票员
  IF :UserType='1' THEN
      BEGIN
        RETURN
        SELECT
          T0."CardCode", --客户编码
          T0."CardName", --客户名称
          T0."U_ShortName" AS "ShortName"
        FROM "OCRD" T0
        INNER JOIN "CRD8" T1 ON T1."CardCode"=T0."CardCode"
        INNER JOIN "USR6" T2 ON T2."BPLId"=T1."BPLId" -- 客户分支在用户权限分支中
        INNER JOIN "OUSR" T3 ON T3."USER_CODE"=T2."UserCode" -- 关联用户信息
        INNER JOIN "OUDG" T4 ON T4."Code"=T3."DfltsGroup" -- 关联用户默认值
        WHERE T0."CardType"=N'C' --业务伙伴类型为客户
                  --状态未冻结
          AND ((T0."validFor"='Y' AND (T0."frozenFrom" IS NULL OR T0."frozenFrom"<=CURRENT_DATE) AND (T0."frozenTo" IS NULL OR T0."frozenTo">=CURRENT_DATE))
            OR (T0."frozenFor"='Y' AND (T0."frozenFrom" IS NULL OR T0."frozenFrom">=CURRENT_DATE) AND (T0."frozenTo" IS NULL OR T0."frozenTo"<=CURRENT_DATE)))
          --客户数据有效
          AND T0."Deleted"='N'
          --判断用户账号
          AND T2."UserCode"=:UserId
          --客户的提货基地为用户的默认分支
          AND T3."U_IsOffice"=N'2';
      END;
  ELSEIF :UserType='2' THEN
      BEGIN
        RETURN
        SELECT DISTINCT
          T0."CardCode", --客户编码
          T0."CardName", --客户名称
          T0."U_ShortName" AS "ShortName"
        FROM "OCRD" T0
        INNER JOIN "@U_CRD8" T1 ON T1."U_CardCode"=T0."CardCode"--取业务伙伴主数据分支对应价格清单表
        INNER JOIN "USR6" T2 ON T2."BPLId"=T1."U_BPLId" --客户分支在用户权限分支中
        INNER JOIN "OPLN" T3 ON T3."ListNum"=T1."U_ListNum" --关联价格清单
        WHERE T0."CardType"=N'C' --业务伙伴类型为客户
                  --状态未冻结
          AND ((T0."validFor"='Y' AND (T0."frozenFrom" IS NULL OR T0."frozenFrom"<=CURRENT_DATE) AND (T0."frozenTo" IS NULL OR T0."frozenTo">=CURRENT_DATE))
            OR (T0."frozenFor"='Y' AND (T0."frozenFrom" IS NULL OR T0."frozenFrom">=CURRENT_DATE) AND (T0."frozenTo" IS NULL OR T0."frozenTo"<=CURRENT_DATE)))
          --客户数据有效
          AND T0."Deleted"='N'
          --判断用户账号
          AND T2."UserCode"=:UserId
          --客户的价格清单为办事处价格清单
          AND (T3."U_WhsCode" IS NOT NULL AND T3."U_WhsCode">N'');
      END;
  ELSE
      BEGIN
        RETURN
        SELECT
          T0."CardCode", --客户编码
          T0."CardName", --客户名称
          T0."U_ShortName" AS "ShortName"
        FROM "OCRD" T0
        INNER JOIN "CRD8" T1 ON T1."CardCode"=T0."CardCode"
        WHERE T0."CardType"=N'C' --业务伙伴类型为客户
          --状态未冻结
          AND ((T0."validFor"='Y' AND (T0."frozenFrom" IS NULL OR T0."frozenFrom"<=CURRENT_DATE) AND (T0."frozenTo" IS NULL OR T0."frozenTo">=CURRENT_DATE))
            OR (T0."frozenFor"='Y' AND (T0."frozenFrom" IS NULL OR T0."frozenFrom">=CURRENT_DATE) AND (T0."frozenTo" IS NULL OR T0."frozenTo"<=CURRENT_DATE)))
          --客户数据有效
          AND T0."Deleted"='N'
          --判断用户账号
          AND T0."U_UserID"=:UserId
          --客户提货基地对应客户分配权限的分支
          AND T1."BPLId"=T0."U_TakeBPLId";
      END;
  END IF;

END;