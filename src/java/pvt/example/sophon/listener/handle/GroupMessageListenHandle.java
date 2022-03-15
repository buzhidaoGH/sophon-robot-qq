package pvt.example.sophon.listener.handle;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.sender.Sender;
import pvt.example.sophon.config.Constants;
import pvt.example.sophon.config.SophonInitConfig;
import pvt.example.sophon.domain.Group;
import pvt.example.sophon.service.DictService;
import pvt.example.sophon.service.GroupService;

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

    /**
     * 帮助命令信息发送
     */
    public void senderMsgHelp(GroupInfo groupInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        List<String> groupList = dictService.getGroupList();
        messageContentBuilder.text("group服务帮助命令：").face(12);
        messageContentBuilder.text("\n命令格式：group option");
        for (String group : groupList) { messageContentBuilder.text("\n— " + group); }
        sender.sendGroupMsg(groupInfo, messageContentBuilder.build());
    }

    /**
     * 群注册信息功能
     */
    public void senderRegisterHandler(GroupInfo groupInfo, Sender sender) {
        SophonInitConfig.getThreadPool().execute(() -> asyncRegisterGroup(groupInfo, sender));
        sender.sendGroupMsg(groupInfo, "梓妹正在登记中[CAT:face,id=261]~");
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
}
