package com.ruoyi.generator.util;

import com.ruoyi.generator.domain.GenerateInput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @auther 易胜
 * @date 2019-12-30
 * @desc
 */
public class TemplateUtil {
    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    public TemplateUtil() {
    }

    public static String getTemplete(String fileName) {
        return FileUtil.getText(TemplateUtil.class.getClassLoader().getResourceAsStream("generate/" + fileName));
    }

    public static void saveJava(GenerateInput input) {
        String path = input.getPath();
        String beanPackageName = input.getBeanPackageName();
        String beanName = input.getBeanName();
        String beanComment = input.getBeanComment();
        List<String> beanFieldName = input.getBeanFieldName();
        List<String> beanFieldType = input.getBeanFieldType();
        List<String> beanFieldValue = input.getBeanFieldValue();
        List<String> getColumnComment = input.getBeanFieldComment();
        String text = getTemplete("java.txt");
        text = text.replace("{beanPackageName}", beanPackageName).replace("{beanName}", beanName).replace("{beanComment}", beanComment);
        String imports = "";
        if (beanFieldType.contains(BigDecimal.class.getSimpleName())) {
            imports = imports + "import " + BigDecimal.class.getName() + ";\n";
        }

        if (beanFieldType.contains(Date.class.getSimpleName())) {
            imports = imports + "import " + Date.class.getName() + ";";
        }

        text = text.replace("{import}", imports);
        String filelds = getFields(getColumnComment, beanFieldName, beanFieldType, beanFieldValue);
        text = text.replace("{filelds}", filelds);
        text = text.replace("{getset}", getset(beanFieldName, beanFieldType));
        FileUtil.saveTextFile(text, path + File.separator + getPackagePath(beanPackageName) + beanName + ".java");
        log.debug("生成java model：{}模板", beanName);
    }

    private static String getFields(List<String> getColumnComment, List<String> beanFieldName, List<String> beanFieldType, List<String> beanFieldValue) {
        StringBuffer buffer = new StringBuffer();
        int size = beanFieldName.size();

        for(int i = 0; i < size; ++i) {
            String name = (String)beanFieldName.get(i);
            if (!"id".equals(name) && !"createTime".equals(name) && !"updateTime".equals(name)) {
                String type = (String)beanFieldType.get(i);
                buffer.append("\t@ApiModelProperty(value=\"").append(StringUtils.isNotBlank((CharSequence)getColumnComment.get(i)) ? (String)getColumnComment.get(i) : "【请添加注释】").append("\")\n");
                buffer.append("\tprivate ").append(type).append(" ").append(name);
                buffer.append(";\n");
            }
        }

        return buffer.toString();
    }

    private static String getset(List<String> beanFieldName, List<String> beanFieldType) {
        StringBuffer buffer = new StringBuffer();
        int size = beanFieldName.size();

        for(int i = 0; i < size; ++i) {
            String name = (String)beanFieldName.get(i);
            if (!"id".equals(name) && !"createTime".equals(name) && !"updateTime".equals(name)) {
                String type = (String)beanFieldType.get(i);
                buffer.append("\tpublic ").append(type).append(" get").append(StringUtils.substring(name, 0, 1).toUpperCase() + name.substring(1, name.length())).append("() {\n");
                buffer.append("\t\treturn ").append(name).append(";\n");
                buffer.append("\t}\n");
                buffer.append("\tpublic void set").append(StringUtils.substring(name, 0, 1).toUpperCase() + name.substring(1, name.length())).append("(").append(type).append(" ").append(name).append(") {\n");
                buffer.append("\t\tthis.").append(name).append(" = ").append(name).append(";\n");
                buffer.append("\t}\n");
            }
        }

        return buffer.toString();
    }

