package pvt.example.sophon.listener.handle;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.bot.BotManager;
import pvt.example.sophon.entity.ApplicationInfo;
import pvt.example.sophon.entity.JVMInfo;
import pvt.example.sophon.entity.SophonInfo;
import pvt.example.sophon.entity.SystemInfo;
import pvt.example.sophon.service.DictService;

import java.util.List;

/**
 * 类&emsp;&emsp;名：ManagerPrivateListenHandle <br/>
 * 描&emsp;&emsp;述：ManagerPrivateListen监听器的Handle处理器类
 */
@Beans
public class ManagerPrivateListenHandle {
    @Depend
    private DictService dictService;
    @Depend
    private MessageContentBuilderFactory builderFactory;
    @Depend
    private BotManager botManager;
    @Depend
    private JVMInfo jvmInfo;
    @Depend
    private SophonInfo sophonInfo;
    @Depend
    private SystemInfo systemInfo;

    /**
     * 帮助命令信息发送
     */
    public void senderMsgHelp(AccountInfo accountInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        List<String> commandList = dictService.getCommandList();
        messageContentBuilder.text("帮助命令面板：").face(12);
        messageContentBuilder.text("\n命令格式：command option");
        for (String command : commandList) { messageContentBuilder.text("\n— " + command); }
        sender.sendPrivateMsg(accountInfo, messageContentBuilder.build());
    }

    /**
     * App信息发送
     */
    public void senderMsgAppInfo(AccountInfo accountInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        ApplicationInfo applicationInfo = new ApplicationInfo(botManager);
        messageContentBuilder.text("Application基本信息：");
        messageContentBuilder.text("\n— 头像：").imageUrl(applicationInfo.getAccountAvatar());
        messageContentBuilder.text("\n— 账号：" + applicationInfo.getAccountCode());
        messageContentBuilder.text("\n— 昵称：" + applicationInfo.getAccountNickname());
        messageContentBuilder.text("\n— 等级：" + applicationInfo.getAccountLevel());
        sender.sendPrivateMsg(accountInfo, messageContentBuilder.build());
    }

    /**
     * JVM信息发送
     */
    public void senderMsgJVMInfo(AccountInfo accountInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        messageContentBuilder.text("JVM基本信息：").face(4);
        messageContentBuilder.text("\n名\u3000\u3000称："+jvmInfo.getJvmName());
        messageContentBuilder.text("\n版\u3000\u3000本："+jvmInfo.getJvmVersion());
        messageContentBuilder.text("\n厂\u3000\u3000商："+jvmInfo.getJvmVendor());
        messageContentBuilder.text("\n全部内存："+jvmInfo.getTotalMemory());
        messageContentBuilder.text("\n使用内存："+jvmInfo.getUseMemory());
        messageContentBuilder.text("\n空闲内存："+jvmInfo.getFreeMemory());
        messageContentBuilder.text("\n线程数量："+jvmInfo.getProcessors());
        sender.sendPrivateMsg(accountInfo, messageContentBuilder.build());
    }

    /**
     * 智梓信息发送
     */
    public void senderMsgSophonInfo(AccountInfo accountInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        messageContentBuilder.text("Sophon基本信息：").face(6);
        messageContentBuilder.text("\n 姓名："+sophonInfo.getName());
        messageContentBuilder.text("\n 昵称："+sophonInfo.getNickName());
        messageContentBuilder.text("\n 年龄："+sophonInfo.getAge());
        messageContentBuilder.text("\n 性别："+sophonInfo.getGender());
        messageContentBuilder.text("\n 生日："+sophonInfo.getBirthday());
        messageContentBuilder.text("\n 性格："+sophonInfo.getPersonality());
        messageContentBuilder.text("\n 爱好："+sophonInfo.getHobby());
        sender.sendPrivateMsg(accountInfo, messageContentBuilder.build());
    }

    /**
     * System信息发送
     */
    public void senderMsgSystemInfo(AccountInfo accountInfo, Sender sender) {
        MessageContentBuilder messageContentBuilder = builderFactory.getMessageContentBuilder();
        messageContentBuilder.text("System基本信息：");
        messageContentBuilder.text("\n 系统名称："+systemInfo.getOsName());
        messageContentBuilder.text("\n 系统版本："+systemInfo.getOsVersion());
        messageContentBuilder.text("\n 系统架构："+systemInfo.getOsArch());
        messageContentBuilder.text("\n Java版本："+systemInfo.getJavaVersion());
        messageContentBuilder.text("\n 局域网IP："+systemInfo.getLanIp());
        messageContentBuilder.text("\n 公\u3000网IP："+systemInfo.getWanIp());
        messageContentBuilder.text("\n 硬盘总容量："+systemInfo.getOsDiskSpaceTotal());
        messageContentBuilder.text("\n 硬盘所用容量："+systemInfo.getOsDiskSpaceUse());
        messageContentBuilder.text("\n 硬盘未用容量："+systemInfo.getOsDiskSpaceFree());
        messageContentBuilder.text("\n 内存总容量："+systemInfo.getOsMemoryTotal());
        messageContentBuilder.text("\n 内存所用容量："+systemInfo.getOsMemoryUse());
        messageContentBuilder.text("\n 内存未用容量："+systemInfo.getOsMemoryFree());
        sender.sendPrivateMsg(accountInfo, messageContentBuilder.build());
    }
}
