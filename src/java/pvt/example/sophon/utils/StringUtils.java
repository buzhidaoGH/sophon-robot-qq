package pvt.example.sophon.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * 类&emsp;&emsp;名：StringUtils <br/>
 * 描&emsp;&emsp;述：
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * unicode解码
     */
    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }

    /**
     * GZIP解压
     */
    public static byte[] unGzipCompress(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * UUID
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(15);
    }
}
