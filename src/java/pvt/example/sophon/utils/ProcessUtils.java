package pvt.example.sophon.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 类&emsp;&emsp;名：ProcessUtils <br/>
 * 描&emsp;&emsp;述：命令行工具类
 */
public class ProcessUtils {
    public static boolean exec(String cmd) {
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            return exec.waitFor() == 0;//等待,直到执行完毕
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String execResult(String cmd) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            //获取输出流，并包装到BufferedReader中
            BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String line = null;
            while((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            exec.waitFor();//等待,直到执行完毕
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
