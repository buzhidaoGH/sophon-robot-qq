package pvt.example.sophon.dao;

import pvt.example.sophon.domain.Dict;

/**
 * 类&emsp;&emsp;名：DictDao <br/>
 * 描&emsp;&emsp;述：字典类Dao层
 */
public interface DictDao {
    public Dict getByFdKey(String key);
}
