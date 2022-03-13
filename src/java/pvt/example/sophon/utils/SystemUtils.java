package pvt.example.sophon.utils;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 类&emsp;&emsp;名：SystemUtils <br/>
 * 描&emsp;&emsp;述：系统工具类
 */
public class SystemUtils {
    private static final int byteToMb = 1024 * 1024;

    /**
     * 获取局域网IP地址
     */
    public static String getSystemLanAddress() {
        try {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
            while (nifs.hasMoreElements()) {
                NetworkInterface nif = nifs.nextElement();
                if (nif.getName().startsWith("wlan")) {
                    Enumeration<InetAddress> addresses = nif.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        if (addr.getAddress().length == 4) { // 速度快于 instanceof
                            return addr.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace(System.err);
        }
        return "127.0.0.1";
    }

    /**
     * 获取公网IP地址
     */
    public static String getSystemWanAddress() {
        try {
            return HttpClientUtils.sendGetHttp("https://ifconfig.me/ip", null).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    /**
     * 通过Key获取系统属性
     */
    public static String getSystemProperties(String key) {
        return System.getProperty(key);
    }

    /**
     * 系统级内存容量 MB
     * @return totalMemory, freeMemory, useMemory
     */
    public static Map<String, String> getSystemMemory() {
        Map<String, String> memoryMap = new HashMap<>();
        // 操作系统级内存情况查询
        OperatingSystemMXBean omMxB = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long freeMemory = omMxB.getFreePhysicalMemorySize() / byteToMb;
        long totalMemory = omMxB.getTotalPhysicalMemorySize() / byteToMb;
        long useMemory = totalMemory - freeMemory;
        memoryMap.put("totalMemory", totalMemory + " MB");
        memoryMap.put("freeMemory", freeMemory + " MB");
        memoryMap.put("useMemory", useMemory + " MB");
        return memoryMap;
    }

    /**
     * 系统级硬盘容量 MB
     * @return totalDiskSpace, freeDiskSpace, useDiskSpace
     */
    public static Map<String, String> getSystemDisk() {
        Map<String, String> diskMap = new HashMap<String, String>();
        long totalDiskSpace = 0L;
        long freeDiskSpace = 0L;
        long useDiskSpace = 0L;
        for (File file : File.listRoots()) {
            // 获取总容量
            totalDiskSpace += file.getTotalSpace();
            // 获取剩余容量
            useDiskSpace += file.getUsableSpace();
        }
        freeDiskSpace = totalDiskSpace - useDiskSpace;
        diskMap.put("totalDiskSpace", totalDiskSpace / byteToMb + " MB");
        diskMap.put("freeDiskSpace", freeDiskSpace / byteToMb + " MB");
        diskMap.put("useDiskSpace", useDiskSpace / byteToMb + " MB");
        return diskMap;
    }
}
