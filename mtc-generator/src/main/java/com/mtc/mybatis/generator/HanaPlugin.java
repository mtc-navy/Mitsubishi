package com.mtc.mybatis.generator;

import com.mysql.jdbc.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class HanaPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //添加domain的import
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        topLevelClass.addImportedType("lombok.Data");

        //添加domain的注解
        topLevelClass.addAnnotation("/*");
        topLevelClass.addAnnotation(" * " + (StringUtils.isNullOrEmpty(introspectedTable.getRemarks()) ? introspectedTable.getFullyQualifiedTable() : introspectedTable.getRemarks()));
        topLevelClass.addAnnotation(" */");
        topLevelClass.addAnnotation("@Data");

        topLevelClass.addAnnotation("@ApiModel(value = \"" + (StringUtils.isNullOrEmpty(introspectedTable.getRemarks()) ?
                introspectedTable.getFullyQualifiedTable() : introspectedTable.getRemarks()) + "\")");

        topLevelClass.addAnnotation("@TableName(\"\\\"" + introspectedTable.getFullyQualifiedTable() + "\\\"\")");

        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成setter
        return false;
    }
}
