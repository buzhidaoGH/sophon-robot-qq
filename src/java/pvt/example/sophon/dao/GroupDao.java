package pvt.example.sophon.dao;

import pvt.example.sophon.domain.Group;

import java.util.List;

/**
 * 类&emsp;&emsp;名：GroupDao <br/>
 * 描&emsp;&emsp;述：群组管理
 */
public interface GroupDao {
    public List<Group> selectAllGroup();

    public Group selectOneByGroupCode(String groupCode);

    public boolean deleteGroupByGroupCode(String groupCode);

    public boolean insertGroup(Group group);

    public boolean updateGroup(Group group);
}
