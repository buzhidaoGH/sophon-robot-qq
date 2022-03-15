package pvt.example.sophon.filter;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类&emsp;&emsp;名：GroupOwnerFilter <br/>
 * 描&emsp;&emsp;述：群中领导判断
 */
@Beans("GroupLeaderFilter")
public class GroupLeaderFilter implements ListenerFilter {
    private static final Logger LOG = LoggerFactory.getLogger(GroupLeaderFilter.class);

    @Override
    public boolean test(@NotNull FilterData data) {
        GroupMsg groupMsg = null;
        try {
            groupMsg = (GroupMsg) data.getMsgGet();
        } catch (Exception e) {
            LOG.warn("异常:{}", e.getMessage());
            return false;
        }
        return groupMsg.getPermission().isOwnerOrAdmin();
    }
}
