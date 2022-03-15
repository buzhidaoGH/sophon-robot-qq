package pvt.example.sophon.listener;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.assists.Permissions;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.BeOperatorInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.containers.OperatorInfo;
import love.forte.simbot.api.message.events.FriendAddRequest;
import love.forte.simbot.api.message.events.GroupAddRequest;
import love.forte.simbot.api.message.events.GroupMemberPermissionChanged;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.api.sender.Setter;
import love.forte.simbot.bot.BotManager;
import pvt.example.sophon.config.PreprocessUtil;
import pvt.example.sophon.domain.Group;
import pvt.example.sophon.service.GroupService;

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
    private BotManager botManager;

    /**
     * 好友申请监听
     */
    @Listen(FriendAddRequest.class)
    public void friendAddRequestListen(FriendAddRequest friendAddRequest, Setter setter) {
        AccountInfo accountInfo = friendAddRequest.getAccountInfo();
        System.out.println("accountInfo = " + accountInfo);
        String text = friendAddRequest.getText();
        System.out.println("text = " + text);
        String originalData = friendAddRequest.getOriginalData();
        System.out.println("originalData = " + originalData);
        // setter.setFriendAddRequest(friendAddRequest.getFlag(),"",true, false);
    }

    /**
     * 群申请监听
     */
    @Listen(GroupAddRequest.class)
    public void groupAddRequestListen(GroupAddRequest groupAddRequest, Sender sender) {

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
        int i = afterChange.isOwnerOrAdmin() ? 1 : 0;
        group.setFdJurisdiction(i);
        groupService.updateGroup(group);
    }
}
