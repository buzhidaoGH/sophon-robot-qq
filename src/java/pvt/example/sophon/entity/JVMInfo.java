package pvt.example.sophon.entity;

import love.forte.common.ioc.annotation.Beans;
import pvt.example.sophon.utils.JVMUtils;
import pvt.example.sophon.utils.SystemUtils;

import java.util.Map;

/**
 * 类&emsp;&emsp;名：JVMInfo <br/>
 * 描&emsp;&emsp;述：JVM虚拟机信息
 */
@Beans
public class JVMInfo {
    private final String jvmVersion;
    private final String jvmVendor;
    private final String jvmName;
    private final String totalMemory;
    private final String processors;
    private String useMemory;
    private String freeMemory;

    {
        jvmVersion = SystemUtils.getSystemProperties("java.vm.version");
        jvmVendor = SystemUtils.getSystemProperties("java.vm.vendor");
        jvmName = SystemUtils.getSystemProperties("java.vm.name");
        Map<String, String> jvmMemory = JVMUtils.getJVMMemory();
        totalMemory = jvmMemory.get("totalMemory");
        processors = jvmMemory.get("processors");
        useMemory = jvmMemory.get("useMemory");
        freeMemory = jvmMemory.get("freeMemory");
    }

    public void setUseMemory(String useMemory) {
        this.useMemory = useMemory;
    }

    public void setFreeMemory(String freeMemory) {
        this.freeMemory = freeMemory;
    }

    @Override
    public String toString() {
        return "JVMInfo{" + "jvmVersion='" + jvmVersion + '\'' + ", jvmVendor='" + jvmVendor + '\'' + ", jvmName='" + jvmName + '\'' + ", totalMemory='" + totalMemory + '\'' + ", processors='" + processors + '\'' + ", useMemory='" + useMemory + '\'' + ", freeMemory='" + freeMemory + '\'' + '}';
    }

    public String getJvmVersion() {
        return jvmVersion;
    }

    public String getJvmVendor() {
        return jvmVendor;
    }

    public String getJvmName() {
        return jvmName;
    }

    public String getTotalMemory() {
        return totalMemory;
    }

    public String getProcessors() {
        return processors;
    }

    public String getUseMemory() {
        return useMemory;
    }

    public String getFreeMemory() {
        return freeMemory;
    }
}
