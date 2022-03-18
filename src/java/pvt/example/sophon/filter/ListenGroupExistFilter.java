package pvt.example.sophon.filter;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.containers.GroupContainer;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import org.jetbrains.annotations.NotNull;
import pvt.example.sophon.domain.Group;
import pvt.example.sophon.service.GroupService;

/**
 * 类&emsp;&emsp;名：ListenGroupExistFilter <br/>
 * 描&emsp;&emsp;述：
 */
@Beans("ListenGroupExistFilter")
public class ListenGroupExistFilter implements ListenerFilter {
    @Depend
    private GroupService groupService;

    @Override
    public boolean test(@NotNull FilterData data) {
        GroupContainer groupContainer = (GroupContainer) data.getMsgGet();
        Group group = groupService.searchGroupByGroupCode(groupContainer.getGroupInfo().getGroupCode());
        if (group == null) {
            return false;
        }
        return true;
    }
}
