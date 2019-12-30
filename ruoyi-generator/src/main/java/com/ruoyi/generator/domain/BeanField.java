package com.ruoyi.generator.domain;

import java.io.Serializable;

/**
 * @auther 易胜
 * @date 2019-12-30
 * @desc
 */
public class BeanField implements Serializable {
    private static final long serialVersionUID = 4279960350136806659L;
    private String columnName;
    private String columnType;
    private String columnComment;
    private String columnDefault;
    private String name;
    private String type;

    public BeanField() {
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnComment() {
        return this.columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnDefault() {
        return this.columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
