package pvt.example.sophon.listener;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.events.FriendAddRequest;
import love.forte.simbot.api.message.events.GroupAddRequest;
import love.forte.simbot.api.sender.Setter;

/**
 * 类&emsp;&emsp;名：SophonManagerListen <br/>
 * 描&emsp;&emsp;述：Sophon的整体管理监听
 */
@Beans
public class SophonManagerListen {
    /**
     * 好友申请监听
     */
    @Listen(FriendAddRequest.class)
    public void friendAddRequestListen(FriendAddRequest friendAddRequest, Setter setter){
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
    public void groupAddRequestListen(){

    }
}
