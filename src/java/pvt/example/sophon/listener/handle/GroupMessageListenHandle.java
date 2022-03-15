package pvt.example.sophon.listener.handle;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.config.Constants;
import pvt.example.sophon.config.SophonInitConfig;
import pvt.example.sophon.domain.Group;
import pvt.example.sophon.service.DictService;
import pvt.example.sophon.service.GroupService;
import pvt.example.sophon.utils.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 类&emsp;&emsp;名：GroupMessageListenHandle <br/>
 * 描&emsp;&emsp;述：群组消息监听处理
 */
@Beans
public class GroupMessageListenHandle {
    @Depend
    private MessageContentBuilderFactory builderFactory;
    @Depend
    private DictService dictService;
    @Depend
    private GroupService groupService;
    private static final Logger LOG = LoggerFactory.getLogger(GroupMessageListenHandle.class);

    /**
     * 帮助命令信息发送
     */
    public void senderMsgHelp(GroupInfo groupInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        List<String> groupList = dictService.getGroupList();
        messageContentBuilder.text("group服务帮助命令：").face(12);
        messageContentBuilder.text("\n命令格式：@智梓 group option");
        for (String group : groupList) { messageContentBuilder.text("\n— " + group); }
        sender.sendGroupMsg(groupInfo, messageContentBuilder.build());
    }

