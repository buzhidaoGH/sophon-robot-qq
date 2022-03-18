package pvt.example.sophon.service;

import love.forte.simbot.api.message.containers.GroupInfo;
import pvt.example.sophon.domain.Group;

import java.util.List;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：GroupService <br/>
 * 描&emsp;&emsp;述：
 */
public interface GroupService {
    public Group searchGroupByGroupCode(String groupCode);

    public Group groupIsEnableAndPer(String groupCode);

    public Group insertGroup(GroupInfo groupInfo);

    public boolean updateGroup(Group group);

    public boolean deleteGroupByGroupCode(String groupCode);

    public Map<String, String> getGroupInfo(GroupInfo groupInfo);

    public boolean groupIsEnable(String groupCode);

    public List<String> searchAllGroupCodeOn();
}
