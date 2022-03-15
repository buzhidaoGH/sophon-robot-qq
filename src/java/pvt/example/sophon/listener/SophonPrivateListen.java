package pvt.example.sophon.listener;

import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.config.Constants;
import pvt.example.sophon.listener.handle.SophonPrivateListenHandle;

/**
 * 类&emsp;&emsp;名：SophonPrivateListen <br/>
 * 描&emsp;&emsp;述：智梓监听所有私有交流Private
 */
@Listener
public class SophonPrivateListen {
    private static final Logger LOG = LoggerFactory.getLogger(SophonPrivateListen.class);
    @Depend
    private SophonPrivateListenHandle handle;

    @Listen(PrivateMsg.class)
    @Filters(value = @Filter(value = Constants.REGEX_SERVE_BEGIN, trim = true, matchType = MatchType.REGEX_FIND))
    public void sophonPrivateMsg(PrivateMsg privateMsg, Sender sender) {
        AccountInfo accountInfo = privateMsg.getAccountInfo();
        LOG.info("账号:{},执行命令:{}", accountInfo.getAccountCode(), privateMsg.getText());
        String[] serves = privateMsg.getText().replaceAll("\\s+", " ").split(" ");
        if (serves.length <= 1) {
            handle.senderMsgHelp(accountInfo, sender);
            return;
        }
        switch (serves[1].toLowerCase()) {
            case Constants.SERVE_WEATHER:
                handle.senderServeWeather(accountInfo, sender, serves);
                break;
            case Constants.SERVE_MUSIC:
                if (privateMsg.getPrivateMsgType() != PrivateMsg.Type.FRIEND) {
                    sender.sendPrivateMsg(accountInfo, "非好友不能使用此功能！");
                    break;
                }
                handle.senderServeMusic(accountInfo, sender, serves);
                break;
            case Constants.SERVE_SING:
                handle.senderServeSing(accountInfo, sender, serves);
                break;
            case Constants.SERVE_HELP:
                handle.senderMsgHelp(accountInfo, sender);
                break;
            default:
                sender.sendPrivateMsg(accountInfo, Constants.MESSAGE_SERVE_NO_EXIST);
        }
    }
}
