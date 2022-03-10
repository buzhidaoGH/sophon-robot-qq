package pvt.example.sophon.config;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.ConfigBeans;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.test.TestJUnit;
import pvt.example.sophon.utils.YamlUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * 类&emsp;&emsp;名：SophonDataSource <br/>
 * 描&emsp;&emsp;述：Sophon的数据库设置
 */
@ConfigBeans
public class SophonInitConfig {
    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger LOG = LoggerFactory.getLogger(SophonInitConfig.class);

    static {
        Map<String, String> config = YamlUtils.getSophonByKey("config");
        LOG.info("mybatis-resource-path:"+config.get("mybatis-resource-path"));
        InputStream resourceAsStream = TestJUnit.class.getResourceAsStream(config.get("mybatis-resource-path"));
        LOG.info("database-file-path:"+config.get("database-file-path"));
        String databasePath = new File(config.get("database-file-path")).getAbsolutePath();
        Properties properties = new Properties();
        properties.setProperty("databasePath", databasePath);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream, properties);
    }

    @Beans
    public SqlSession getSqlSession() {
        // 默认不会自动提交事务
        return sqlSessionFactory.openSession();
    }
}
