package com.ruoyi.generator.service;

import com.ruoyi.generator.domain.BeanField;
import com.ruoyi.generator.domain.GenerateInput;

import java.util.List;

/**
 * @auther 易胜
 * @date 2019-12-30
 * @desc
 */

public interface GenerateService {
    List<BeanField> listBeanField(String tableName, String dbFlag);

    String tableComment(String tableName, String dbFlag);

    String upperFirstChar(String string, String tableHead);

    void saveCode(GenerateInput input);
}