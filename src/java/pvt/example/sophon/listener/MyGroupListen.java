package pvt.example.sophon.listener;

import catcode.Neko;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.constant.PriorityConstant;

import java.util.List;

/**
 * 类&emsp;&emsp;名：MyGroupListen <br/>
 * 描&emsp;&emsp;述：群消息监听的示例类<br/>
 * 所有需要被管理的类都需要标注 {@link Beans} 注解
 */
@Beans
public class MyGroupListen {

    @OnGroup
    public void onGroupMsg(GroupMsg groupMsg) {
        // 打印此次消息中的 纯文本消息内容。
        // 纯文本消息中，不会包含任何特殊消息（例如图片、表情等）。
        System.out.println(groupMsg.getText());

        // 打印此次消息中的 消息内容。
        // 消息内容会包含所有的消息内容，也包括特殊消息。特殊消息使用CAT码进行表示。
        // 需要注意的是，绝大多数情况下，getMsg() 的效率低于甚至远低于 getText()
        System.out.println(groupMsg.getMsg());

        // 获取此次消息中的 消息主体。
        // messageContent代表消息主体，其中通过可以获得 msg, 以及特殊消息列表。
        // 特殊消息列表为 List<Neko>, 其中，Neko是CAT码的封装类型。

        MessageContent msgContent = groupMsg.getMsgContent();

        // 打印消息主体
        System.out.println(msgContent);
        // 打印消息主体中的所有图片的链接（如果有的话）
        List<Neko> imageCats = msgContent.getCats("image");
        System.out.println("img counts: " + imageCats.size());
        for (Neko image : imageCats) {
            System.out.println("Img url: " + image.get("url"));
        }

        // 获取发消息的人。
        GroupAccountInfo accountInfo = groupMsg.getAccountInfo();
        // 打印发消息者的账号与昵称。
        System.out.println(accountInfo.getAccountCode());
        System.out.println(accountInfo.getAccountNickname());

        // 获取群信息
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 打印群号与名称
        System.out.println(groupInfo.getGroupCode());
        System.out.println(groupInfo.getGroupName());
    }

    @Listen(PrivateMsg.class)
    @Priority(PriorityConstant.THIRD)
    public void onGroupAddRequest(PrivateMsg groupAddRequest){
        System.out.println("PriorityConstant.THIRD:"+ groupAddRequest.getText());
    }

}