    public static void saveJavaDao(GenerateInput input, String dbType) {
        String path = input.getPath();
        String tableName = input.getTableName();
        String beanPackageName = input.getBeanPackageName();
        String beanName = input.getBeanName();
        String daoPackageName = input.getDaoPackageName();
        String daoName = input.getDaoName();
        String text = getTemplete("dao.txt");
        text = text.replace("{daoPackageName}", daoPackageName);
        text = text.replace("{beanPackageName}", beanPackageName);
        text = text.replace("{daoName}", daoName);
        text = text.replace("{table_name}", tableName);
        text = text.replace("{beanName}", beanName);
        text = text.replace("{beanParamName}", lowerFirstChar(beanName));
        String insertColumns = getInsertColumns(input.getColumnNames());
        text = text.replace("{insert_columns}", insertColumns);
        String insertValues = getInsertValues(input.getColumnNames(), input.getBeanFieldName());
        text = text.replace("{insert_values}", insertValues);
        FileUtil.saveTextFile(text, path + File.separator + getPackagePath(daoPackageName) + daoName + ".java");
        log.debug("生成java dao：{}模板", beanName);
        text = getTemplete("mapper.xml");
        text = text.replace("{daoPackageName}", daoPackageName);
        text = text.replace("{beanPackageName}", beanPackageName);
        text = text.replace("{daoName}", daoName);
        text = text.replace("{table_name}", tableName);
        text = text.replace("{beanName}", beanName);
        String resultMap = getResultMap(input.getColumnNames(), input.getBeanFieldName());
        text = text.replace("{resultItem}", resultMap);
        String sets = getUpdateSets(input.getColumnNames(), input.getBeanFieldName());
        text = text.replace("{update_sets}", sets);
        if (dbType.equals("oracle")) {
            text = text.replace("{generatedKeys}", "");
            text = text.replace("{generatedKeysForOracle}", generatedKeysForOracle(tableName));
        } else if (dbType.equals("mysql")) {
            text = text.replace("{generatedKeysForOracle}", "");
            text = text.replace("{generatedKeys}", "useGeneratedKeys=\"true\" keyProperty=\"id\"");
        }

        String saveField = getSaveField(input.getColumnNames(), input.getBeanFieldName());
        String saveVal = getSaveVal(input.getColumnNames(), input.getBeanFieldName());
        text = text.replace("{saveField}", saveField);
        text = text.replace("{saveVal}", saveVal);
        String where = getWhere(input.getColumnNames(), input.getBeanFieldName());
        text = text.replace("{where}", where);
        String[] page = getPageSupport(dbType);
        text = text.replace("{page_support_start}", page[0]);
        text = text.replace("{page_support_end}", page[1]);
        FileUtil.saveTextFile(text, path + File.separator + beanName + "Mapper.xml");
    }

