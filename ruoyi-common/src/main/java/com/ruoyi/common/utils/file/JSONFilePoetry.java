package com.ruoyi.common.utils.file;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @auther 易胜
 * @date 2020-01-02
 * @desc
 */
public class JSONFilePoetry extends FileUploadUtils{

    public JSONArray readJsonData(MultipartFile file) throws IOException {
        String filePath = upload(file);
        System.out.println("filePath:"+filePath);
        String jsonString = FileUtils.readFileToString(new File(filePath),"utf-8");
        jsonString = jsonString.replaceAll("id\":","id2\":");
        jsonString = jsonString.replaceAll("desc\":","description\":");

        return JSONArray.parseArray(jsonString);
    }
}
