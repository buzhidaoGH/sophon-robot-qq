package pvt.example.sophon.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：JsonParseUtils <br/>
 * 描&emsp;&emsp;述：Json字符串解析工具类
 */
public class JsonParseUtils {
    public static JSONObject jsonExtract(String json) {
        return JSONObject.parseObject(json);
    }

    public static List<Map<String, String>> xhsJsonGetCards(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject.getIntValue("code") != 0) {return null;}
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("cards");
        ArrayList<Map<String, String>> mapArrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            long timestamp = jsonArray.getJSONObject(i).getJSONObject("desc").getLongValue("timestamp");
            String card = jsonArray.getJSONObject(i).getString("card");
            HashMap<String, String> cardMap = new HashMap<>();
            cardMap.put("timestamp", timestamp + "");
            cardMap.put("card", card);
            mapArrayList.add(cardMap);
        }
        return mapArrayList;
    }

    public static Map<String,String> xhsJsonCardHandler(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String, String> cardMap = new HashMap<>();
        cardMap.put("desc", jsonObject.getString("desc"));
        cardMap.put("dynamic", jsonObject.getString("dynamic"));
        cardMap.put("pic", jsonObject.getString("pic"));
        cardMap.put("title", jsonObject.getString("title"));
        return cardMap;
    }
}
