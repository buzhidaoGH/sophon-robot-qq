package pvt.example.sophon.service.impl;

import love.forte.common.ioc.annotation.Beans;
import org.apache.ibatis.session.SqlSession;
import pvt.example.sophon.config.SophonInitConfig;
import pvt.example.sophon.dao.ManagerDao;
import pvt.example.sophon.domain.Manager;
import pvt.example.sophon.service.ManagerService;

import java.util.ArrayList;
import java.util.List;

/**
 * 类&emsp;&emsp;名：ManagerServiceImpl <br/>
 * 描&emsp;&emsp;述：Manager服务实现类
 */
@Beans
public class ManagerServiceImpl implements ManagerService {
    private final ManagerDao managerDao;
    {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        managerDao = sqlSession.getMapper(ManagerDao.class);
    }

    @Override
    public List<String> managerAccountList() {
        List<Manager> managerList = managerDao.selectAllManager();
        List<String> managerAccountList = new ArrayList<String>();
        for (Manager manager : managerList) {
            managerAccountList.add(manager.getFdAccount());
        }
        return managerAccountList;
    }
}
