package pvt.example.sophon.config;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.containers.Containers;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.results.GroupAdmin;
import love.forte.simbot.api.message.results.GroupFullInfo;
import love.forte.simbot.api.message.results.GroupMemberInfo;
import love.forte.simbot.api.message.results.GroupMemberList;
import love.forte.simbot.api.sender.Getter;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.api.sender.Setter;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.bot.BotManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：PreprocessUtils <br/>
 * 描&emsp;&emsp;述：预处理工具类
 */
@Beans
public class PreprocessUtil {
    @Depend
    private BotManager botManager;

    /**
     * 通过群信息返回更为全的群信息
     */
    public GroupFullInfo getGroupFullInfoByGroupInfo(GroupInfo groupInfo) {
        Getter getter = botManager.getDefaultBot().getSender().GETTER;
        return getter.getGroupInfo(Containers.getGroupContainer(groupInfo));
    }

    public Setter getSetter(){
        return botManager.getDefaultBot().getSender().SETTER;
    }
    public Sender getSender(){
        return botManager.getDefaultBot().getSender().SENDER;
    }
    public Getter getGetter(){
        return botManager.getDefaultBot().getSender().GETTER;
    }

    /**
     * 通过群信息查询最后的发言时间和最后的入群时间
     */
    public Map<String, Long> getGroupLastTime(GroupInfo groupInfo) {
        Bot defaultBot = botManager.getDefaultBot();
        String accountCode = defaultBot.getBotInfo().getAccountCode();
        Getter getter = defaultBot.getSender().GETTER;
        GroupMemberList groupMemberList = getter.getGroupMemberList(groupInfo);
        long lastSpeakTime = 0;
        long lastJoinTime = 0;
        for (GroupMemberInfo groupMemberInfo : groupMemberList) {
            if (accountCode.equals(groupMemberInfo.getAccountCode())) { continue; }
            if (lastSpeakTime <= groupMemberInfo.getLastSpeakTime()) {
                lastSpeakTime = groupMemberInfo.getLastSpeakTime();
            }
            if (lastJoinTime <= groupMemberInfo.getJoinTime()) {
                lastJoinTime = groupMemberInfo.getJoinTime();
            }
        }
        Map<String, Long> lastTime = new HashMap<>();
        lastTime.put("lastSpeakTime", lastSpeakTime);
        lastTime.put("lastJoinTime", lastJoinTime);
        return lastTime;
    }

    /**
     * 判断bot是否属于此群的管理位置
     */
    public boolean botIsGroupAdmin(GroupInfo groupInfo) {
        String botCode = botManager.getDefaultBot().getBotInfo().getAccountCode();
        System.out.println("botCode = " + botCode);
        GroupFullInfo groupFullInfo = getGroupFullInfoByGroupInfo(groupInfo);
        if (botCode.equals(groupFullInfo.getOwner().getAccountCode())) {
            return true;
        }
        for (GroupAdmin admin : groupFullInfo.getAdmins()) {
            if (botCode.equals(admin.getAccountCode())) {
                return true;
            }
        }
        return false;
    }



}
