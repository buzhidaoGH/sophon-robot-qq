package pvt.example.sophon.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * 类&emsp;&emsp;名：FileIOUtils <br/>
 * 描&emsp;&emsp;述：NIO的File处理工具
 */
public class FileIOUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FileIOUtils.class);

    public static String[] fileReadLine(String path) {
        ArrayList<String> strings = new ArrayList<>();
        InputStream is = FileIOUtils.class.getResourceAsStream(path);
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            reader = new BufferedReader(isr, 5 * 1024);
            String tem;
            while ((tem = reader.readLine()) != null) {
                // 处理读取的数据
                strings.add(tem);
            }
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
        finally {
            // 关闭相关流
            try {
                assert reader != null;
                reader.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                LOG.warn(e.getMessage());
            }
        }
        return strings.toArray(new String[0]);
    }

    public static void printFileLine(String path){
        String[] strings = FileIOUtils.fileReadLine(path);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public static FileChannel getFileChannel(String path) {
        FileChannel open = null;
        try {
            open = FileChannel.open(Paths.get(path));
        } catch (IOException e) {
            LOG.warn("${}资源不存在!", path);
            LOG.warn(e.getMessage());
        }
        return open;
    }
}
