DROP VIEW "MTC_WEB_VM_Material";
CREATE VIEW "MTC_WEB_VM_Material"
AS
SELECT
  T2."CardCode",
  T0."ItemCode",
  T0."ItemName",
  T0."SalFactor1" AS "PerPacKg",
  T0."U_IsStdPack" AS "IsStdPack",
  T0."SalUnitMsr" AS "InvntryUom"
FROM "OITM" T0
INNER JOIN "ITM1" T1 ON T1."ItemCode"=T0."ItemCode"
INNER JOIN "OCRD" T2 ON T2."ListNum"=T1."PriceList";