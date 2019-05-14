

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%//Crystal Java Reporting Component (JRC) imports.%>
<%-- jrcerom.jar--%>
<%@page import="com.crystaldecisions.reports.sdk.*" %>
<%-- rascore.jar--%>
<%@page import="com.crystaldecisions.sdk.occa.report.lib.*" %>
<%-- webreporting.jar--%>
<%@page import="com.crystaldecisions.report.web.viewer.*"%>
<%@page import="com.crystaldecisions.sdk.occa.report.data.ParameterFieldDiscreteValue"%>
<%@page import="com.crystaldecisions.sdk.occa.report.data.Values"%>
<%@page import="java.util.Locale"%>
<%@page import="com.crystaldecisions.sdk.occa.report.data.FieldDisplayNameType"%>
<%@page import="com.crystaldecisions.sdk.occa.report.data.ParameterField"%>
<%@page import="com.crystaldecisions.sdk.occa.report.data.IParameterField"%>
<%@page import="com.crystaldecisions.sdk.occa.report.data.Fields"%>

<%
    //水晶报表的位置
    final String REPORT_NAME = "jsp/num_amount.rpt";
%>

<%
    try {
        //打开报表
        ReportClientDocument reportClientDoc = new ReportClientDocument();
        reportClientDoc.open(REPORT_NAME, 0);

        //把报表源放进session,传递到报表显示页面
        //session.setAttribute("reportSource", reportClientDoc.getReportSource());

        Object reportSource = reportClientDoc.getReportSource();
        reportClientDoc.getDatabaseController().logon("SYSTEM", "B1adminMTC");


        //建立一个viewer对象实例,并设置
        CrystalReportViewer viewer = new CrystalReportViewer();
        viewer.setOwnPage(true);
        viewer.setOwnForm(true);
        viewer.setPrintMode(CrPrintMode.ACTIVEX);

        Fields fields = reportClientDoc.getDataDefController().getDataDefinition().getParameterFields();
        int totalFields = fields.size();
        for(int i=0;i<totalFields;i++){
            IParameterField field =  (IParameterField)fields.get(i);
            System.out.println(field.getName());
            ParameterField paramField = (ParameterField)reportClientDoc.getDataDefController().getDataDefinition().getParameterFields().findField(field.getName(), FieldDisplayNameType.fieldName, Locale.getDefault());
            Values vals1 = new Values();
            ParameterFieldDiscreteValue pfieldDV1 = new ParameterFieldDiscreteValue();
            if("Dockey@".equals(field.getName())){
                pfieldDV1.setValue("269755");
            }else if("SCHEMA@".equals(field.getName())){
                pfieldDV1.setValue("SBO_TQ_LIVE");
            }
            vals1.add(pfieldDV1);
            paramField.setCurrentValues(vals1);
            fields.add(paramField);
        }


        //从session中取报表源
        //Object reportSource = session.getAttribute("reportSource");
        //viewer.setReportSource(reportSource);
        viewer.setReportSource(reportSource);
        viewer.setParameterFields(fields);
        viewer.setReuseParameterValuesOnRefresh(true);//解决报表工具栏刷新时参数丢失问题


        //显示水晶报表
        viewer.processHttpRequest(request, response,this. getServletConfig().getServletContext(), null);


        //转到报表显示页面
        //response.sendRedirect("CrystalReportViewer.jsp");
    }
    catch(ReportSDKException ex) {
        ex.printStackTrace();
    }
    catch(Exception ex) {
        ex.printStackTrace();
    }
%>