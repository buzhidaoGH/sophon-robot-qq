package pvt.example.sophon.config;

/**
 * 类&emsp;&emsp;名：Constants <br/>
 * 描&emsp;&emsp;述：常数接口类
 */
public interface Constants {
    /**
     * Cookie存储常量
     */
    public static final String COOKIE_MUSIC163 = "NMTID=00OiMNeaAU1ca1BlEVqsyfR7rhBsokAAAF_gd3uNw";
    /**
     * 正则表达式常量
     */
    public static final String REGEX_COMMAND_BEGIN = "^(?i)command"; // 以command开头
    public static final String REGEX_COMMAND_NO_BEGIN = "(?i)^(?!command)";// 不以 command开头
    public static final String REGEX_SERVE_BEGIN = "^(?i)serve"; // 以 serve 开头
    public static final String REGEX_SERVE_NO_BEGIN = "(?i)^(?!serve)"; // 不以 serve 开头
    /**
     * API接口地址
     */
    public static final String WEATHER_API = "https://v0.tianqiapi.com/?version=today&unit=m&appid=43656176&appsecret"
            + "=I42og6Lm&query=";
    public static final String MUSIC163_SEARCH_API= "http://music.163.com/api/search/pc";
    public static final String MUSIC163_ADDRESS_API = "http://music.163.com/song/media/outer/url?id=musicid.mp3";
    public static final String MUSIC_SOAR_API = "https://api.uomg.com/api/rand.music?";
    /**
     * COMMAND命令常量
     */
    public static final String COMMAND_HELP = "help";       // help 命令
    public static final String COMMAND_JVM = "jvm";         // jvm 虚拟机命令
    public static final String COMMAND_APP = "app";         // app 应用命令
    public static final String COMMAND_SYSTEM = "system";   // system 系统命令
    public static final String COMMAND_SOPHON = "sophon";   // sophon 智梓个人信息
    public static final String COMMAND_NO_EXIST = "此命令不存在!\n帮助命令查询: command help";
    /**
     * SERVE命令常量
     */
    public static final String SERVE_HELP = "help";         // help 命令
    public static final String SERVE_MUSIC = "music";       // music 命令
    public static final String SERVE_WEATHER = "weather";   // weather 命令
    public static final String SERVE_SING = "sing";   // sing 命令
}
