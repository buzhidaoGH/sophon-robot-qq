package pvt.example.sophon.filter;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import org.jetbrains.annotations.NotNull;
import pvt.example.sophon.domain.Group;
import pvt.example.sophon.service.GroupService;

/**
 * 类&emsp;&emsp;名：HaveGroupManagementFilter <br/>
 * 描&emsp;&emsp;述：
 */
@Beans("HaveGroupManagementFilter")
public class HaveGroupManagementFilter implements ListenerFilter {
    @Depend
    private GroupService groupService;

    @Override
    public boolean test(@NotNull FilterData data) {
        GroupMsg groupMsg = (GroupMsg) data.getMsgGet();
        Group group = groupService.groupIsEnableAndPer(groupMsg.getGroupInfo().getGroupCode());
        if (group == null) {
            return false;
        }
        return true;
    }
}
