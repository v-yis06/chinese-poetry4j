package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author yisheng
 */
@SpringBootApplication (exclude = { DataSourceAutoConfiguration.class })
public class ThirdApiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ThirdApiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  若一第三方API启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .--.           ____     ____       \n" +
                " |  |                              \n" +
                " |  |                              \n" +
                " |  |                              \n" +
                " |  |                              \n" +
                " |  |                              \n" +
                " |  |                              \n" +
                " |  |                              \n" +
                " '--'          `'-'     ..-..      ");
    }

}