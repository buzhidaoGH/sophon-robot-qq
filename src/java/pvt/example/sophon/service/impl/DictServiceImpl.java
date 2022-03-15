package pvt.example.sophon.service.impl;

import love.forte.common.ioc.annotation.Beans;
import org.apache.ibatis.session.SqlSession;
import pvt.example.sophon.config.SophonInitConfig;
import pvt.example.sophon.dao.DictDao;
import pvt.example.sophon.domain.Dict;
import pvt.example.sophon.service.DictService;

import java.util.Arrays;
import java.util.List;

/**
 * 类&emsp;&emsp;名：DictServiceImpl <br/>
 * 描&emsp;&emsp;述：
 */
@Beans
public class DictServiceImpl implements DictService {
    @Override
    public List<String> getCommandList() {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        DictDao dictDao = sqlSession.getMapper(DictDao.class);
        Dict command = dictDao.getByFdKey("command");
        sqlSession.close();
        String fdValue = command.getFdValue();
        return Arrays.asList(fdValue.split(","));
    }

    @Override
    public List<String> getServeList() {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        DictDao dictDao = sqlSession.getMapper(DictDao.class);
        Dict serve = dictDao.getByFdKey("serve");
        sqlSession.close();
        String fdValue = serve.getFdValue();
        return Arrays.asList(fdValue.split(","));
    }

    @Override
    public List<String> getGroupList() {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        DictDao dictDao = sqlSession.getMapper(DictDao.class);
        Dict group = dictDao.getByFdKey("group");
        sqlSession.close();
        String fdValue = group.getFdValue();
        return Arrays.asList(fdValue.split(","));
    }

}
