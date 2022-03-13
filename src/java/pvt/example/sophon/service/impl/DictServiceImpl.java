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
    private final DictDao dictDao;

    {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        dictDao = sqlSession.getMapper(DictDao.class);
    }

    @Override
    public List<String> getCommandList() {
        Dict command = dictDao.getByFdKey("command");
        String fdValue = command.getFdValue();
        return Arrays.asList(fdValue.split(","));
    }

    @Override
    public List<String> getServeList() {
        Dict serve = dictDao.getByFdKey("serve");
        String fdValue = serve.getFdValue();
        return Arrays.asList(fdValue.split(","));
    }
}
