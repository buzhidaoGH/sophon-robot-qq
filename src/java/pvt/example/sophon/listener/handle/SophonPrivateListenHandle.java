package pvt.example.sophon.listener.handle;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.sender.Sender;
import pvt.example.sophon.config.SophonInitConfig;
import pvt.example.sophon.service.DictService;
import pvt.example.sophon.utils.ApiUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 类&emsp;&emsp;名：SophonPrivateListenHandle <br/>
 * 描&emsp;&emsp;述：SophonPrivateListen的handle处理工具
 */
@Beans
public class SophonPrivateListenHandle {
    @Depend
    private MessageContentBuilderFactory builderFactory;
    @Depend
    private DictService dictService;

    /**
     * 帮助命令信息发送
     */
    public void senderMsgHelp(AccountInfo accountInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        List<String> serveList = dictService.getServeList();
        messageContentBuilder.text("serve服务帮助命令：").face(12);
        messageContentBuilder.text("\n命令格式：serve option");
        for (String serve : serveList) { messageContentBuilder.text("\n— " + serve); }
        sender.sendPrivateMsg(accountInfo, messageContentBuilder.build());
    }

    /**
     * Serve查看天气服务
     */
    public void senderServeWeather(AccountInfo accountInfo, Sender sender, String[] serves) {
        if (serves.length <= 2) {
            sender.sendPrivateMsg(accountInfo, "地区天气查看：server weather 地区");
            return;
        }
        ExecutorService threadPool = SophonInitConfig.getThreadPool();
        threadPool.execute(() -> this.asyncFindWeather(accountInfo, sender, serves[2]));
        sender.sendPrivateMsg(accountInfo, "天气查询需要一定时间，请耐心等待，梓妹正在处理中[CAT:face,id=130]~");
    }

    /**
     * 私聊异步天气查询
     */
    private void asyncFindWeather(AccountInfo accountInfo, Sender sender, String city) {
        Map<String, String> weatherMap = ApiUtils.queryWeatherByCityApi(city);
        if (weatherMap == null) {
            sender.sendPrivateMsg(accountInfo, "地区输入有误！或者查询失败！");
            return;
        }
        MessageContentBuilder msgContentBuilder = builderFactory.getMessageContentBuilder();
        msgContentBuilder.text("您所查地区：" + weatherMap.get("city"));
        msgContentBuilder.text("\n 数据时间：" + weatherMap.get("updateTime"));
        msgContentBuilder.text("\n 体感温度：" + weatherMap.get("feelsLike"));
        msgContentBuilder.text("\n 平均气温：" + weatherMap.get("aveTemperature"));
        msgContentBuilder.text("\n 最高气温：" + weatherMap.get("maxTemperature"));
        msgContentBuilder.text("\n 天气情况：" + weatherMap.get("phrase"));
        msgContentBuilder.text("\n 能 见 度：" + weatherMap.get("visibility"));
        msgContentBuilder.text("\n 风速风向：" + weatherMap.get("windDirCompass") + weatherMap.get("windSpeed"));
        msgContentBuilder.text("\n 紫外线级别：" + weatherMap.get("uvIndex"));
        msgContentBuilder.text("\n 贴心提示：" + weatherMap.get("narrative"));
        sender.sendPrivateMsg(accountInfo, msgContentBuilder.build());
    }

    /**
     * 处理 music 命令
     */
    public void senderServeMusic(AccountInfo accountInfo, Sender sender, String[] serves) {
        if (serves.length <= 2) {
            sender.sendPrivateMsg(accountInfo, "音乐分享：server music [歌曲名]");
            return;
        }
        SophonInitConfig.getThreadPool().execute(() -> asyncShareMusic(accountInfo, sender, serves[2]));
        sender.sendPrivateMsg(accountInfo, "音乐搜索需要一定时间，请耐心等待，梓妹正在处理中[CAT:face,id=307]~");
    }

    /**
     * 私聊异步音乐分享
     */
    private void asyncShareMusic(AccountInfo accountInfo, Sender sender, String musicName) {
        Map<String, String> musicMap = ApiUtils.queryMusic163ByName(musicName);
        if (musicMap == null) {
            sender.sendPrivateMsg(accountInfo, "音乐分享失败！音乐源不存在！");
            return;
        }
        String format = String.format("[CAT:music,type=163,musicUrl=%s,title=%s,picture=%s]", musicMap.get("musicUrl"),
                                      musicMap.get("name"), musicMap.get("picUrl"));
        sender.sendPrivateMsg(accountInfo, format);
    }

    /**
     * 处理 sing 命令
     */
    public void senderServeSing(AccountInfo accountInfo, Sender sender, String[] serves) {
        // SophonInitConfig.getThreadPool().execute(() -> asyncRecordSing(accountInfo, sender));
        sender.sendPrivateMsg(accountInfo, "梓妹目前无法发送语音，框架对语音还有不兼容bug[CAT:face,id=173]~");
    }

    /**
     * 唱歌 sing 异步回复
     */
    private void asyncRecordSing(AccountInfo accountInfo, Sender sender) {
        // String absolutePath = new File("dir/output.amr").getAbsolutePath();
        // System.out.println("absolutePath = " + absolutePath);
        String absolutePath = "F:\\PerProject\\sophon-robot-qq\\dir\\output4.amr";
        File file = new File("dir/output4.amr");
        sender.sendPrivateMsg(accountInfo, "[CAT:voice,file=D://voice/test.amr]");
    }
}
