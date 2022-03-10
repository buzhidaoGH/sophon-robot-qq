package pvt.example.sophon.test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.SophonRobotInitConfig;
import pvt.example.sophon.config.SophonInitConfig;
import pvt.example.sophon.dao.ManagerDao;
import pvt.example.sophon.domain.Manager;
import pvt.example.sophon.utils.FileIOUtils;
import pvt.example.sophon.utils.StringUtils;
import pvt.example.sophon.utils.YamlUtils;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 类&emsp;&emsp;名：TestJUnit <br/>
 * 描&emsp;&emsp;述：Test测试
 */
public class TestJUnit {
    static {
        SophonRobotInitConfig.logbackCreateInit();
    }

    private static final Logger LOG = LoggerFactory.getLogger(TestJUnit.class);
    public static void main(String[] args) {
        TestJUnit testJUnit = new TestJUnit();
        testJUnit.test07();
    }

    private void test01() {
        Connection conn = null;
        String databasePath = new File("database/sophon.db").getAbsolutePath();
        System.out.println("databasePath = " + databasePath);
        try {
            String url = "jdbc:sqlite:"+databasePath;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void test02(){
        String resource = "config/mybatis-config.xml";
        InputStream resourceAsStream = TestJUnit.class.getResourceAsStream(resource);
        String databasePath = new File("database/sophon.db").getAbsolutePath();
        Properties properties = new Properties();
        properties.setProperty("databasePath", databasePath);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream,properties);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println("sqlSession = " + sqlSession);
        sqlSession.close();
    }
    private void test03(){
        Map<String, String> sophon = YamlUtils.getSophonByKey("sophon");
        System.out.println("sophon = " + sophon);
        Map<String, String> config = YamlUtils.getSophonByKey("config");
        System.out.println("config = " + config);
        String s = config.get("mybatisResourcePath");
        String s1 = config.get("mybatis-resource-path");
        System.out.println("s1 = " + s1);
        System.out.println("s = " + s);
    }

    private void test04(){
        SophonInitConfig sophonInitConfig = new SophonInitConfig();
        SqlSession sqlSession = sophonInitConfig.getSqlSession();
        ManagerDao mapper = sqlSession.getMapper(ManagerDao.class);
        List<Manager> managers = mapper.selectAllManager();
        for (Manager manager : managers) {
            System.out.println("manager = " + manager);
        }
        sqlSession.close();
    }

    private void test05(){
        StringUtils.isEmpty(null);
        boolean empty = true;
        System.out.println("empty = " + empty);
    }

    private void test06(){
        LOG.info("info日志");
        LOG.debug("debug日志");
    }

    private void test07(){
        String[] strings = FileIOUtils.fileReadLine("/config/banner.txt");
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
