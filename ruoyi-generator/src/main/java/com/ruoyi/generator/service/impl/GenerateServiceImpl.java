package com.ruoyi.generator.service.impl;

import com.google.common.collect.Maps;
import com.ruoyi.generator.domain.BeanField;
import com.ruoyi.generator.domain.GenerateInput;
import com.ruoyi.generator.service.GenerateService;
import com.ruoyi.generator.util.StringUtil;
import com.ruoyi.generator.util.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @auther 易胜
 * @date 2019-12-30
 * @desc
 */
@Service
public class GenerateServiceImpl implements GenerateService, EnvironmentAware {
    private Environment evn;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<BeanField> beanFieldMapper = new RowMapper<BeanField>() {
        public BeanField mapRow(ResultSet rs, int paramInt) throws SQLException {
            BeanField beanField = new BeanField();
            beanField.setColumnName(rs.getString("column_name"));
            beanField.setColumnType(rs.getString("data_type"));
            beanField.setColumnComment(rs.getString("column_comment"));
            beanField.setColumnDefault(rs.getString("column_default"));
            return beanField;
        }
    };
    private static Map<String, String> mapmysql = Maps.newHashMap();
    private static Map<String, String> maporacle = Maps.newHashMap();

    static {
        mapmysql.put("int", Integer.class.getSimpleName());
        mapmysql.put("tinyint", Integer.class.getSimpleName());
        mapmysql.put("double", Double.class.getSimpleName());
        mapmysql.put("float", Float.class.getSimpleName());
        mapmysql.put("decimal", BigDecimal.class.getSimpleName());
        mapmysql.put("date", Date.class.getSimpleName());
        mapmysql.put("timestamp", Date.class.getSimpleName());
        mapmysql.put("datetime", Date.class.getSimpleName());
        mapmysql.put("varchar", String.class.getSimpleName());
        mapmysql.put("text", String.class.getSimpleName());
        mapmysql.put("longtext", String.class.getSimpleName());
        maporacle.put("NUMBER", Long.class.getSimpleName());
        maporacle.put("DATE", Date.class.getSimpleName());
        maporacle.put("TIMESTAMP", Date.class.getSimpleName());
        maporacle.put("CHAR", String.class.getSimpleName());
        maporacle.put("VARCHAR2", String.class.getSimpleName());
        maporacle.put("LONG", String.class.getSimpleName());
    }

    public GenerateServiceImpl() {
    }

    public List<BeanField> listBeanField(String tableName, String dbFlag) {
        String url = this.evn.getProperty("spring.datasource." + dbFlag + ".url");
        String[] staarray = url.split(":");
        new ArrayList();
        List<BeanField> beanFields;
        if (staarray[1].equals("mysql")) {
            beanFields = jdbcTemplate.query("select column_name, data_type, column_comment, column_default FROM information_schema.columns WHERE table_name= ? and table_schema = (select database())", new String[]{tableName}, this.beanFieldMapper);
        } else {
            if (!staarray[1].equals("oracle")) {
                throw new IllegalArgumentException("目前仅支持mysql和oracle,如需支持其他数据库，请联系Henry");
            }

            String username = this.evn.getProperty("spring.datasource." + dbFlag + ".username");
            beanFields = jdbcTemplate.query("select b.COLUMN_NAME as column_name,a.data_type as data_type,b.comments as column_comment,a.data_default as column_default from all_tab_cols a ,all_col_comments b where a.column_name=b.column_name and a.table_name=b.table_name and a.OWNER=b.OWNER and a.table_name=upper(?) AND a.OWNER=?", new String[]{tableName, username}, this.beanFieldMapper);
        }

        if (CollectionUtils.isEmpty(beanFields)) {
            throw new IllegalArgumentException("表" + tableName + "不存在");
        } else {
            beanFields.parallelStream().forEach((b) -> {
                b.setName(StringUtil.str2hump(b.getColumnName()));
                String type = null;
                if (staarray[1].equals("mysql")) {
                    type = (String)mapmysql.get(b.getColumnType());
                } else {
                    if (!staarray[1].equals("oracle")) {
                        throw new IllegalArgumentException("目前仅支持mysql和oracle,如需支持其他数据库，请联系Henry");
                    }

                    type = (String)maporacle.get(b.getColumnType());
                }

                if (type == null) {
                    type = String.class.getSimpleName();
                }

                b.setType(type);
                if ("id".equals(b.getName())) {
                    b.setType(Long.class.getSimpleName());
                }

                b.setColumnDefault(b.getColumnDefault() == null ? "" : b.getColumnDefault());
            });
            return beanFields;
        }
    }

    public String upperFirstChar(String string, String tableHead) {
        string = string.toLowerCase();
        tableHead = tableHead.toLowerCase();
        if (string.startsWith(tableHead)) {
            string = string.substring(tableHead.length());
        }

        String name = StringUtil.str2hump(string);
        String firstChar = name.substring(0, 1);
        name = name.replaceFirst(firstChar, firstChar.toUpperCase());
        return name;
    }

    public void saveCode(GenerateInput input) {
        String dbFlag = input.getDbName();
        String url = this.evn.getProperty("spring.datasource." + dbFlag + ".url");
        String[] staarray = url.split(":");
        TemplateUtil.saveJava(input);
        TemplateUtil.saveJavaDao(input, staarray[1]);
        TemplateUtil.saveController(input);
        TemplateUtil.saveHtmlList(input);
    }

    public String tableComment(String tableName, String dbFlag) {
        String url = this.evn.getProperty("spring.datasource." + dbFlag + ".url");
        String[] staarray = url.split(":");
        Map<String, Object> tableComment = new HashMap();
        String username;
        if (staarray[1].equals("mysql")) {
            username = url.substring(url.lastIndexOf("/") + 1);
            username = username.substring(0, username.lastIndexOf("?"));
            tableComment = jdbcTemplate.queryForMap("SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_NAME = ? AND TABLE_SCHEMA = ?", new Object[]{tableName, username});
        } else {
            if (!staarray[1].equals("oracle")) {
                throw new IllegalArgumentException("目前仅支持mysql和oracle,如需支持其他数据库，请联系Henry");
            }

            username = this.evn.getProperty("spring.datasource." + dbFlag + ".username");

            try {
                tableComment = jdbcTemplate.queryForMap("select COMMENTS as TABLE_COMMENT from all_tab_comments where TABLE_NAME=? AND OWNER=?", new Object[]{tableName, username});
            } catch (DataAccessException var9) {
                System.out.println("查询表注释异常" + var9.getMessage());
            }
        }

        if (tableComment != null && ((Map)tableComment).size() > 0) {
            Object o = ((Map)tableComment).get("TABLE_COMMENT");
            if (o != null) {
                return o.toString();
            }
        }

        return "";
    }

    public void setEnvironment(Environment environment) {
        this.evn = environment;
    }
}
