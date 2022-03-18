package pvt.example.sophon.config;

/**
 * 类&emsp;&emsp;名：Constants <br/>
 * 描&emsp;&emsp;述：常数接口类
 */
public interface Constants {
    public static final String[] UID_ARRAY = {"456664753", "473837611","1343321779"};
    /**
     * Cookie存储常量
     */
    public static final String COOKIE_MUSIC163 = "NMTID=00OiMNeaAU1ca1BlEVqsyfR7rhBsokAAAF_gd3uNw";

    // -acodec copy -ss 00:00:00 -t 00:00:50
    // dir/bin/ffmpeg.exe -i dir/xxx -ss 00:00:00 -t 00:00:50 -ac 1 -ar 8000 dir/xxxx
    // dir/bin/ffmpeg.exe -i dir/%s -ac 1 -ar 8000 dir/%s
    // dir/bin/ffmpeg.exe -y -i dir/%s -ss 00:00:00 -t 00:00:50 -ac 1 -ar 8000 dir/%s

    /**
     * CMD 命令行命令
     */
    public static String COMMAND_MP3_TO_AMR(String source, String target) {
        return String.format("dir/bin/ffmpeg.exe -y -i dir/%s -ac 1 -ar 8000 dir/%s", source, target);
    }

    /**
     * MESSAGE常量
     */
    public static final String MESSAGE_COMMAND_NO_EXIST = "此命令不存在!\n帮助命令查询: command help";
    public static final String MESSAGE_SERVE_NO_EXIST = "此命令不存在!\n帮助命令查询: serve help";
    public static final String MESSAGE_GROUP_NO_EXIST = "此命令不存在!\n帮助命令查询: @智梓 group help";
    public static final String MESSAGE_GROUP_SERVE_NO_EXIST = "此命令不存在!\n帮助命令查询: @智梓 help";
    public static final String MESSAGE_GROUP_REGISTER_SUCCESS = "群信息注册成功！\n开启智梓监听命令：@智梓 group on";
    public static final String MESSAGE_GROUP_REGISTER_FAIL = "群信息注册失败！或已经注册！";
    /**
     * 正则表达式常量
     */
    public static final String REGEX_COMMAND_BEGIN = "^(?i)command"; // 以 command 开头
    public static final String REGEX_SOPHON_BEGIN = "^(?i)sophon"; // 以 sophon 开头
    public static final String REGEX_COMMAND_NO_BEGIN = "(?i)^(?!command)";// 不以 command 开头
    public static final String REGEX_SERVE_BEGIN = "^(?i)serve"; // 以 serve 开头
    public static final String REGEX_SERVE_NO_BEGIN = "(?i)^(?!serve)"; // 不以 serve 开头
    public static final String REGEX_GROUP_BEGIN = "(?i)group"; // 包含 group
    public static final String REGEX_GROUP_NO_BEGIN = "(?i)^(?!group)"; // 不包含 group
    /**
     * API接口地址
     */
    public static final String WEATHER_API = "https://v0.tianqiapi.com/?version=today&unit=m&appid=43656176&appsecret"
            + "=I42og6Lm&query=";
    public static final String MUSIC163_SEARCH_API = "http://music.163.com/api/search/pc";
    public static final String MUSIC163_ADDRESS_API = "http://music.163.com/song/media/outer/url?id=musicid.mp3";
    public static final String MUSIC_SOAR_API = "https://api.uomg.com/api/rand.music?";
    public static final String RANDOM_JOKE = "https://api.ghser.com/xiaohua/";
    public static final String LSJT_EVENTS = "http://api.weijieyue.cn/api/lsjt/api.php?max=5";

    public static String URL_DYNAMIC() {
        return "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?host_uid=" + UID_ARRAY[(int) (Math.random() * 10) % 3];
    }

    /**
     * COMMAND命令常量
     */
    public static final String COMMAND_HELP = "help";       // help 命令
    public static final String COMMAND_JVM = "jvm";         // jvm 虚拟机命令
    public static final String COMMAND_APP = "app";         // app 应用命令
    public static final String COMMAND_SYSTEM = "system";   // system 系统命令
    public static final String COMMAND_SOPHON = "sophon";   // sophon 智梓个人信息
    /**
     * SERVE命令常量
     */
    public static final String SERVE_HELP = "help";         // help 命令
    public static final String SERVE_MUSIC = "music";       // music 命令
    public static final String SERVE_WEATHER = "weather";   // weather 命令
    public static final String SERVE_SING = "sing";   // sing 命令
    /**
     * GROUP 命令常量
     */
    public static final String GROUP_HELP = "help"; // help 命令
    public static final String GROUP_REGISTER = "register"; // register 命令
    public static final String GROUP_UNREGISTER = "unregister"; // unregister 命令
    public static final String GROUP_STATUS = "status"; // status 命令
    public static final String GROUP_INFO = "info"; // info 命令
    public static final String GROUP_ON = "on"; // on 命令
    public static final String GROUP_OFF = "off"; // off 命令
    /**
     * GROUP SERVE 命令
     */
    public static final String GROUP_SERVE_HELP = "help"; // help 命令
    public static final String GROUP_SERVE_MUSIC = "music"; // music 命令
    public static final String GROUP_SERVE_SING = "sing"; // sing 命令
    public static final String GROUP_SERVE_WEATHER = "weather"; // weather 命令
    public static final String GROUP_SERVE_NEWS = "news"; // news 命令
    public static final String GROUP_SERVE_LSJT = "lsjt"; // lsjt 命令
    public static final String GROUP_SERVE_JOKE = "joke"; // joke 命令
    /**
     * SOPHON命令常量
     */
    public static final String SOPHON_FRIEND_LIST = "friendList";
    public static final String SOPHON_GROUP_LIST = "groupList";
}
