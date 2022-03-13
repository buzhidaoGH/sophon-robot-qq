package pvt.example.sophon.entity;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Constr;
import pvt.example.sophon.utils.SystemUtils;

import java.util.Map;

/**
 * 类&emsp;&emsp;名：SystemInfo <br/>
 * 描&emsp;&emsp;述：操作系统系统信息
 */
@Beans(init = true)
public class SystemInfo {
    private final String wanIp;
    private final String lanIp;
    private final String osName;
    private final String osArch;
    private final String osVersion;
    private final String javaVersion;
    private final String osMemoryTotal;
    private String osMemoryUse;
    private String osMemoryFree;
    private final String osDiskSpaceTotal;
    private String osDiskSpaceFree;
    private String osDiskSpaceUse;

    {
        wanIp = SystemUtils.getSystemWanAddress();
        lanIp = SystemUtils.getSystemLanAddress();
        osName = SystemUtils.getSystemProperties("os.name");
        osArch = SystemUtils.getSystemProperties("os.arch");
        osVersion = SystemUtils.getSystemProperties("os.version");
        javaVersion = SystemUtils.getSystemProperties("java.version");
        Map<String, String> systemMemory = SystemUtils.getSystemMemory();
        osMemoryTotal = systemMemory.get("totalMemory");
        osMemoryUse = systemMemory.get("useMemory");
        osMemoryFree = systemMemory.get("freeMemory");
        Map<String, String> systemDisk = SystemUtils.getSystemDisk();
        osDiskSpaceTotal = systemDisk.get("totalDiskSpace");
        osDiskSpaceUse = systemDisk.get("useDiskSpace");
        osDiskSpaceFree = systemDisk.get("freeDiskSpace");
    }

    private static final SystemInfo systemInfo = new SystemInfo();

    private SystemInfo() {
    }

    @Constr
    public static SystemInfo getSystemInfo() {
        return systemInfo;
    }

    public void setOsMemoryUse(String osMemoryUse) {
        this.osMemoryUse = osMemoryUse;
    }

    public void setOsMemoryFree(String osMemoryFree) {
        this.osMemoryFree = osMemoryFree;
    }

    public void setOsDiskSpaceFree(String osDiskSpaceFree) {
        this.osDiskSpaceFree = osDiskSpaceFree;
    }

    public void setOsDiskSpaceUse(String osDiskSpaceUse) {
        this.osDiskSpaceUse = osDiskSpaceUse;
    }

    @Override
    public String toString() {
        return "SystemInfo{" + "wanIp='" + wanIp + '\'' + ", lanIp='" + lanIp + '\'' + ", osName='" + osName + '\'' + ", osArch='" + osArch + '\'' + ", osVersion='" + osVersion + '\'' + ", javaVersion='" + javaVersion + '\'' + ", osMemoryTotal='" + osMemoryTotal + '\'' + ", osMemoryUse='" + osMemoryUse + '\'' + ", osMemoryFree='" + osMemoryFree + '\'' + ", osDiskSpaceTotal='" + osDiskSpaceTotal + '\'' + ", osDiskSpaceFree='" + osDiskSpaceFree + '\'' + ", osDiskSpaceUse='" + osDiskSpaceUse + '\'' + '}';
    }

    public String getOsDiskSpaceTotal() {
        return osDiskSpaceTotal;
    }

    public String getOsDiskSpaceFree() {
        return osDiskSpaceFree;
    }

    public String getOsDiskSpaceUse() {
        return osDiskSpaceUse;
    }

    public String getWanIp() {
        return wanIp;
    }

    public String getLanIp() {
        return lanIp;
    }

    public String getOsName() {
        return osName;
    }

    public String getOsArch() {
        return osArch;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public String getOsMemoryTotal() {
        return osMemoryTotal;
    }

    public String getOsMemoryUse() {
        return osMemoryUse;
    }

    public String getOsMemoryFree() {
        return osMemoryFree;
    }
}
