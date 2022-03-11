package pvt.example.sophon.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：HttpClientUtils <br/>
 * 描&emsp;&emsp;述：HttpClient客户端工具类
 */
public class HttpClientUtils {
    public static String sendGetHttp(String url, Map<String, String> parameters) {
        String result = "";
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
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

    public static String getParameter(Map<String, String> parameters) {
        // 处理请求参数
        StringBuffer sb = new StringBuffer();
        //编码之后的参数
        String params = null;
        if (null != parameters && parameters.size() > 0) {
            try {
                if (parameters.size() == 1) {
                    for (String name : parameters.keySet()) {
                        sb.append(name).append("=").append(
                                java.net.URLEncoder.encode(parameters.get(name) == null ? "" : parameters.get(name),
                                                           "UTF-8"));
                    }
                    params = sb.toString();
                } else {
                    for (String name : parameters.keySet()) {
                        sb.append(name).append("=").append(
                                java.net.URLEncoder.encode(parameters.get(name) == null ? "" : parameters.get(name),
                                                           "UTF-8")).append("&");
                    }
                    String temp_params = sb.toString();
                    params = temp_params.substring(0, temp_params.length() - 1);
                }
            } catch (Exception e) {
            }
        }
        return params;
    }
}
