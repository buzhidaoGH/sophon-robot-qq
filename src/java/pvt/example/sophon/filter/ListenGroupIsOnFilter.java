package pvt.example.sophon.filter;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import org.jetbrains.annotations.NotNull;
import pvt.example.sophon.service.GroupService;

/**
 * 类&emsp;&emsp;名：ListenGroupFilter <br/>
 * 描&emsp;&emsp;述：判断智梓 在此群是否为启动状态
 */
@Beans("ListenGroupIsOnFilter")
public class ListenGroupIsOnFilter  implements ListenerFilter {
    @Depend
    private GroupService groupService;
    @Override
    public boolean test(@NotNull FilterData data) {
        GroupMsg groupMsg = (GroupMsg) data.getMsgGet();
        return groupService.groupIsEnable(groupMsg.getGroupInfo().getGroupCode());
    }
}
