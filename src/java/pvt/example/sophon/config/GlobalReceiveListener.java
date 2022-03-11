package pvt.example.sophon.config;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.BotInfo;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.constant.PriorityConstant;
import love.forte.simbot.listener.ListenerContext;

/**
 * 类&emsp;&emsp;名：GlobalListener <br/>
 * 描&emsp;&emsp;述：全局监听器,用于统计和过滤监听所有项目请求
 */
@Beans
public class GlobalReceiveListener {
    @Priority(PriorityConstant.FIRST)
    @Listen(MsgGet.class)
    public void globalListener(MsgGet globalMsg, ListenerContext listenerContext) {
        BotInfo botInfo = globalMsg.getBotInfo();
        System.out.println("botInfo = " + botInfo);
        AccountInfo accountInfo = globalMsg.getAccountInfo();
        System.out.println("accountInfo = " + accountInfo);
        String originalData = globalMsg.getOriginalData();
        System.out.println("originalData = " + originalData);
        System.out.println("listenerContext = " + listenerContext);
    }
}
