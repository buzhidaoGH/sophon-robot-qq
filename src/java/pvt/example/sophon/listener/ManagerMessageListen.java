package pvt.example.sophon.listener;

import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.message.results.*;
import love.forte.simbot.api.sender.Getter;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import love.forte.simbot.filter.MostMatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.config.Constants;
import pvt.example.sophon.listener.handle.ManagerMessageListenHandle;

import java.util.List;

/**
 * 类&emsp;&emsp;名：ManagerPrivateListen <br/>
 * 描&emsp;&emsp;述：管理员私信消息监听分发 <br/>
 */
@Listener
public class ManagerMessageListen {
    @Depend
    private ManagerMessageListenHandle handle;
    private static final Logger LOG = LoggerFactory.getLogger(ManagerMessageListen.class);

    /**
     * 监听Manager技术管理员私信
     * @param privateMsg 私聊消息
     * @param sender     信息发送器
     */
    @Listen(PrivateMsg.class)
    @Filters(value = {@Filter(value = Constants.REGEX_COMMAND_BEGIN, trim = true, matchType = MatchType.REGEX_FIND)},
             customFilter = {"ManagerDiscernFilter"},
             customMostMatchType = MostMatchType.ALL)
    public void managerCommandMethod(PrivateMsg privateMsg, Sender sender) {
        AccountInfo accountInfo = privateMsg.getAccountInfo();
        String[] commands = privateMsg.getText().replaceAll("\\s+", " ").split(" ");
        LOG.info("账号:{},执行命令:{}", accountInfo.getAccountCode(), privateMsg.getText());
        if (commands.length <= 1) {
            handle.senderMsgHelp(accountInfo, sender);
            return;
        }
        switch (commands[1].toLowerCase()) {
            case Constants.COMMAND_HELP:
                handle.senderMsgHelp(accountInfo, sender);
                break;
            case Constants.COMMAND_APP:
                handle.senderMsgAppInfo(accountInfo, sender);
                break;
            case Constants.COMMAND_JVM:
                handle.senderMsgJVMInfo(accountInfo, sender);
                break;
            case Constants.COMMAND_SOPHON:
                handle.senderMsgSophonInfo(accountInfo, sender);
                break;
            case Constants.COMMAND_SYSTEM:
                handle.senderMsgSystemInfo(accountInfo, sender);
                break;
            default:
                sender.sendPrivateMsg(accountInfo, Constants.MESSAGE_COMMAND_NO_EXIST);
        }
    }

    /**
     * 管理员获取 sophon 的账号信息
     */
    @Listen(PrivateMsg.class)
    @Filters(value = {@Filter(value = Constants.REGEX_SOPHON_BEGIN, trim = true, matchType = MatchType.REGEX_FIND)},
             customFilter = {"ManagerDiscernFilter"},
             customMostMatchType = MostMatchType.ALL)
    public void sophonInfoMethod(PrivateMsg privateMsg, Sender sender, Getter getter) {
        FriendList friendList = getter.getFriendList();
        List<FriendInfo> friends = friendList.getResults();
        for (FriendInfo friend : friends) {
            String grouping = friend.getGrouping();
            String accountCode = friend.getAccountCode();
            String accountNickname = friend.getAccountNickname();
            LOG.info("分组:{},昵称:{},账号:{}", grouping, accountNickname, accountCode);
        }
        GroupList groupList = getter.getGroupList();
        List<SimpleGroupInfo> groups = groupList.getResults();
        for (SimpleGroupInfo group : groups) {
            String groupCode = group.getGroupCode();
            GroupFullInfo groupInfo = getter.getGroupInfo(group);
            GroupOwner owner = groupInfo.getOwner();
            List<GroupAdmin> admins = groupInfo.getAdmins();
            String ownerNickname = owner.getAccountNickname();
            String ownerCode = owner.getAccountCode();
            StringBuffer stringBuffer = new StringBuffer();
            for (GroupAdmin admin : admins) {
                stringBuffer.append(admin.getAccountNickname()).append(" ");
            }
            String groupName = group.getGroupName();
            LOG.info("群号:{},群名:{},群主昵称:{},群主账号:{}", groupCode, groupName, ownerNickname, ownerCode);
            LOG.info("管理列表：{}", stringBuffer);
        }
    }
}