    private static String generatedKeysForOracle(String tableName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\t<selectKey resultType=\"java.lang.Long\" keyProperty=\"id\" order=\"BEFORE\"> \n ");
        buffer.append("\t\t\t").append("select GETLSHBYID('" + tableName + "') as id from dual\n");
        buffer.append("\t\t</selectKey>\n\t\t");
        return buffer.toString();
    }

    private static String getSaveField(List<String> columnNames, List<String> beanFieldName) {
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();

        for(int i = 0; i < size; ++i) {
            String column = (String)columnNames.get(i);
            String field = (String)beanFieldName.get(i);
            if (!"id".equals(field)) {
                buffer.append("\t\t\t<if test=\"" + field + " != null\">\n");
                buffer.append("\t\t\t\t" + column).append(", \n");
                buffer.append("\t\t\t</if>\n");
            }
        }

        return buffer.toString();
    }

    private static String getSaveVal(List<String> columnNames, List<String> beanFieldName) {
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();

        for(int i = 0; i < size; ++i) {
            String field = (String)beanFieldName.get(i);
            if (!"id".equals(field)) {
                buffer.append("\t\t\t<if test=\"" + field + " != null\">\n");
                buffer.append("\t\t\t\t").append("#{").append(field).append("}, \n");
                buffer.append("\t\t\t</if>\n");
            }
        }

        return buffer.toString();
    }

    private static String getResultMap(List<String> columnNames, List<String> beanFieldName) {
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();

        for(int i = 0; i < size; ++i) {
            String column = (String)columnNames.get(i);
            String field = (String)beanFieldName.get(i);
            if (!"id".equals(field)) {
                buffer.append("\t\t<result column=\"" + column + "\" property=\"" + field + "\" />\n");
            }
        }

        return buffer.toString();
    }

    private static String[] getPageSupport(String dbType) {
        String[] page = new String[2];
        if (dbType.equals("oracle")) {
            page[0] = "\tSELECT * FROM \n\t\t(SELECT tab.*, ROWNUM RN FROM (";
            page[1] = "\t) tab \n\t\tWHERE ROWNUM &lt;= #{params.offset}*#{params.limit}\n\t\t)ptab WHERE ptab.RN >(#{params.offset}-1)*#{params.limit}";
        } else if (dbType.equals("mysql")) {
            page[0] = "";
            page[1] = "\tlimit #{params.offset}, #{params.limit}";
        }

        return page;
    }

    private static String getInsertValues(List<String> columnNames, List<String> beanFieldName) {
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();

        for(int i = 0; i < size; ++i) {
            String column = (String)columnNames.get(i);
            if (!"id".equals(column)) {
                buffer.append("#{").append((String)beanFieldName.get(i)).append("}, ");
            }
        }

        String sets = StringUtils.substringBeforeLast(buffer.toString(), ",");
        return sets;
    }

    private static String getInsertColumns(List<String> columnNames) {
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();

        for(int i = 0; i < size; ++i) {
            String column = (String)columnNames.get(i);
            if (!"id".equals(column)) {
                buffer.append(column).append(", ");
            }
        }

        String insertColumns = StringUtils.substringBeforeLast(buffer.toString(), ",");
        return insertColumns;
    }

    private static String getUpdateSets(List<String> columnNames, List<String> beanFieldName) {
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();

        for(int i = 0; i < size; ++i) {
            String column = (String)columnNames.get(i);
            String field = (String)beanFieldName.get(i);
            if (!"id".equals(field)) {
                buffer.append("\t\t\t<if test=\"" + field + " != null\">\n");
                buffer.append("\t\t\t\t" + column).append(" = ").append("#{").append(field).append("}, \n");
                buffer.append("\t\t\t</if>\n");
            }
        }

        return buffer.toString();
    }

    private static String getWhere(List<String> columnNames, List<String> beanFieldName) {
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();

        for(int i = 0; i < size; ++i) {
            String column = (String)columnNames.get(i);
            buffer.append("\t\t\t<if test=\"params." + (String)beanFieldName.get(i) + " != null and params." + (String)beanFieldName.get(i) + " != ''\">\n");
            buffer.append("\t\t\t\tand " + column).append(" = ").append("#{params.").append((String)beanFieldName.get(i)).append("} \n");
            buffer.append("\t\t\t</if>\n");
        }

        return buffer.toString();
    }

    public static String lowerFirstChar(String beanName) {
        String name = StringUtil.str2hump(beanName);
        String firstChar = name.substring(0, 1);
        name = name.replaceFirst(firstChar, firstChar.toLowerCase());
        return name;
    }

    private static String getPackagePath(String packageName) {
        String packagePath = packageName.replace(".", "/");
        if (!packagePath.endsWith("/")) {
            packagePath = packagePath + "/";
        }

        return packagePath;
    }

    public static void saveController(GenerateInput input) {
        String path = input.getPath();
        String beanPackageName = input.getBeanPackageName();
        String beanName = input.getBeanName();
        String daoPackageName = input.getDaoPackageName();
        String daoName = input.getDaoName();
        String text = getTemplete("controller.txt");
        text = text.replace("{daoPackageName}", daoPackageName);
        text = text.replace("{beanPackageName}", beanPackageName);
        text = text.replace("{daoName}", daoName);
        text = text.replace("{daoParamName}", lowerFirstChar(daoName));
        text = text.replace("{beanName}", beanName);
        text = text.replace("{beanParamName}", lowerFirstChar(beanName));
        text = text.replace("{controllerPkgName}", input.getControllerPkgName());
        text = text.replace("{controllerName}", input.getControllerName());
        FileUtil.saveTextFile(text, path + File.separator + getPackagePath(input.getControllerPkgName()) + input.getControllerName() + ".java");
        log.debug("生成controller：{}模板", beanName);
    }

    public static void saveHtmlList(GenerateInput input) {
        String path = input.getPath();
        String beanName = input.getBeanName();
        String beanParamName = lowerFirstChar(beanName);
        String text = getTemplete("htmlList.txt");
        text = text.replace("{beanParamName}", beanParamName);
        text = text.replace("{beanName}", beanName);
        List<String> beanFieldNames = input.getBeanFieldName();
        List<String> beanFieldComment = input.getBeanFieldComment();
        text = text.replace("{columnsDatas}", getHtmlColumnsDatas(beanFieldNames));
        text = text.replace("{ths}", getHtmlThs(beanFieldNames, beanFieldComment));
        FileUtil.saveTextFile(text, path + File.separator + beanParamName + "List.html");
        log.debug("生成查询页面：{}模板", beanName);
        text = getTemplete("htmlAdd.txt");
        text = text.replace("{beanParamName}", beanParamName);
        text = text.replace("{addDivs}", getAddDivs(beanFieldNames, beanFieldComment));
        FileUtil.saveTextFile(text, path + File.separator + "add" + beanName + ".html");
        log.debug("生成添加页面：{}模板", beanName);
        text = getTemplete("htmlUpdate.txt");
        text = text.replace("{beanParamName}", beanParamName);
        text = text.replace("{addDivs}", getAddDivs(beanFieldNames, beanFieldComment));
        text = text.replace("{initData}", getInitData(beanFieldNames));
        FileUtil.saveTextFile(text, path + File.separator + "update" + beanName + ".html");
        log.debug("生成修改页面：{}模板", beanName);
    }

    private static CharSequence getInitData(List<String> beanFieldNames) {
        StringBuilder builder = new StringBuilder();
        beanFieldNames.forEach((b) -> {
            builder.append("\t\t\t\t\t\t$('#" + b + "').val(data." + b + ");\n");
        });
        return builder.toString();
    }

    private static String getAddDivs(List<String> beanFieldNames, List<String> beanFieldComment) {
        StringBuilder builder = new StringBuilder();
        if (beanFieldNames != null && beanFieldComment != null) {
            for(int i = 0; i < beanFieldNames.size(); ++i) {
                String b = (String)beanFieldNames.get(i);
                String b2 = (String)beanFieldComment.get(i);
                if (!"id".equals(b) && !"createTime".equals(b) && !"updateTime".equals(b)) {
                    builder.append("\t\t\t<div class='form-group'>\n");
                    builder.append("\t\t\t\t<label class='col-md-2 control-label'>" + (StringUtils.isNotBlank(b2) ? b2 : b) + "</label>\n");
                    builder.append("\t\t\t\t<div class='col-md-10'>\n");
                    builder.append("\t\t\t\t\t<input class='form-control' placeholder='" + (StringUtils.isNotBlank(b2) ? b2 : b) + "' type='text' name='" + b + "' id='" + b + "' data-bv-notempty='true' data-bv-notempty-message='" + (StringUtils.isNotBlank(b2) ? b2 : b) + " 不能为空'>\n");
                    builder.append("\t\t\t\t</div>\n");
                    builder.append("\t\t\t</div>\n");
                }
            }
        }

        return builder.toString();
    }

    private static String getHtmlThs(List<String> beanFieldNames, List<String> beanFieldComment) {
        StringBuilder builder = new StringBuilder();
        if (beanFieldNames != null && beanFieldComment != null) {
            for(int i = 0; i < beanFieldNames.size(); ++i) {
                builder.append("\t\t\t\t\t\t\t\t\t<th>{beanFieldName}</th>\n".replace("{beanFieldName}", StringUtils.isNotBlank((CharSequence)beanFieldComment.get(i)) ? (CharSequence)beanFieldComment.get(i) : (CharSequence)beanFieldNames.get(i)));
            }
        }

        return builder.toString();
    }

    private static String getHtmlColumnsDatas(List<String> beanFieldNames) {
        StringBuilder builder = new StringBuilder();
        beanFieldNames.forEach((b) -> {
            builder.append("\t\t\t\t{\"data\" : \"{beanFieldName}\", \"defaultContent\" : \"\"},\n".replace("{beanFieldName}", b));
        });
        builder.append("");
        return builder.toString();
    }
}
