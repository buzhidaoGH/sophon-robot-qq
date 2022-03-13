package pvt.example.sophon.entity;

import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Constr;
import pvt.example.sophon.utils.DateUtils;
import pvt.example.sophon.utils.YamlUtils;

import java.util.Map;

/**
 * 类&emsp;&emsp;名：SophonInfo <br/>
 * 描&emsp;&emsp;述：智梓的基本信息
 */
@Beans(init = true)
public class SophonInfo {
    private static final String name;
    private static int age;
    private static final String gender;
    private static final String birthday;
    private static final String hobby;
    private static final String personality;
    private static final String nickName;

    static {
        Map<String, String> sophon = YamlUtils.getSophonByKey("sophon");
        name = sophon.get("name");
        age = Integer.parseInt(sophon.get("age")) + DateUtils.yearToNow(sophon.get("birthday"));
        gender = sophon.get("gender");
        birthday = sophon.get("birthday");
        hobby = sophon.get("hobby");
        personality = sophon.get("personality");
        nickName = sophon.get("nick-name");
    }

    private static final SophonInfo sophonInfo = new SophonInfo();

    public void setAge(int age) {
        this.age = age;
    }

    private SophonInfo() {
    }

    @Constr
    public static SophonInfo getSophonInfo() {
        return sophonInfo;
    }

    @Override
    public String toString() {
        return "SophonInfo{" + "name='" + name + '\'' + "nickName='" + nickName + '\'' + ", age=" + age + ", gender" +
                "='" + gender + '\'' + ", birthday" + "='" + birthday + '\'' + ", hobby='" + hobby + '\'' + ", " +
                "personality='" + personality + '\'' + '}';
    }

    public String getNickName() {
        return nickName;
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
