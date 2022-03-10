package pvt.example.sophon.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：YamlUtils <br/>
 * 描&emsp;&emsp;述：
 */
public class YamlUtils {
    private static final LinkedHashMap<String, Map<String, String>> sophonYaml;

    static {
        InputStream sophonIs = YamlUtils.class.getResourceAsStream("/config/sophon.yml");
        Yaml yaml = new Yaml();
        sophonYaml = yaml.load(sophonIs);
    }

    public static Map<String, String> getSophonByKey(String key) {
        return sophonYaml.get(key);
    }

}
