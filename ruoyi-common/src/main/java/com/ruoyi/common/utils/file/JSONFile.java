package com.ruoyi.common.utils.file;

import com.alibaba.fastjson.JSONArray;

import java.io.IOException;

/**
 * @auther 易胜
 * @date 2020-01-02
 * @desc
 */
public interface JSONFile {

    /**
     * 读取json文件数据
     */
    JSONArray readJsonData(String classPath) throws IOException;
}
