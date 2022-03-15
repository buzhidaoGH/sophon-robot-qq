package pvt.example.sophon;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.utils.StringUtils;
import pvt.example.sophon.utils.YamlUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类&emsp;&emsp;名：SophonRobotInitConfig <br/>
 * 描&emsp;&emsp;述：在Sophon机器人启动之前要做的初始化
 */
public class SophonRobotInitConfig {
    public static void   logbackCreateInit() {
        String config = YamlUtils.getSophonByKey("config").get("logback-resource-path");
        InputStream resourceAsStream = SophonRobotInitConfig.class.getResourceAsStream(config);
        if (resourceAsStream != null && StringUtils.isEmpty(System.getProperty("logback.configurationFile"))) {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.reset();
            try {
                configurator.doConfigure(resourceAsStream);
            } catch (JoranException e) {
                java.util.logging.Logger logger = java.util.logging.Logger.getLogger("");
                logger.info("logback文件未找到!");
                e.printStackTrace();
            }finally {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
        }
    }
}
