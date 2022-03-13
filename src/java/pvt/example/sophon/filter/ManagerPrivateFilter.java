package pvt.example.sophon.filter;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import org.jetbrains.annotations.NotNull;
import pvt.example.sophon.service.ManagerService;

import java.util.List;

/**
 * 类&emsp;&emsp;名：ManagerFilter <br/>
 * 描&emsp;&emsp;述：管理员私聊过滤器
 */
@Beans("ManagerPrivateFilter")
public class ManagerPrivateFilter implements ListenerFilter {
    @Depend
    private ManagerService managerService;

    @Override
    public boolean test(@NotNull FilterData data) {
        PrivateMsg privateMsg = (PrivateMsg) data.getMsgGet();
        AccountInfo accountInfo = privateMsg.getAccountInfo();
        List<String> managerAccountList = managerService.managerAccountList();
        return managerAccountList.contains(accountInfo.getAccountCode());
    }
}
