DROP VIEW "MTC_WEB_VM_PreMaterialList";
CREATE VIEW "MTC_WEB_VM_PreMaterialList"
AS
SELECT
  T1."CardCode",
  T0."ItemCode",
  T0."ItemName",
  T0."PerPacKg",
  T0."IsStdPack",
  T0."InvntryUom"
FROM "MTC_WRDR1" T0
INNER JOIN "MTC_WRDR" T1 ON T1."DocEntry"=T0."DocEntry"
WHERE EXISTS(SELECT 1 FROM "MTC_WRDR" TT
             WHERE TT."CardCode"=T1."CardCode" AND TT."Creator"=T1."Creator"
             GROUP BY TT."CardCode",T1."Creator"
             HAVING MAX(TT."DocEntry")=T0."DocEntry");