package pvt.example.sophon.listener;

import catcode.Neko;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.constant.PriorityConstant;
import love.forte.simbot.filter.MatchType;
import net.mamoe.mirai.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.config.SophonInitConfig;
import pvt.example.sophon.utils.TestUtils;

/**
 * 类&emsp;&emsp;名：SophonTestListen <br/>
 * 描&emsp;&emsp;述：
 */
@Listener
public class SophonTestListen {
    private static final Logger LOG = LoggerFactory.getLogger(SophonTestListen.class);
    @Depend
    private BotManager botManager;

    @Listen(PrivateMsg.class)
    @Priority(PriorityConstant.THIRD)
    public void sophonPrivateTest(PrivateMsg privateMsg, Sender sender) {
        Bot miraiBot = SophonInitConfig.getMiraiBot(botManager.getDefaultBot().getBotInfo().getBotCodeNumber());
        AccountInfo accountInfo = privateMsg.getAccountInfo();
        LOG.info("Bot:{},账号:{},Msg:{}", miraiBot, accountInfo.getAccountCode(), privateMsg.getMsg());
    }

    @Listen(GroupMsg.class)
    @Priority(PriorityConstant.THIRD)
    public void sophonPrivateTest(GroupMsg groupMsg, Sender sender) {
        Bot miraiBot = SophonInitConfig.getMiraiBot(botManager.getDefaultBot().getBotInfo().getBotCodeNumber());
        AccountInfo accountInfo = groupMsg.getAccountInfo();
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        LOG.info("Bot:{},群号:{},账号:{},Msg:{}", miraiBot,groupInfo.getGroupCode(), accountInfo.getAccountCode(), groupMsg.getMsg());
    }

    @Listen(PrivateMsg.class)
    @Filter(value = "test", matchType = MatchType.STARTS_WITH)
    public void test(PrivateMsg privateMsg, Sender sender) {
        // miraiBot.getFriend(1780948378).sendMessage("我敲音乐咋搞");
        Neko record = TestUtils.getRecord("D:/voice/test.amr");
        sender.sendGroupMsg("681660897", record.toString());
    }
}