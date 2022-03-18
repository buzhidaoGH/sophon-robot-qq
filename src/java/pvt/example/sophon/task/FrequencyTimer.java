package pvt.example.sophon.task;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.timer.EnableTimeTask;
import love.forte.simbot.timer.Fixed;
import pvt.example.sophon.config.PreprocessUtil;
import pvt.example.sophon.service.GroupService;
import pvt.example.sophon.utils.HttpClientUtils;
import pvt.example.sophon.utils.JsonParseUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类&emsp;&emsp;名：FrequencyTimer <br/>
 * 描&emsp;&emsp;述：频率定时器
 */
@Beans
@EnableTimeTask
public class FrequencyTimer {
    @Depend
    private PreprocessUtil preprocessUtil;
    @Depend
    private GroupService groupService;
    @Depend
    private MessageContentBuilderFactory builderFactory;
    public static HashSet<String> NEWS_TITLE_SET = new HashSet<String>(); // 新闻消息暂存
    public static String YsxwTitle = "";
    public static String XhsTitle = "";
    public static String YsnyTitle = "";

    private MessageContent msgCardBuilder(Map<String, String> cardMap) {
        MessageContentBuilder msgBuilder = builderFactory.getMessageContentBuilder();
        msgBuilder.text("消息来源于哔哩哔哩动态:\n");
        msgBuilder.text("时间:【" + cardMap.get("time") + "】\n");
        msgBuilder.text("标题:【" + cardMap.get("title") + "】\n");
        msgBuilder.imageUrl(cardMap.get("pic"));
        msgBuilder.text("动态信息:" + cardMap.get("dynamic") + "\n");
        msgBuilder.text("信息概要:" + cardMap.get("desc") + "\n");
        return msgBuilder.build();
    }

    @Fixed(value = 20, delay = 5, timeUnit = TimeUnit.MINUTES)
    public void ysxwScanTimer() {
        String urlYsxw = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?host_uid=456664753";
        Map<String, String> cardMap = JsonParseUtils.firstCardMsgHandler(HttpClientUtils.sendGetHttp(urlYsxw, null));
        if (cardMap == null || cardMap.get("title") == null || YsxwTitle.equals(cardMap.get("title"))) { return; }
        YsxwTitle = cardMap.get("title");
        Sender sender = preprocessUtil.getSender();
        MessageContent messageContent = msgCardBuilder(cardMap);
        List<String> groupCodes = groupService.searchAllGroupCodeOn();
        for (String groupCode : groupCodes) {
            sender.sendGroupMsg(groupCode, messageContent);
        }
    }

    @Fixed(value = 20, delay = 4, timeUnit = TimeUnit.MINUTES)
    public void xhsScanTimer() {
        String urlXhs = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?host_uid=473837611";
        Map<String, String> cardMap = JsonParseUtils.firstCardMsgHandler(HttpClientUtils.sendGetHttp(urlXhs, null));
        if (cardMap == null || cardMap.get("title") == null || XhsTitle.equals(cardMap.get("title"))) { return; }
        XhsTitle = cardMap.get("title");
        Sender sender = preprocessUtil.getSender();
        MessageContent messageContent = msgCardBuilder(cardMap);
        List<String> groupCodes = groupService.searchAllGroupCodeOn();
        for (String groupCode : groupCodes) {
            sender.sendGroupMsg(groupCode, messageContent);
        }
    }

    @Fixed(value = 20, delay = 6, timeUnit = TimeUnit.MINUTES)
    public void ysnyScanTimer() {
        String urlYsny = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?host_uid=1343321779";
        Map<String, String> cardMap = JsonParseUtils.firstCardMsgHandler(HttpClientUtils.sendGetHttp(urlYsny, null));
        if (cardMap == null || cardMap.get("title") == null || YsnyTitle.equals(cardMap.get("title"))) { return; }
        YsnyTitle = cardMap.get("title");
        Sender sender = preprocessUtil.getSender();
        MessageContent messageContent = msgCardBuilder(cardMap);
        List<String> groupCodes = groupService.searchAllGroupCodeOn();
        for (String groupCode : groupCodes) {
            sender.sendGroupMsg(groupCode, messageContent);
        }
    }
}
