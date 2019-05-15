DROP PROCEDURE "MTC_Create_UserInfoFromSAP";
CREATE PROCEDURE "MTC_Create_UserInfoFromSAP"
(
    in object_type nvarchar(20), 			-- SBO Object Type
	in transaction_type nchar(1),			-- [A]dd, [U]pdate, [D]elete, [C]ancel, C[L]ose
	in num_of_cols_in_key int,
	in list_of_key_cols_tab_del nvarchar(255),
	in list_of_cols_val_tab_del nvarchar(255),
	out errbox result
)
LANGUAGE SQLSCRIPT
AS
BEGIN

  errbox = SELECT '0' error,'OK' errormsg FROM dummy;

  --SAP创建用户时候
  IF :object_type=N'12' AND (:transaction_type=N'A' OR :transaction_type=N'U') THEN
  BEGIN

    INSERT INTO "SYS_USER"("USER_ID","USERNAME","SAPUSERNAME","PASSWORD","SALT","EMAIL","MOBILE","STATUS","DEPT_ID","CREATE_TIME","USER_TYPE","DEL_FLAG","REMARK")
    SELECT
      SYS_USER_SEQ.NEXTVAL,
      T0."USER_CODE" AS "USERNAME",
      IFNULL(T0."U_NAME",T0."USER_CODE") AS "SAPUSERNAME",
      CASE WHEN T0."U_IsOffice"=N'1' THEN T3."PARAM_VALUE" --办事处
           ELSE T1."PARAM_VALUE" END AS "PASSWORD",
      CASE WHEN T0."U_IsOffice"=N'1' THEN T4."PARAM_VALUE" --办事处
           ELSE T2."PARAM_VALUE" END AS "SALT",
      T0."E_Mail" AS "EMAIL",
      T0."Tel1" AS "MOBILE",
      N'1' AS "STATUS",
      CASE WHEN IFNULL(T0."U_IsOffice",'2')=N'1' THEN N'3' --办事处
           ELSE N'2' END AS "DEPT_ID", --基地
      CURRENT_TIMESTAMP AS "CREATE_TIME",
      CASE WHEN IFNULL(T0."U_IsOffice",'2')=N'1' THEN N'2' --办事处
           ELSE N'1' END AS "USER_TYPE",--基地
      N'0' AS "DEL_FLAG",
      N'' AS "REMARK"
    FROM "OUSR" T0
    LEFT JOIN "SYS_CONFIG" T1 ON T1."PARAM_KEY"=N'USER_DEFAULT_PASSWORD_COMPANY'--基地默认密码
    LEFT JOIN "SYS_CONFIG" T2 ON T2."PARAM_KEY"=N'USER_DEFAULT_SALT_COMPANY'--基地默认密钥
    LEFT JOIN "SYS_CONFIG" T3 ON T3."PARAM_KEY"=N'USER_DEFAULT_PASSWORD_OFFICE'--办事处默认密码
    LEFT JOIN "SYS_CONFIG" T4 ON T4."PARAM_KEY"=N'USER_DEFAULT_SALT_OFFICE'--办事处默认密钥
    WHERE T0."USERID"=:list_of_key_cols_tab_del
     AND T0."USER_CODE" NOT IN (SELECT "USERNAME" FROM "SYS_USER");

    --存在的场合更新用户类型
    UPDATE T0
    SET T0."USER_TYPE"=(CASE WHEN IFNULL(T1."U_IsOffice",'2')=N'1' THEN N'2' ELSE N'1' END)
    FROM "SYS_USER" T0
    INNER JOIN "OUSR" T1 ON T1."USER_CODE"=T0."USERNAME"
    WHERE T0."USER_ID"=:list_of_key_cols_tab_del;
  END;
  END IF;

  --SAP新增、修改业务伙伴的时候
  IF :object_type=N'2' AND (:transaction_type=N'A' OR :transaction_type=N'U') THEN
  BEGIN

    INSERT INTO "SYS_USER"("USER_ID","USERNAME","SAPUSERNAME","PASSWORD","SALT","EMAIL","MOBILE","STATUS","DEPT_ID","CREATE_TIME","USER_TYPE","DEL_FLAG","REMARK")
    SELECT
      SYS_USER_SEQ.NEXTVAL,
      T0."U_UserID" AS "USERNAME",
      T0."CntctPrsn" AS "SAPUSERNAME",
      T1."PARAM_VALUE" AS "PASSWORD",
      T2."PARAM_VALUE" AS "SALT",
      T0."E_Mail" AS "EMAIL",
      T0."Cellular" AS "MOBILE",
      N'1' AS "STATUS",
      N'2' AS "DEPT_ID",
      CURRENT_TIMESTAMP AS "CREATE_TIME",
      N'3' AS "USER_TYPE",
      N'0' AS "DEL_FLAG",
      N'' AS "REMARK"
    FROM "OCRD" T0
    LEFT JOIN "SYS_CONFIG" T1 ON T1."PARAM_KEY"=N'USER_DEFAULT_PASSWORD_CUSTOMER'--客户默认密码
    LEFT JOIN "SYS_CONFIG" T2 ON T2."PARAM_KEY"=N'USER_DEFAULT_SALT_CUSTOMER'--客户默认密钥
    WHERE T0."U_UserID" IS NOT NULL AND T0."U_UserID" !=N''
     AND T0."CardCode"=:list_of_key_cols_tab_del
     AND T0."U_UserID" NOT IN (SELECT "USERNAME" FROM "SYS_USER");

  END;
  END IF;

END;