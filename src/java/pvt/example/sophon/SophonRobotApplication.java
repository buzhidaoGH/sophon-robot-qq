package pvt.example.sophon;

import love.forte.simbot.annotation.SimbotApplication;
import love.forte.simbot.core.SimbotApp;
import love.forte.simbot.core.SimbotContext;

import java.util.Set;

/**
 * 类&emsp;&emsp;名：QQRobotApplication <br/>
 * 描&emsp;&emsp;述：QQ机器人启动类
 */
@SimbotApplication
public class SophonRobotApplication {
    public static void main(String[] args) {
        SimbotContext run = SimbotApp.run(SophonRobotApplication.class, args);
        Set<String> allBeans = run.getAllBeans();
        for (String allBean : allBeans) {
            System.out.println("allBean = " + allBean);
        }
    }
}
