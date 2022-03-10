package pvt.example.sophon.test;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.timer.EnableTimeTask;
import love.forte.simbot.timer.Fixed;

import java.util.concurrent.TimeUnit;

/**
 * 类&emsp;&emsp;名：Test <br/>
 * 描&emsp;&emsp;述：
 */
@EnableTimeTask
@Beans
public class Test {

    @Depend
    private BotManager botManager;

    /**
     * 2分钟执行一次。
     */
    @Fixed(value = 2, timeUnit = TimeUnit.MINUTES)
    @Listen(MsgGet.class)
    @Filter(at = {"1780948378"})
    public void task() {
    }

}
