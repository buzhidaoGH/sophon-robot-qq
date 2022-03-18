package pvt.example.sophon.task;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.timer.Cron;
import love.forte.simbot.timer.EnableTimeTask;
import pvt.example.sophon.config.PreprocessUtil;
import pvt.example.sophon.listener.handle.GroupMessageListenHandle;
import pvt.example.sophon.service.GroupService;
import pvt.example.sophon.utils.ApiUtils;
import pvt.example.sophon.utils.HttpClientUtils;

import java.util.List;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：PeriodicTimer <br/>
 * 描&emsp;&emsp;述：周期定时器
 */
@Beans
@EnableTimeTask
public class PeriodicTimer {
    @Depend
    private PreprocessUtil preprocessUtil;
    @Depend
    private GroupService groupService;
    @Depend
    private MessageContentBuilderFactory builderFactory;

    @Cron(value = "0 45 8/2 * * ? *")
    public void jokeTimer() {
        Sender sender = preprocessUtil.getSender();
        MessageContentBuilder msgBuilder = builderFactory.getMessageContentBuilder();
        msgBuilder.text("定时分享：");
        msgBuilder.imageLocal(GroupMessageListenHandle.class.getResource("/images/xhll.png").getPath());
        msgBuilder.text(ApiUtils.randomJoke());
        List<String> groupCodeList = groupService.searchAllGroupCodeOn();
        for (String groupCode : groupCodeList) {
            sender.sendGroupMsg(groupCode, msgBuilder.build());
        }
    }

    @Cron("0 15 7/2 * * ? *")
    public void musicShareTimer() {
        Sender sender = preprocessUtil.getSender();
        Map<String, String> randomMusic = HttpClientUtils.getRandomMusic();
        if (randomMusic == null) { return; }
        String format = String.format("[CAT:music,type=163,musicUrl=%s,title=%s,picture=%s]",
                                      randomMusic.get("musicUrl"), randomMusic.get("name"), randomMusic.get("picUrl"));
        List<String> groupCodeList = groupService.searchAllGroupCodeOn();
        for (String groupCode : groupCodeList) {
            sender.sendGroupMsg(groupCode, format);
        }
    }

    @Cron(value = "0 40 11 * * ?")
    public void lunchTimer() {
        List<String> groupCodeList = groupService.searchAllGroupCodeOn();
        Sender sender = preprocessUtil.getSender();
        for (String groupCode : groupCodeList) {
            MessageContentBuilder msgContentBuilder = builderFactory.getMessageContentBuilder();
            msgContentBuilder.text("11点40了!干饭了~干饭了~\n");
            msgContentBuilder.image(PeriodicTimer.class.getResourceAsStream("/images/gfr.jpg"));
            sender.sendGroupMsg(groupCode, msgContentBuilder.build());
        }
    }

    @Cron(value = "0 30 22 * * ?")
    public void sleepTimer() {
        List<String> groupCodeList = groupService.searchAllGroupCodeOn();
        Sender sender = preprocessUtil.getSender();
        for (String groupCode : groupCodeList) {
            MessageContentBuilder msgContentBuilder = builderFactory.getMessageContentBuilder();
            msgContentBuilder.text("22点30了!睡觉了~睡觉了~\n");
            msgContentBuilder.image(PeriodicTimer.class.getResourceAsStream("/images/sjl.jpg"));
            sender.sendGroupMsg(groupCode, msgContentBuilder.build());
        }
    }
}
