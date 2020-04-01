package com.ruoyi.web.controller.poetry;

import com.google.common.collect.Lists;
import com.ruoyi.web.utils.QrCodeUtil;
import com.ruoyi.web.utils.ZipUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 二维码图片文件生成与下载
 *
 * @author eason
 * @date 2020-04-1
 */
@Controller
@RequestMapping("/system/qrcode")
public class QrCodeFileController {


    /**
     * 二维码管理
     */
    @ApiOperation(value = "二维码图片生成与下载",notes = "")
    @PostMapping( "/download")
    @ResponseBody
    public byte[] downloadQrImagesZip(String qrCodesStr, String deptId, HttpServletResponse resp)
    {

        qrCodesStr = "DKLKD-BP-0001,DKLKD-BP-0002,DKLKD-BP-0013,44";
        deptId = "123";

        String rootPath = "/Users/eason/Desktop/tmp";
        String zipFilePath = rootPath + File.separator + "qrImages.zip";
        File srcDir = new File(rootPath + File.separator + deptId);
        File zipFile = new File(zipFilePath);

        List<String> qrCodeList = Lists.newArrayList(qrCodesStr.split(","));
        QrCodeUtil qrCodeUtil = new QrCodeUtil();
        try {
            qrCodeUtil.generateIceQrFiles(qrCodeList, rootPath, deptId);

            // 压缩
            ZipUtil.zipFilesByDirs(srcDir, zipFile);

            // 输出文件流到前端
            FileInputStream fileInputStream = new FileInputStream(zipFilePath);
            OutputStream outputStream = resp.getOutputStream();
            byte[] buffer = new byte[1024];
            int bufferLen = 0;
            while ((bufferLen = fileInputStream.read(buffer))>0){
                outputStream.write(buffer,0,bufferLen);
            }
            fileInputStream.close();
            outputStream.close();

            return buffer;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }


        return null;

    }
}
