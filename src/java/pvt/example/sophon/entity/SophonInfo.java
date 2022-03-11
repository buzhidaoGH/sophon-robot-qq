package pvt.example.sophon.entity;

import pvt.example.sophon.utils.DateUtils;
import pvt.example.sophon.utils.YamlUtils;

import java.util.Map;

/**
 * 类&emsp;&emsp;名：SophonInfo <br/>
 * 描&emsp;&emsp;述：智梓的基本信息
 */
public class SophonInfo {
    private static final String name;
    private static final int age;
    private static final String gender;
    private static final String birthday;
    private static final String hobby;
    private static final String personality;

    static {
        Map<String, String> sophon = YamlUtils.getSophonByKey("sophon");
        name = sophon.get("name");
        age = Integer.parseInt(sophon.get("age")) + DateUtils.yearToNow(sophon.get("birthday"));
        gender = sophon.get("gender");
        birthday = sophon.get("birthday");
        hobby = sophon.get("hobby");
        personality = sophon.get("personality");
    }

    private static final SophonInfo sophonInfo = new SophonInfo();

    private SophonInfo() {
    }

    public static SophonInfo getSophonInfo() {
        return sophonInfo;
    }

    @Override
    public String toString() {
        return "SophonInfo{" + "name='" + name + '\'' + ", age=" + age + ", gender='" + gender + '\'' + ", birthday" + "='" + birthday + '\'' + ", hobby='" + hobby + '\'' + ", personality='" + personality + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHobby() {
        return hobby;
    }

    public String getPersonality() {
        return personality;
    }
}
