package pvt.example.sophon.utils;

import com.alibaba.fastjson.JSONObject;
import pvt.example.sophon.config.Constants;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：HttpClientUtils <br/>
 * 描&emsp;&emsp;述：HttpClient客户端工具类
 */
public class HttpClientUtils {
    /**
     * 快速GET请求
     * @param url url地址
     * @return 页面源代码
     */
    public static String sendGetHttp(String url, Map<String, String> parameters) {
        StringBuilder result = new StringBuilder();
        String parameter = getParameter(parameters);
        String urlName = url;
        if (parameter != null && !"".equals(parameter)) {
            urlName += "?" + parameter;
        }
        //读取响应输入流
        BufferedReader in = null;
        try {
            URL realURL = new URL(urlName);
            URLConnection conn = realURL.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append("\n").append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 快速POST请求
     * @param url url地址
     * @return 页面源代码
     */
    public static String sendPostHttp(String url, Map<String, String> parameters) {
        String result = "";
        String parameter = getParameter(parameters);
        //读取响应输入流
        BufferedReader in = null;
        try {
            URL realURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realURL.openConnection();
            // 提交模式
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // 是否输入参数
            conn.setDoOutput(true);
            // 输入参数
            conn.getOutputStream().write(parameter.getBytes());
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPostHttp(String url, Map<String, String> parameters, String cookie) {
        String result = "";
        String parameter = getParameter(parameters);
        //读取响应输入流
        BufferedReader in = null;
        try {
            URL realURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realURL.openConnection();
            // 提交模式
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("cookie", cookie);
            // 是否输入参数
            conn.setDoOutput(true);
            // 输入参数
            conn.getOutputStream().write(parameter.getBytes());
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 对parameters进行解析
     * @return 组装好的参数串
     */
    private static String getParameter(Map<String, String> parameters) {
        // 处理请求参数
        StringBuffer sb = new StringBuffer();
        //编码之后的参数
        String params = null;
        if (null != parameters && parameters.size() > 0) {
            try {
                if (parameters.size() == 1) {
                    for (String name : parameters.keySet()) {
                        sb.append(name)
                          .append("=")
                          .append(java.net.URLEncoder.encode(parameters.get(name) == null ? "" : parameters.get(name),
                                                             "UTF-8"));
                    }
                    params = sb.toString();
                } else {
                    for (String name : parameters.keySet()) {
                        sb.append(name)
                          .append("=")
                          .append(java.net.URLEncoder.encode(parameters.get(name) == null ? "" : parameters.get(name),
                                                             "UTF-8"))
                          .append("&");
                    }
                    String temp_params = sb.toString();
                    params = temp_params.substring(0, temp_params.length() - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params;
    }

    /**
     * 通过url获取响应头
     * return 响应头值
     */
    public static String getResponseHeader(String method, String url, String key) {
        String headValue = null;
        try {
            URL realURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realURL.openConnection();
            // 提交模式
            conn.setRequestMethod(method);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.connect();
            headValue = conn.getHeaderField(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return headValue;
    }

    /**
     * 通过url下载资源,返回文件uuid
     */
    public static String downloaderUrlResource(String method, String url, String outPath) {
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        String uuid = StringUtils.getUUID();
        try {
            URL realURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realURL.openConnection();
            // 提交模式
            conn.setRequestMethod(method);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.connect();
            bufferedInputStream = new BufferedInputStream(conn.getInputStream());
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outPath + uuid));
            byte[] bytes = new byte[1024];
            int num = 0;
            while ((num = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, num);
                bufferedOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert bufferedOutputStream != null;
                bufferedOutputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uuid;
    }

    /**
     * 获取飙升榜的随机一个音乐 Map 信息
     */
    public static Map<String, String> getRandomMusic() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sort", "飙升榜");
        map.put("format", "json");
        try {
            URL realURL = new URL(Constants.MUSIC_SOAR_API + getParameter(map));
            URLConnection conn = realURL.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.connect();
            byte[] bytes = StringUtils.unGzipCompress(conn.getInputStream());
            map.clear();
            String json = new String(bytes, StandardCharsets.UTF_8);
            JSONObject jsonObject = JsonParseUtils.jsonExtract(json);
            if (!"1".equals(jsonObject.getString("code"))) { return null; }
            jsonObject = jsonObject.getJSONObject("data");
            map.put("name", jsonObject.getString("name"));
            map.put("musicUrl", jsonObject.getString("url")+".mp3");
            map.put("picUrl", jsonObject.getString("picurl"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
