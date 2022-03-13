package pvt.example.sophon.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：JVMUtils <br/>
 * 描&emsp;&emsp;述：JVM虚拟机工具类
 */
public class JVMUtils {
    private static final int byteToMb = 1024 * 1024;

    /**
     * JVM内存容量 MB
     * @return totalMemory, freeMemory, useMemory , processors
     */
    public static Map<String, String> getJVMMemory() {
        Map<String, String> memoryMap = new HashMap<>();
        Runtime run = Runtime.getRuntime();
        long totalMemory = run.totalMemory() / byteToMb;
        long freeMemory = run.freeMemory() / byteToMb;
        long useMemory = totalMemory - freeMemory;
        int processors = run.availableProcessors();
        memoryMap.put("totalMemory", totalMemory + " MB");
        memoryMap.put("freeMemory", freeMemory + " MB");
        memoryMap.put("useMemory", useMemory + " MB");
        memoryMap.put("processors", processors + " 核处理");
        return memoryMap;
    }
}
