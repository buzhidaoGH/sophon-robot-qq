package pvt.example.sophon.dao;

import pvt.example.sophon.domain.Manager;

import java.util.List;

/**
 * 类&emsp;&emsp;名：Manager <br/>
 * 描&emsp;&emsp;述：管理员实体类
 */
public interface ManagerDao {
    public List<Manager> selectAllManager();

    public boolean insertManager(Manager manager);
}
