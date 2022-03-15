package pvt.example.sophon.test;

import catcode.Neko;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.SophonRobotInitConfig;
import pvt.example.sophon.config.Constants;
import pvt.example.sophon.config.SophonInitConfig;
import pvt.example.sophon.dao.GroupDao;
import pvt.example.sophon.dao.ManagerDao;
import pvt.example.sophon.domain.Group;
import pvt.example.sophon.domain.Manager;
import pvt.example.sophon.entity.JVMInfo;
import pvt.example.sophon.entity.SophonInfo;
import pvt.example.sophon.entity.SystemInfo;
import pvt.example.sophon.utils.*;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;

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
        // testJUnit.test10();
        // System.out.println("127.0.0.1");
        testJUnit.test25();
    }

    private void test25() {
        List<Map<String, String>> cardsMap = ApiUtils.biliXhsDynamic();
        for (Map<String, String> stringStringMap : cardsMap) {
            System.out.println("stringStringMap = " + stringStringMap);
        }
    }

    private void test24() {
        long start = System.currentTimeMillis();
        boolean sameDay = DateUtils.isSameDay(new Date(), new Date());
        long end = System.currentTimeMillis();
        System.out.println("end-start = " + (end - start));
        boolean b = DateUtils.timestampIsToDay(2123532);
        start = System.currentTimeMillis();
        System.out.println("start - end " + (start - end));
    }

    private void test23() {
        String s = ApiUtils.lsjtEvents();
        System.out.println("s = " + s);
    }

    private void test22() {
        String s = ApiUtils.randomJoke();
        System.out.println("s = " + s);
    }

    private void test21() {
        String cmd = Constants.COMMAND_MP3_TO_AMR("8f42", "output.amr");
        System.out.println("cmd = " + cmd);
        String res = ProcessUtils.execResult(cmd);
        System.out.println(res);
    }

    private void test20() {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
        Group group = groupDao.selectOneByGroupCode("groupCode");
        sqlSession.close();
        System.out.println("group = " + group);
    }

    private void test19() {
        Neko record = TestUtils.getRecord("F://abc//test.amr");
        System.out.println("record = " + record);
    }

    private void test18() {
        String s = HttpClientUtils.downloaderUrlResource("GET",
                                                         "http://music.163.com/song/media/outer/url?id=1849998058.mp3",
                                                         "dir/");
        System.out.println("s = " + s);
    }

    private void test17() {
        Map<String, String> randomMusic = HttpClientUtils.getRandomMusic();
        assert randomMusic != null;
        for (String value : randomMusic.values()) {
            System.out.println(value);
        }
    }

    private void test16() {
        Map<String, String> musicName = ApiUtils.queryMusic163ByName("啦啦啦啦啦啦");
        if (musicName == null) { return;}
        for (String key : musicName.keySet()) {
            System.out.print(key + " : ");
            System.out.println(musicName.get(key));
        }
    }

    private void test15() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("s", "千里行走");
        map.put("offset", "0");
        map.put("limit", "1");
        map.put("type", "1");
        String s = HttpClientUtils.sendPostHttp("http://music.163.com/api/search/pc", map,
                                                "NMTID=00OiMNeaAU1ca1BlEVqsyfR7rhBsokAAAF_gd3uNw");
        JSONObject jsonObject = JsonParseUtils.jsonExtract(s);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray jsonArray = result.getJSONArray("songs");
        JSONObject song = jsonArray.getJSONObject(0);
        String name = song.getString("name");
        String id = song.getString("id");
        System.out.println("id = " + id);
        System.out.println("name = " + name);
    }

    private void test14() {
        ExecutorService threadPool = SophonInitConfig.getThreadPool();
        for (int i = 0; i < 40; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        threadPool.shutdown();
    }

    private void test13() {
        long startTime = System.currentTimeMillis();
        Map<String, String> weatherMap = ApiUtils.queryWeatherByCityApi("长沙");
        if (weatherMap == null) { return; }
        String phrase = weatherMap.get("phrase");
        System.out.println("phrase = " + phrase);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    private void test12() {
        JVMInfo jvmInfo = JVMInfo.getJVMInfo();
        System.out.println("jvmInfo = " + jvmInfo);
        SystemInfo systemInfo = SystemInfo.getSystemInfo();
        System.out.println("systemInfo = " + systemInfo);
    }

    private void test11() {
        Runtime run = Runtime.getRuntime();
        System.out.println("JVM可以使用的总内存:    " + run.totalMemory() / (1024 * 1024));
        System.out.println("JVM可以使用的剩余内存:    " + run.freeMemory() / (1024 * 1024));
        System.out.println("JVM可以使用的处理器个数:    " + run.availableProcessors());
        Properties props = System.getProperties();
        System.out.println("Java的虚拟机实现版本：    " + props.getProperty("java.vm.version"));
        System.out.println("Java的虚拟机实现供应商：    " + props.getProperty("java.vm.vendor"));
        System.out.println("Java的虚拟机实现名称：    " + props.getProperty("java.vm.name"));
    }

    private void test10() {
        String s = HttpClientUtils.sendGetHttp("https://ifconfig.me/ip", null);
        System.out.println("s = " + s.trim());
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
        } finally {
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

    private void test04_2() {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        ManagerDao mapper = sqlSession.getMapper(ManagerDao.class);
        Manager manager = new Manager();
        manager.setFdAccount("177777777");
        manager.setFdNickname("昵称");
        manager.setFdRole('0');
        boolean b = mapper.insertManager(manager);
        sqlSession.commit();
        sqlSession.close();
        System.out.println("b = " + b);
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }

    private void test04() {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
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
