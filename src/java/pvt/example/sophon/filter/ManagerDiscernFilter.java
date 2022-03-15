package pvt.example.sophon.filter;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import org.jetbrains.annotations.NotNull;
import pvt.example.sophon.service.ManagerService;

import java.util.List;

/**
 * 类&emsp;&emsp;名：ManagerFilter <br/>
 * 描&emsp;&emsp;述：管理员私聊过滤器
 */
@Beans("ManagerDiscernFilter")
public class ManagerDiscernFilter implements ListenerFilter {
    @Depend
    private ManagerService managerService;

    @Override
    public boolean test(@NotNull FilterData data) {
        MsgGet msgGet = data.getMsgGet();
        AccountInfo accountInfo = msgGet.getAccountInfo();
        List<String> managerAccountList = managerService.managerAccountList();
        return managerAccountList.contains(accountInfo.getAccountCode());
    }
}
