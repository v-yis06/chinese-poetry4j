package com.ruoyi.generator.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @auther 易胜
 * @date 2019-12-30
 * @desc
 */
public class GenerateInput implements Serializable {
    private static final long serialVersionUID = -2870071259702969061L;
    private String path;
    private String tableName;
    private String dbName;
    private String beanPackageName;
    private String beanName;
    private String beanComment;
    private String daoPackageName;
    private String daoName;
    private String controllerPkgName;
    private String controllerName;
    private List<String> columnNames;
    private List<String> beanFieldName;
    private List<String> beanFieldType;
    private List<String> beanFieldValue;
    private List<String> beanFieldComment;

    public GenerateInput() {
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getBeanComment() {
        return this.beanComment;
    }

    public void setBeanComment(String beanComment) {
        this.beanComment = beanComment;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanPackageName() {
        return this.beanPackageName;
    }

    public void setBeanPackageName(String beanPackageName) {
        this.beanPackageName = beanPackageName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getDaoPackageName() {
        return this.daoPackageName;
    }

    public void setDaoPackageName(String daoPackageName) {
        this.daoPackageName = daoPackageName;
    }

    public String getDaoName() {
        return this.daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    public String getControllerPkgName() {
        return this.controllerPkgName;
    }

    public void setControllerPkgName(String controllerPkgName) {
        this.controllerPkgName = controllerPkgName;
    }

    public String getControllerName() {
        return this.controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public List<String> getColumnNames() {
        return this.columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<String> getBeanFieldName() {
        return this.beanFieldName;
    }

    public void setBeanFieldName(List<String> beanFieldName) {
        this.beanFieldName = beanFieldName;
    }

    public List<String> getBeanFieldType() {
        return this.beanFieldType;
    }

    public void setBeanFieldType(List<String> beanFieldType) {
        this.beanFieldType = beanFieldType;
    }

    public List<String> getBeanFieldValue() {
        return this.beanFieldValue;
    }

    public void setBeanFieldValue(List<String> beanFieldValue) {
        this.beanFieldValue = beanFieldValue;
    }

    public List<String> getBeanFieldComment() {
        return this.beanFieldComment;
    }

    public void setBeanFieldComment(List<String> beanFieldComment) {
        this.beanFieldComment = beanFieldComment;
    }
}