    /**
     * 群注册信息功能
     */
    public void senderRegisterHandler(GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> asyncRegisterGroup(groupInfo, sender));
    }

    /**
     * 处理群解除注册功能
     */
    public void senderUnRegisterHandler(GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> asyncUnRegisterGroup(groupInfo, sender));
    }

    /**
     * 处理发送当前梓妹状态信息
     */
    public void senderStatusHandler(GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> asyncStatusForGroup(groupInfo, sender));
    }

    /**
     * 处理并返回当前群的基本信息
     */
    public void senderInfoHandler(GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> asyncInfoGroup(groupInfo, sender));
    }

    /**
     * 处理梓妹开机
     */
    public void senderOnHandler(GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> asyncOnGroup(groupInfo, sender));
    }

    /**
     * 处理梓妹关机
     */
    public void senderOffHandler(GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> asyncOffGroup(groupInfo, sender));
    }

    private void asyncOnGroup(GroupInfo groupInfo, Sender sender) {
        Group group = groupService.searchGroupByGroupCode(groupInfo.getGroupCode());
        if (group == null) {
            sender.sendGroupMsg(groupInfo, "此群未在梓妹的管理范围！\n别想使唤我[CAT:face,id=102]");
            return;
        }
        if (0 == group.getFdEnable()) {
            group.setFdEnable(1);
            if (groupService.updateGroup(group)) {
                sender.sendGroupMsg(groupInfo, "哔哔哔~梓妹启动，变形梓妹开机！[CAT:face,id=130]");
            }
            return;
        }
        sender.sendGroupMsg(groupInfo, "梓妹已经在工作了[CAT:face,id=30]！");
    }

    private void asyncOffGroup(GroupInfo groupInfo, Sender sender) {
        Group group = groupService.searchGroupByGroupCode(groupInfo.getGroupCode());
        if (group == null) {
            sender.sendGroupMsg(groupInfo, "此群未在梓妹的管理范围！\n别想使唤我[CAT:face,id=102]");
            return;
        }
        if (1 == group.getFdEnable()) {
            group.setFdEnable(0);
            if (groupService.updateGroup(group)) {
                sender.sendGroupMsg(groupInfo, "梓妹要休息了，Bye了个Bye~！[CAT:face,id=25]");
            }
        }
    }

    /**
     * 异步返回当前群基本信息
     */
    private void asyncInfoGroup(GroupInfo groupInfo, Sender sender) {
        Group group = groupService.searchGroupByGroupCode(groupInfo.getGroupCode());
        if (group == null) {
            sender.sendGroupMsg(groupInfo, "此群未在梓妹的管理范围！\n别想使唤我[CAT:face,id=102]");
            return;
        }
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        Map<String, String> groupMap = groupService.getGroupInfo(groupInfo);
        messageContentBuilder.imageUrl(Objects.requireNonNull(groupInfo.getGroupAvatar()));
        messageContentBuilder.text("\n群\u3000\u3000主:").text(groupMap.get("owner"));
        messageContentBuilder.text(String.format("\n管理(%s):", groupMap.get("adminCount")))
                             .text(groupMap.get("adminNames"));
        messageContentBuilder.text(String.format("\n群成员:%s", groupMap.get("total")));
        messageContentBuilder.text(String.format("\n创建时间:%s", groupMap.get("createTime")));
        messageContentBuilder.text(String.format("\n最后发言时间:%s", groupMap.get("lastSpeak")));
        messageContentBuilder.text(String.format("\n最近加群时间:%s", groupMap.get("lastJoin")));
        sender.sendGroupMsg(groupInfo, messageContentBuilder.build());
    }

    /**
     * 异步解除群注册信息
     */
    private void asyncUnRegisterGroup(GroupInfo groupInfo, Sender sender) {
        Group group = groupService.searchGroupByGroupCode(groupInfo.getGroupCode());
        if (group == null) {
            sender.sendGroupMsg(groupInfo, "此群还未处在我的治理辖区，你取消个啥？[CAT:face,id=212]");
            return;
        }
        if (groupService.deleteGroupByGroupCode(groupInfo.getGroupCode())) {
            sender.sendGroupMsg(groupInfo, "既然不要我，那我也不再守护这个群了！[CAT:face,id=273]");
        }
    }

    /**
     * 异步注册群功能
     */
    private void asyncRegisterGroup(GroupInfo groupInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        // 添加到执行service添加到数据库
        Group group = groupService.insertGroup(groupInfo);
        if (group != null) {
            sender.sendGroupMsg(groupInfo, "梓妹正在登记中[CAT:face,id=261]~");
            this.asyncStatusForGroup(groupInfo, sender);
            messageContentBuilder.text(Constants.MESSAGE_GROUP_REGISTER_SUCCESS);
            sender.sendGroupMsg(groupInfo, messageContentBuilder.build());
            return;
        }
        sender.sendGroupMsg(groupInfo, Constants.MESSAGE_GROUP_REGISTER_FAIL);
    }

    /**
     * 异步梓妹在群状态信息
     */
    private void asyncStatusForGroup(GroupInfo groupInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        Group group = groupService.searchGroupByGroupCode(groupInfo.getGroupCode());
        if (group != null) {
            messageContentBuilder.text("群\u3000\u3000名：" + group.getFdGroupName());
            messageContentBuilder.text("\n群\u3000\u3000号：" + group.getFdGroupCode());
            messageContentBuilder.text("\n群\u3000\u3000主：" + group.getFdOwner());
            messageContentBuilder.text("\n梓妹状态：" + group.getEnableStr());
            messageContentBuilder.text("\n梓妹权限：" + group.getJurisdictionStr());
            sender.sendGroupMsg(groupInfo, messageContentBuilder.build());
            return;
        }
        messageContentBuilder.text("此群未在梓妹的管理范围！\n别想使唤我").face(102);
        sender.sendGroupMsg(groupInfo, messageContentBuilder.build());
    }

    /**
     * 群serve管理 help
     */
    public void senderServeMsgHelp(GroupInfo groupInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        List<String> groupServeList = dictService.getGroupServeList();
        messageContentBuilder.text("群应用服务命令：").face(12);
        messageContentBuilder.text("\n命令格式：@智梓 option");
        for (String groupServe : groupServeList) { messageContentBuilder.text("\n— " + groupServe); }
        sender.sendGroupMsg(groupInfo, messageContentBuilder.build());
    }

    /**
     * 群发送天气信息
     */
    public void sendWeatherWithGroup(GroupAccountInfo accountInfo, GroupInfo groupInfo, Sender sender,
                                     String[] serves) {
        if (serves.length <= 1) {
            sender.sendGroupMsg(groupInfo, "地区天气查看：@智梓 weather 地区");
            return;
        }
        SophonInitConfig.getThreadPool()
                        .execute(() -> this.asyncFindWeather(accountInfo, groupInfo, sender, serves[1]));
        sender.sendGroupMsg(groupInfo, "天气查询需要一定时间，请耐心等待，梓妹正在处理中[CAT:face,id=130]~");
    }

    private void asyncFindWeather(GroupAccountInfo accountInfo, GroupInfo groupInfo, Sender sender, String city) {
        Map<String, String> weatherMap = ApiUtils.queryWeatherByCityApi(city);
        if (weatherMap == null) {
            sender.sendGroupMsg(groupInfo, "地区输入有误！或者查询失败！");
            return;
        }
        MessageContentBuilder msgContentBuilder = builderFactory.getMessageContentBuilder();
        msgContentBuilder.at(accountInfo);
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
        sender.sendGroupMsg(groupInfo, msgContentBuilder.build());
    }

    /**
     * 群发送音乐分享
     */
    public void sendMusicWhitGroup(GroupInfo groupInfo, Sender sender, String[] serves) {
        if (serves.length <= 1) {
            sender.sendGroupMsg(groupInfo, "音乐分享：server music [歌曲名]");
            return;
        }
        SophonInitConfig.getThreadPool().execute(() -> asyncShareMusic(groupInfo, sender, serves[1]));
        sender.sendGroupMsg(groupInfo, "音乐搜索需要一定时间，请耐心等待，梓妹正在处理中[CAT:face,id=307]~");
    }

    private void asyncShareMusic(GroupInfo groupInfo, Sender sender, String musicName) {
        Map<String, String> musicMap = ApiUtils.queryMusic163ByName(musicName);
        if (musicMap == null) {
            sender.sendGroupMsg(groupInfo, "音乐分享失败！音乐源不存在！");
            return;
        }
        String format = String.format("[CAT:music,type=163,musicUrl=%s,title=%s,picture=%s]", musicMap.get("musicUrl"),
                                      musicMap.get("name"), musicMap.get("picUrl"));
        sender.sendGroupMsg(groupInfo, format);
    }

    /**
     * 群唱歌语音
     */
    public void sendSingWithGroup(GroupAccountInfo accountInfo, GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> asyncGroupRecordSing(accountInfo, groupInfo, sender));
        sender.sendGroupMsg(groupInfo, String.format("[CAT:at,code=%s] 请耐心等待，梓妹正在拼装语音哦[CAT:face,id=211]~",
                                                     accountInfo.getAccountCode()));
    }

    private void asyncGroupRecordSing(GroupAccountInfo accountInfo, GroupInfo groupInfo, Sender sender) {
        Map<String, String> randomMusic = HttpClientUtils.getRandomMusic();
        MessageContentBuilder msgBuilder = builderFactory.getMessageContentBuilder();
        msgBuilder.at(accountInfo.getAccountCode());
        if (randomMusic == null) {
            msgBuilder.text("随机歌曲搜索出现了问题！梓妹也无能为力~");
            msgBuilder.face(210);
            sender.sendGroupMsg(groupInfo, msgBuilder.build());
            return;
        }
        msgBuilder.text("歌曲名:").text(randomMusic.get("name"));
        msgBuilder.imageUrl(randomMusic.get("picUrl"));
        String musicFileName = HttpClientUtils.downloaderUrlResource("GET", randomMusic.get("musicUrl"), "dir/");
        if (ProcessUtils.exec(Constants.COMMAND_MP3_TO_AMR(musicFileName, musicFileName + ".amr"))) {
            File musicFile = new File("dir/" + musicFileName + ".amr");
            sender.sendGroupMsg(groupInfo, msgBuilder.build());
            sender.sendGroupMsg(groupInfo, String.format("[CAT:voice,file=%s]", musicFile.getAbsolutePath()));
            if (FileIOUtils.deleteFile("dir/" + musicFileName)) {
                LOG.info("文件:'{}'删除成功", musicFileName);
            }
            if (FileIOUtils.deleteFile(musicFile)) {
                LOG.info("文件:'{}'删除成功", musicFile);
            }
            return;
        }
        sender.sendGroupMsg(groupInfo, "梓妹的服务器出现了未知错误[CAT:face,id=146]~");
    }

    /**
     * 随机笑话
     */
    public void senderJokeWithGroup(GroupAccountInfo accountInfo, GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> {
            MessageContentBuilder msgBuilder = builderFactory.getMessageContentBuilder();
            msgBuilder.at(accountInfo.getAccountCode());
            msgBuilder.text("\n");
            msgBuilder.imageLocal(GroupMessageListenHandle.class.getResource("/images/xhll.png").getPath());
            msgBuilder.text("\n" + ApiUtils.randomJoke());
            sender.sendGroupMsg(groupInfo, msgBuilder.build());
        });
        sender.sendGroupMsg(groupInfo, "笑话什么的，总得让我先想一想吧[CAT:face,id=122]~");
    }

    /**
     * 历史上的今天
     */
    public void sendLsjtWithGroup(GroupAccountInfo accountInfo, GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> {
            MessageContentBuilder msgBuilder = builderFactory.getMessageContentBuilder();
            msgBuilder.at(accountInfo.getAccountCode());
            msgBuilder.text("你知道历史上的").text(DateUtils.timestampToFormat(System.currentTimeMillis(), "MM-dd"));
            msgBuilder.text("发生过什么事吗?").face(208);
            msgBuilder.text(ApiUtils.lsjtEvents());
            sender.sendGroupMsg(groupInfo, msgBuilder.build());
        });
    }

    /**
     * 新华社今日动态信息
     */
    public void sendNewsXhsWithGroup(GroupAccountInfo accountInfo, GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> {
            MessageContentBuilder msgBuilder = builderFactory.getMessageContentBuilder();
            msgBuilder.text("消息来源于哔哩哔哩新华社:");
            List<Map<String, String>> cardsMap = ApiUtils.biliXhsDynamic();
            cardsMap=cardsMap.subList(0, 2);
            for (Map<String, String> cardMap : cardsMap) {
                msgBuilder.text("\n---------分割行------------\n\n");
                msgBuilder.text("时间:【" + cardMap.get("time") + "】\n");
                msgBuilder.text("标题:【" + cardMap.get("title") + "】\n");
                msgBuilder.imageUrl(cardMap.get("pic"));
                msgBuilder.text("动态信息:" + cardMap.get("dynamic") + "\n");
                msgBuilder.text("信息概要:" + cardMap.get("desc") + "\n");
            }
            sender.sendGroupMsg(groupInfo, msgBuilder.build());
        });
        sender.sendGroupMsg(groupInfo, String.format("[CAT:at,code=%s]让梓妹先整理一下消息哦~[CAT:face,id=193]",
                                                     accountInfo.getAccountCode()));
    }
}
