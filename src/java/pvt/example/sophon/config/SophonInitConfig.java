package pvt.example.sophon.config;

import love.forte.common.ioc.annotation.ConfigBeans;
import net.mamoe.mirai.Bot;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.test.TestJUnit;
import pvt.example.sophon.utils.YamlUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类&emsp;&emsp;名：SophonDataSource <br/>
 * 描&emsp;&emsp;述：Sophon的初始化设置
 */
@ConfigBeans
public class SophonInitConfig {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger LOG = LoggerFactory.getLogger(SophonInitConfig.class);
    private static final ExecutorService executorService;

    static {
        Map<String, String> config = YamlUtils.getSophonByKey("config");
        LOG.info("mybatis-resource-path:" + config.get("mybatis-resource-path"));
        InputStream resourceAsStream = TestJUnit.class.getResourceAsStream(config.get("mybatis-resource-path"));
        LOG.info("database-file-path:" + config.get("database-file-path"));
        String databasePath = new File(config.get("database-file-path")).getAbsolutePath();
        Properties properties = new Properties();
        properties.setProperty("databasePath", databasePath);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream, properties);
        try {
            resourceAsStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int threadPoolSize = Integer.parseInt(config.get("thread-pool"));
        executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    public static SqlSession getSqlSessionAuto() {
        return sqlSessionFactory.openSession(true);
    }


    public static ExecutorService getThreadPool() {
        return executorService;
    }

    public static Bot getMiraiBot(long qq) {
        return Bot.getInstanceOrNull(qq);
    }
}
