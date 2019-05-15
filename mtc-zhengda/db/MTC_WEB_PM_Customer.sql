DROP PROCEDURE "MTC_WEB_PM_Customer";
CREATE PROCEDURE "MTC_WEB_PM_Customer"
(
  UserId NVARCHAR(50)  --用户ID(=>MTC_WUSR-DocEntry)
)
AS
BEGIN
  SELECT
    T0."CardCode",
    T0."CardName",
    T0."U_SalePsitn" AS "SalePsitn",--业务员编号
    T0."U_SaleMan" AS "SaleMan",--业务员名称
    T0."U_DeptPsitn" AS "DeptPsitn",--大部岗位号
    T0."U_Deptmet" AS "Deptmet"--大部岗位名
  FROM "OCRD" T0
  WHERE T0."CardType"='C';

END;