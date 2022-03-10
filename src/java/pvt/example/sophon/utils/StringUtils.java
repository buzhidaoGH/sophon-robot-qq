package pvt.example.sophon.utils;

/**
 * 类&emsp;&emsp;名：StringUtils <br/>
 * 描&emsp;&emsp;述：
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }
}
