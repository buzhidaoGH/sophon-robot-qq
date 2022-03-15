package pvt.example.sophon.listener;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import love.forte.simbot.filter.MostMatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.config.Constants;
import pvt.example.sophon.listener.handle.GroupMessageListenHandle;

/**
 * 类&emsp;&emsp;名：GroupMessageListen <br/>
 * 描&emsp;&emsp;述：群信息监听
 */
@Beans
public class GroupMessageListen {
    @Depend
    private GroupMessageListenHandle handle;
    private static final Logger LOG = LoggerFactory.getLogger(GroupMessageListen.class);

    @Listen(GroupMsg.class)
    @Filters(value = {
            @Filter(atBot = true, value = Constants.REGEX_GROUP_BEGIN, trim = true, matchType = MatchType.REGEX_FIND)},
             customFilter = {"ManagerDiscernFilter", "GroupLeaderFilter"},
             customMostMatchType = MostMatchType.ANY)
    public void groupMessageCommand(GroupMsg groupMsg, Sender sender) {
        GroupAccountInfo accountInfo = groupMsg.getAccountInfo();
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String[] groups = groupMsg.getText().trim().replaceAll("\\s+", " ").split(" ");
        LOG.info("群号:{},账号:{},执行命令:{}", groupInfo.getGroupCode(), accountInfo.getAccountCode(), groupMsg.getText());
        if (groups.length <= 1) {
            handle.senderMsgHelp(groupInfo, sender);
            return;
        }
        switch (groups[1].toLowerCase()) {
            case Constants.GROUP_HELP:
                handle.senderMsgHelp(groupInfo, sender);
                break;
            case Constants.GROUP_REGISTER:
                handle.senderRegisterHandler(groupInfo, sender);
                break;
            case Constants.GROUP_UNREGISTER:
                handle.senderUnRegisterHandler(groupInfo,sender);
                break;
            case Constants.GROUP_STATUS:
                handle.senderStatusHandler(groupInfo,sender);
                break;
            case Constants.GROUP_INFO:
                handle.senderInfoHandler(groupInfo,sender);
                break;
            default:
                sender.sendGroupMsg(groupInfo, Constants.MESSAGE_GROUP_NO_EXIST);
        }
    }
}
