package pvt.example.sophon.service;

import java.util.List;

/**
 * 类&emsp;&emsp;名：DictService <br/>
 * 描&emsp;&emsp;述：
 */
public interface DictService {
    public List<String> getCommandList();

    public List<String> getServeList();

    public String getFriendVerification();

    public String getGroupVerification();

    public List<String> getGroupList();

    public List<String> getGroupServeList();
}
