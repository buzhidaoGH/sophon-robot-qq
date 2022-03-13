package pvt.example.sophon.listener;

import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.*;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import love.forte.simbot.filter.MostMatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.config.Constants;
import pvt.example.sophon.listener.handle.ManagerPrivateListenHandle;

/**
 * 类&emsp;&emsp;名：ManagerPrivateListen <br/>
 * 描&emsp;&emsp;述：管理员私信消息监听分发 <br/>
 */
@Listener
public class ManagerPrivateListen {
    @Depend
    private ManagerPrivateListenHandle handle;
    private static final Logger LOG = LoggerFactory.getLogger(ManagerPrivateListen.class);

    /**
     * 监听Manager技术管理员私信
     * @param privateMsg 私聊消息
     * @param sender     信息发送器
     */
    @Listen(PrivateMsg.class)
    @Filters(value = {@Filter(value = Constants.REGEX_COMMAND_BEGIN, trim = true, matchType = MatchType.REGEX_FIND)},
             customFilter = {"ManagerPrivateFilter"},
             customMostMatchType = MostMatchType.ALL)
    public void managerCommandMethod(PrivateMsg privateMsg, Sender sender) {
        AccountInfo accountInfo = privateMsg.getAccountInfo();
        String[] commands = privateMsg.getText().replaceAll("\\s+", " ").split(" ");
        LOG.info("账号:{},执行命令:{}",accountInfo.getAccountCode(),privateMsg.getText());
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
                sender.sendPrivateMsg(accountInfo, Constants.COMMAND_NO_EXIST);
        }
    }
}

