package pvt.example.sophon.listener;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.assists.Permissions;
import love.forte.simbot.api.message.containers.*;
import love.forte.simbot.api.message.events.*;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.api.sender.Setter;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.filter.MostMatchType;
import pvt.example.sophon.config.PreprocessUtil;
import pvt.example.sophon.domain.Group;
import pvt.example.sophon.service.DictService;
import pvt.example.sophon.service.GroupService;
import pvt.example.sophon.utils.DateUtils;

import java.util.Objects;

/**
 * 类&emsp;&emsp;名：SophonManagerListen <br/>
 * 描&emsp;&emsp;述：Sophon的整体管理监听
 */
@Beans
public class SophonManagerListen {
    @Depend
    private PreprocessUtil preprocessUtil;
    @Depend
    private GroupService groupService;
    @Depend
    private DictService dictService;
    @Depend
    private BotManager botManager;
    @Depend
    private MessageContentBuilderFactory builderFactory;

    /**
     * 好友申请监听
     */
    @Listen(FriendAddRequest.class)
    public void friendAddRequestListen(FriendAddRequest friendAddRequest, Setter setter) {
        String friendVerification = dictService.getFriendVerification();
        if (friendVerification.contains(Objects.requireNonNull(friendAddRequest.getText()))) {
            setter.setFriendAddRequest(friendAddRequest.getFlag(), null, false, false);
            return;
        }
        setter.setFriendAddRequest(friendAddRequest.getFlag(), null, true, false);
    }

    /**
     * 群申请监听
     */
    @Listen(GroupAddRequest.class)
    public void groupAddRequestListen(GroupAddRequest groupAddRequest, Setter setter) {
        String text = groupAddRequest.getText();
        String groupVerification = dictService.getGroupVerification();
        assert text != null;
        if (text.contains(groupVerification)) {
            setter.setGroupAddRequest(groupAddRequest.getFlag(), true, false, "答案正确,欢迎光临");
            return;
        }
        setter.setGroupAddRequest(groupAddRequest.getFlag(), false, false, "问题答案不正确");
    }

    /**
     * 群成员减少事件
     * @param gmReduce
     */
    @Listen(GroupMemberReduce.class)
    @Filters(customFilter = {"ListenGroupExistFilter"}, customMostMatchType = MostMatchType.ALL)
    public void groupMemberReduce(GroupMemberReduce gmReduce, Sender sender) {
        GroupMemberReduce.Type reduceType = gmReduce.getReduceType();
        GroupInfo groupInfo = gmReduce.getGroupInfo();
        AccountInfo accountInfo = gmReduce.getAccountInfo();
        if (reduceType.equals(GroupMemberReduce.Type.LEAVE)) {
            sender.sendGroupMsg(groupInfo, String.format("%s 主动退出了本群", accountInfo.getAccountNickname()));
        } else {
            BeOperatorInfo beOperatorInfo = gmReduce.getBeOperatorInfo();
            sender.sendGroupMsg(groupInfo, String.format("%s 被 %s 踢出了本群", accountInfo.getAccountNickname(),
                                                         beOperatorInfo.getAccountNickname()));
        }
    }

    /**
     * 群成员增加事件
     */
    @Listen(GroupMemberIncrease.class)
    @Filters(customFilter = {"ListenGroupExistFilter"}, customMostMatchType = MostMatchType.ALL)
    public void groupMemberReduce(GroupMemberIncrease gmIncrease, Sender sender) {
        GroupInfo groupInfo = gmIncrease.getGroupInfo();
        AccountInfo accountInfo = gmIncrease.getAccountInfo();
        MessageContentBuilder msgContent = builderFactory.getMessageContentBuilder();
        msgContent.at(accountInfo);
        msgContent.text(String.format("您于%s加入", DateUtils.timestampToFormat(gmIncrease.getTime() * 1000, "yyyy-MM-dd")))
                  .text(Objects.requireNonNull(groupInfo.getGroupName()));
        msgContent.image(SophonManagerListen.class.getResourceAsStream("/images/hyrq.jpg"));
        sender.sendGroupMsg(groupInfo, msgContent.build());
    }

    /**
     * 群成员权限变动
     */
    @Listen(GroupMemberPermissionChanged.class)
    public void groupMemberPerChanged(GroupMemberPermissionChanged gmpChanged, Sender sender) {
        GroupInfo groupInfo = gmpChanged.getGroupInfo();
        Group group = groupService.searchGroupByGroupCode(groupInfo.getGroupCode());
        if (group == null) {return; }
        String botCode = botManager.getDefaultBot().getBotInfo().getAccountCode();
        BeOperatorInfo beOperatorInfo = gmpChanged.getBeOperatorInfo();
        OperatorInfo operatorInfo = gmpChanged.getOperatorInfo();
        String afterChange = gmpChanged.getAfterChange().isOwnerOrAdmin() ? "管理者" : "普通成员";
        String beforeChange = gmpChanged.getBeforeChange().isOwnerOrAdmin() ? "管理者" : "普通成员";
        sender.sendGroupMsg(gmpChanged,
                            String.format("群内成员权限变动:\n%s 由 %s 变为 %s,\n操作人:%s", beOperatorInfo.getAccountNickname(),
                                          beforeChange, afterChange, operatorInfo.getAccountNickname()));
        if (botCode.equals(beOperatorInfo.getAccountCode())) {
            this.groupBotPerChanged(group, gmpChanged.getAfterChange());
        }
    }

    /**
     * 机器人在群中权限变动
     */
    private void groupBotPerChanged(Group group, Permissions afterChange) {
        group.setFdJurisdiction(afterChange.isOwnerOrAdmin() ? 1 : 0);
        groupService.updateGroup(group);
    }

    /**
     * 群消息监听撤回(需要是管理员);群消息监听敏感词
     */
    @Listen(GroupMsg.class)
    public void  groupMessageRetraction(GroupMsg groupMsg){
        Permissions permission = groupMsg.getPermission();
        System.out.println("permission = " + permission);
        Permissions permission1 = groupMsg.getBotInfo().getPermission();
        System.out.println("permission1==permission = " + (permission1 == permission));
        System.out.println(groupMsg.getPermission().isOwnerOrAdmin());
        System.out.println(groupMsg.getBotInfo().getPermission().isOwnerOrAdmin());
        System.out.println("groupMsg.getText() = " + groupMsg.getText());
    }

}
