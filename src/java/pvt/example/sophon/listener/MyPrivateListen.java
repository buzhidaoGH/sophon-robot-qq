package pvt.example.sophon.listener;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.constant.PriorityConstant;

/**
 * 类&emsp;&emsp;名：MyPrivateListen <br/>
 * 描&emsp;&emsp;述：私聊消息监听的示例类 <br/>
 * 被管理的类都需要标注 {@link Beans} 注解
 */
@Beans
public class MyPrivateListen {
    /**
     * 通过依赖注入获取一个 "消息正文构建器工厂"。
     */
    @Depend
    private MessageContentBuilderFactory messageContentBuilderFactory;

    @Listen(PrivateMsg.class)
    @Priority(PriorityConstant.SECOND)
    public void replyPrivateMsg1(PrivateMsg privateMsg, Sender sender) {
        String text = privateMsg.getText();
        System.out.println("PriorityConstant.SECOND:" + text);
    }

}

