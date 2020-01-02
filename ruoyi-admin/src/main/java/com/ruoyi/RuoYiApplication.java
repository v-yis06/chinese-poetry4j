package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  若一启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.     ____     ____       \n" +
                " |  _ _   \\    \\   \\    /   /    \n" +
                " | ( ' )  |     \\  _. ._   /       \n" +
                " |(_ o _) /     '. _( )_ .'         \n" +
                " | (_,_).' __    '(_ o _)'          \n" +
                " |  |\\ \\  |  |    '(_,_)'         \n" +
                " |  | \\ `'   |     \\  /           \n" +
                " |  |  \\    /       \\/            \n" +
                " ''-'   `'-'     ..-..               ");
    }
}