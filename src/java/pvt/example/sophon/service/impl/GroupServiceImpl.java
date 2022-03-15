package pvt.example.sophon.service.impl;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.results.GroupAdmin;
import love.forte.simbot.api.message.results.GroupFullInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pvt.example.sophon.config.PreprocessUtil;
import pvt.example.sophon.config.SophonInitConfig;
import pvt.example.sophon.dao.GroupDao;
import pvt.example.sophon.domain.Group;
import pvt.example.sophon.service.GroupService;
import pvt.example.sophon.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：GroupServiceImpl <br/>
 * 描&emsp;&emsp;述：
 */
@Beans
public class GroupServiceImpl implements GroupService {
    @Depend
    private PreprocessUtil preprocessUtil;
    private static final Logger LOG = LoggerFactory.getLogger(GroupServiceImpl.class);

    /**
     * @param groupCode 群号
     * @return 通过群号返回 group
     */
    @Override
    public Group searchGroupByGroupCode(String groupCode) {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
        Group group = groupDao.selectOneByGroupCode(groupCode);
        sqlSession.close();
        return group;
    }

    /**
     * @param groupInfo 基本信息
     * @return 通过群基本信息插入数据到tab表中
     */
    @Override
    public Group insertGroup(GroupInfo groupInfo) {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
        Group group = new Group();
        GroupFullInfo groupFullInfo = preprocessUtil.getGroupFullInfoByGroupInfo(groupInfo);
        group.setFdGroupCode(groupFullInfo.getGroupCode());
        group.setFdGroupName(groupFullInfo.getGroupName());
        group.setFdOwner(groupFullInfo.getOwner().getAccountNickname());
        int jurisdiction = preprocessUtil.botIsGroupAdmin(groupInfo) ? 1 : 0;
        group.setFdJurisdiction(jurisdiction);
        boolean flag = false;
        try {
            flag = groupDao.insertGroup(group);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            LOG.warn("错误信息：{}", e.getMessage());
        } finally {
            sqlSession.close();
        }
        return flag ? group : null;
    }

    /**
     * @param group 群对象
     * @return 根据群对象更新数据
     */
    @Override
    public boolean updateGroup(Group group) {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
        boolean flag = groupDao.updateGroup(group);
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    /**
     * @param groupCode 群号
     * @return 根据群号删除
     */
    @Override
    public boolean deleteGroupByGroupCode(String groupCode) {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
        boolean flag = groupDao.deleteGroupByGroupCode(groupCode);
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    @Override
    public Map<String, String> getGroupInfo(GroupInfo groupInfo) {
        Map<String, String> mapGroup = new HashMap<String, String>();
        GroupFullInfo groupFullInfo = preprocessUtil.getGroupFullInfoByGroupInfo(groupInfo);
        mapGroup.put("owner", groupFullInfo.getOwner().getAccountNickname());
        List<String> adminName = new ArrayList<>();
        for (GroupAdmin admin : groupFullInfo.getAdmins()) {
            adminName.add(admin.getAccountNickname());
        }
        mapGroup.put("adminCount", adminName.size() + "人");
        if (adminName.size() != 0) {
            mapGroup.put("adminNames", String.join(",", adminName));
        } else {
            mapGroup.put("adminNames", "无管理");
        }
        mapGroup.put("createTime", DateUtils.timestampToFormat(groupFullInfo.getCreateTime(), "yyyy-MM-dd HH:mm"));
        mapGroup.put("total", groupFullInfo.getTotal() + "人");
        Map<String, Long> lastTime = preprocessUtil.getGroupLastTime(groupInfo);
        mapGroup.put("lastSpeak", DateUtils.timestampToFormat(lastTime.get("lastSpeakTime"), "yyyy-MM-dd HH:mm"));
        mapGroup.put("lastJoin", DateUtils.timestampToFormat(lastTime.get("lastJoinTime"), "yyyy-MM-dd HH:mm"));
        return mapGroup;
    }

    @Override
    public boolean groupIsEnable(String groupCode) {
        SqlSession sqlSession = SophonInitConfig.getSqlSession();
        GroupDao groupDao = sqlSession.getMapper(GroupDao.class);
        Group group = groupDao.selectOneByGroupCode(groupCode);
        sqlSession.close();
        if (group==null){
            return false;
        }
        return group.getFdEnable() != 0;
    }
}
