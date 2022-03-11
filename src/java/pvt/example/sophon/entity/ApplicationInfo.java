package pvt.example.sophon.entity;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.containers.BotInfo;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.bot.BotManager;

/**
 * 类&emsp;&emsp;名：ApplicationInfo <br/>
 * 描&emsp;&emsp;述：Application应用信息
 */
@Beans
public class ApplicationInfo {
    @Depend
    private BotManager botManager;
    private String accountAvatar;
    private final String accountCode;
    private String accountNickname;
    private String accountLevel;

    {
        Bot defaultBot = botManager.getDefaultBot();
        BotInfo botInfo = defaultBot.getBotInfo();
        accountAvatar = botInfo.getAccountAvatar();
        accountCode = botInfo.getAccountCode();
        accountNickname = botInfo.getAccountNickname();
        accountLevel = botInfo.getBotLevel()+" 级";
    }

    @Override
    public String toString() {
        return "ApplicationInfo{" + "accountAvatar='" + accountAvatar + '\'' + ", accountCode='" + accountCode + '\'' + ", accountNickname='" + accountNickname + '\'' + ", accountLevel='" + accountLevel + '\'' + '}';
    }

    public void setAccountAvatar(String accountAvatar) {
        this.accountAvatar = accountAvatar;
    }

    public void setAccountNickname(String accountNickname) {
        this.accountNickname = accountNickname;
    }

    public void setAccountLevel(String accountLevel) {
        this.accountLevel = accountLevel;
    }

    public String getAccountAvatar() {
        return accountAvatar;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public String getAccountNickname() {
        return accountNickname;
    }

    public String getAccountLevel() {
        return accountLevel;
    }
}
