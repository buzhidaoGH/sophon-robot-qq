package pvt.example.sophon;

import love.forte.simbot.annotation.SimbotApplication;
import love.forte.simbot.core.SimbotApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.utils.FileIOUtils;

/**
 * 类&emsp;&emsp;名：QQRobotApplication <br/>
 * 描&emsp;&emsp;述：QQ机器人启动类
 */
@SimbotApplication
public class SophonRobotApplication {
    static {
        // logback日志初始化加载
        SophonRobotInitConfig.logbackCreateInit();
    }

    private static final Logger LOG = LoggerFactory.getLogger(SophonRobotApplication.class);

    public static void main(String[] args) {
        SimbotApp.run(SophonRobotApplication.class, args);
        FileIOUtils.printFileLine("/config/banner.txt");
    }
}
