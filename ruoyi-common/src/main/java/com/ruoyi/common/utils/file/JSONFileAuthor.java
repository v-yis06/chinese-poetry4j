package com.ruoyi.common.utils.file;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * @auther 易胜
 * @date 2020-01-02
 * @desc
 */
public class JSONFileAuthor implements JSONFile{

    public JSONArray readJsonData(String classPath) throws IOException {
        ClassPathResource resource = new ClassPathResource(classPath);
        File file = resource.getFile();
        String jsonString = FileUtils.readFileToString(file,"utf-8");
        jsonString = jsonString.replaceAll("id\":","id2\":");
        jsonString = jsonString.replaceAll("desc\":","description\":");

        return JSONArray.parseArray(jsonString);
    }
}
