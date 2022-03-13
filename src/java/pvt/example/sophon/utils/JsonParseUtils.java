package pvt.example.sophon.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 类&emsp;&emsp;名：JsonParseUtils <br/>
 * 描&emsp;&emsp;述：Json字符串解析工具类
 */
public class JsonParseUtils {
    public static JSONObject jsonExtract(String json) {
        return JSONObject.parseObject(json);
    }
}
