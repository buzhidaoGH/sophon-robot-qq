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
import pvt.example.sophon.entity.JVMInfo;
import pvt.example.sophon.entity.SophonInfo;
import pvt.example.sophon.entity.SystemInfo;
import pvt.example.sophon.utils.FileIOUtils;
import pvt.example.sophon.utils.HttpClientUtils;
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
        testJUnit.test12();
    }

    private void test12(){
        JVMInfo jvmInfo = new JVMInfo();
        System.out.println("jvmInfo = " + jvmInfo);
        SystemInfo systemInfo = new SystemInfo();
        System.out.println("systemInfo = " + systemInfo);
    }

    private void test11() {
        Runtime run = Runtime.getRuntime();
        System.out.println("JVM可以使用的总内存:    " + run.totalMemory()/(1024*1024));
        System.out.println("JVM可以使用的剩余内存:    " + run.freeMemory()/(1024*1024));
        System.out.println("JVM可以使用的处理器个数:    " + run.availableProcessors());

        Properties props = System.getProperties();
        System.out.println("Java的虚拟机实现版本：    " + props.getProperty("java.vm.version"));
        System.out.println("Java的虚拟机实现供应商：    " + props.getProperty("java.vm.vendor"));
        System.out.println("Java的虚拟机实现名称：    " + props.getProperty("java.vm.name"));
    }

    private void test10() {
        String s = HttpClientUtils.sendGetHttp("https://ifconfig.me/ip", null);
        System.out.println("s = " + s);
    }

    /*private void test09() {
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://ifconfig.me/ip");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                                                   .setConnectionRequestTimeout(35000)// 请求超时时间
                                                   .setSocketTimeout(60000)// 数据读取超时时间
                                                   .build();
        httpGet.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = aDefault.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            String result = EntityUtils.toString(entity);
            System.out.println("result = " + result);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                aDefault.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    private void test01() {
        Connection conn = null;
        String databasePath = new File("database/sophon.db").getAbsolutePath();
        System.out.println("databasePath = " + databasePath);
        try {
            String url = "jdbc:sqlite:" + databasePath;
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

    private void test02() {
        String resource = "config/mybatis-config.xml";
        InputStream resourceAsStream = TestJUnit.class.getResourceAsStream(resource);
        String databasePath = new File("database/sophon.db").getAbsolutePath();
        Properties properties = new Properties();
        properties.setProperty("databasePath", databasePath);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream, properties);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println("sqlSession = " + sqlSession);
        sqlSession.close();
    }

    private void test03() {
        Map<String, String> sophon = YamlUtils.getSophonByKey("sophon");
        System.out.println("sophon = " + sophon);
        Map<String, String> config = YamlUtils.getSophonByKey("config");
        System.out.println("config = " + config);
        String s = config.get("mybatisResourcePath");
        String s1 = config.get("mybatis-resource-path");
        System.out.println("s1 = " + s1);
        System.out.println("s = " + s);
    }

    private void test04() {
        SophonInitConfig sophonInitConfig = new SophonInitConfig();
        SqlSession sqlSession = sophonInitConfig.getSqlSession();
        ManagerDao mapper = sqlSession.getMapper(ManagerDao.class);
        List<Manager> managers = mapper.selectAllManager();
        for (Manager manager : managers) {
            System.out.println("manager = " + manager);
        }
        sqlSession.close();
    }

    private void test05() {
        StringUtils.isEmpty(null);
        boolean empty = true;
        System.out.println("empty = " + empty);
    }

    private void test06() {
        LOG.info("info日志");
        LOG.debug("debug日志");
    }

    private void test07() {
        String[] strings = FileIOUtils.fileReadLine("/config/banner.txt");
        for (String string : strings) {
            System.out.println(string);
        }
    }

    private void test08() {
        SophonInfo sophonInfo = SophonInfo.getSophonInfo();
        System.out.println("sophonInfo = " + sophonInfo);
    }
}
