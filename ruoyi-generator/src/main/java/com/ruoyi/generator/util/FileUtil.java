package com.ruoyi.generator.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;

/**
 * @auther 易胜
 * @date 2019-12-30
 * @desc
 */
public class FileUtil {
    public FileUtil() {
    }

    public static String saveFile(MultipartFile file, String pathname) {
        try {
            File targetFile = new File(pathname);
            if (targetFile.exists()) {
                return pathname;
            } else {
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }

                file.transferTo(targetFile);
                return pathname;
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static boolean deleteFile(String pathname) {
        File file = new File(pathname);
        if (!file.exists()) {
            return false;
        } else {
            boolean flag = file.delete();
            if (flag) {
                File[] files = file.getParentFile().listFiles();
                if (files == null || files.length == 0) {
                    file.getParentFile().delete();
                }
            }

            return flag;
        }
    }

    public static String fileMd5(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String getPath() {
        return "/" + LocalDate.now().toString().replace("-", "/") + "/";
    }

    public static void saveTextFile(String value, String path) {
        FileWriter writer = null;

        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            writer = new FileWriter(file);
            writer.write(value);
            writer.flush();
        } catch (IOException var12) {
            var12.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException var11) {
                var11.printStackTrace();
            }

        }

    }

    public static String getText(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        } else {
            try {
                return getText((InputStream)(new FileInputStream(file)));
            } catch (FileNotFoundException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static String getText(InputStream inputStream) {
        InputStreamReader isr = null;
        BufferedReader bufferedReader = null;

        try {
            isr = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder();

            String string;
            while((string = bufferedReader.readLine()) != null) {
                string = string + "\n";
                builder.append(string);
            }

            String var6 = builder.toString();
            return var6;
        } catch (IOException var18) {
            var18.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }

            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }
            }

        }

        return null;
    }
}
