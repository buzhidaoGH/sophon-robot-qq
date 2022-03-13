package pvt.example.sophon;

import love.forte.simbot.annotation.SimbotApplication;
import love.forte.simbot.core.SimbotApp;
import love.forte.simbot.core.SimbotContext;
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
        SophonRobotInitConfig.logbackCreateInit(); // logback日志初始化加载
    }

    private static final Logger LOG = LoggerFactory.getLogger(SophonRobotApplication.class);

    public static void main(String[] args) {
        SimbotContext run = SimbotApp.run(SophonRobotApplication.class, args);
        LOG.info("智梓系统成功启动!");
        FileIOUtils.printFileLine("/config/banner.txt");
    }
}
