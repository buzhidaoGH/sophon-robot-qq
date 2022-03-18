package pvt.example.sophon.utils;

import com.alibaba.fastjson.JSONObject;
import pvt.example.sophon.config.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：ApiUtils <br/>
 * 描&emsp;&emsp;述：网络Api接口工具
 */
public class ApiUtils {
    /**
     * 天气API
     * @param city 城市名
     */
    public static Map<String, String> queryWeatherByCityApi(String city) {
        Map<String, String> map = new HashMap<>();
        String url = Constants.WEATHER_API + city;
        String weatherJson = StringUtils.unicodeDecode(HttpClientUtils.sendGetHttp(url, null));
        JSONObject weatherJSONObject = JsonParseUtils.jsonExtract(weatherJson);
        String errCode = weatherJSONObject.getString("errcode");
        if (!"0".equals(errCode)) {
            return null;
        }
        map.put("updateTime", weatherJSONObject.getString("updateTimeFormat"));
        map.put("city", weatherJSONObject.getString("city"));
        JSONObject day = weatherJSONObject.getJSONObject("day");
        map.put("feelsLike", day.getString("feelsLike") + "℃");
        map.put("aveTemperature", day.getString("temperature") + "℃");
        map.put("maxTemperature", day.getString("temperatureMaxSince7am") + "℃");
        map.put("phrase", day.getString("phrase"));
        map.put("visibility", day.getString("visibility") + "KM");
        map.put("windSpeed", day.getString("windSpeed") + "km/h");
        map.put("windDirCompass", day.getString("windDirCompass"));
        map.put("uvIndex", day.getString("uvIndex"));
        map.put("narrative", day.getString("narrative"));
        return map;
    }

    /**
     * 网易音乐api
     * @param musicName 音乐名称
     */
    public static Map<String, String> queryMusic163ByName(String musicName) {
        Map<String, String> map = new HashMap<>();
        map.put("s", musicName);
        map.put("offset", "0");
        map.put("limit", "1");
        map.put("type", "1");
        String resultJson = HttpClientUtils.sendPostHttp(Constants.MUSIC163_SEARCH_API, map, Constants.COOKIE_MUSIC163);
        map.clear();
        JSONObject result = JsonParseUtils.jsonExtract(resultJson).getJSONObject("result");
        if ("0".equals(result.getString("songCount"))) {
            return null;
        }
        JSONObject song = result.getJSONArray("songs").getJSONObject(0);
        map.put("name", song.getString("name"));
        map.put("id", song.getString("id"));
        String picUrl = song.getJSONObject("album").getString("blurPicUrl");
        map.put("picUrl", picUrl);
        map.put("musicUrl", Constants.MUSIC163_ADDRESS_API.replace("musicid", map.get("id")));
        return map;
    }

    /**
     * 随机笑话
     */
    public static String randomJoke() {
        return HttpClientUtils.sendGetHttp(Constants.RANDOM_JOKE, null);
    }

    /**
     * 历史上的今天
     */
    public static String lsjtEvents() {
        return HttpClientUtils.sendGetHttp(Constants.LSJT_EVENTS, null);
    }

    /**
     * 哔哩哔哩新华社今日动态
     */
    public static List<Map<String, String>> biliXhsDynamic() {
        String historyJson = HttpClientUtils.sendGetHttp(Constants.URL_DYNAMIC(), null);
        List<Map<String, String>> descMaps = JsonParseUtils.xhsJsonGetCards(historyJson);
        List<Map<String, String>> cardsMap = new ArrayList<>();
        assert descMaps != null;
        for (Map<String, String> desc : descMaps) {
            long timestamp = Long.parseLong(desc.get("timestamp"));
            if (!DateUtils.timestampIsToDay(timestamp * 1000)) { continue; }
            Map<String, String> cardMap = JsonParseUtils.xhsJsonCardHandler(desc.get("card"));
            if (null == cardMap.get("title")) { continue; }
            cardMap.put("time", DateUtils.timestampToFormat(timestamp * 1000, "yyyy-MM-dd HH:mm"));
            cardsMap.add(cardMap);
        }
        return cardsMap;
    }
}
