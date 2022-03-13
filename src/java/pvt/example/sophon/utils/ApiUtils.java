package pvt.example.sophon.utils;

import com.alibaba.fastjson.JSONObject;
import pvt.example.sophon.config.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：ApiUtils <br/>
 * 描&emsp;&emsp;述：网络Api接口工具
 */
public class ApiUtils {
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
}
