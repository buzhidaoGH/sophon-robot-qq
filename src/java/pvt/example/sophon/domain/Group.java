package pvt.example.sophon.domain;

/**
 * 类&emsp;&emsp;名：Group <br/>
 * 描&emsp;&emsp;述：管理的群
 */
public class Group {
    private int pkId;
    private String fdGroupCode;
    private String fdGroupName;
    private int fdJurisdiction;
    private String fdOwner;
    private int fdEnable;

    @Override
    public String toString() {
        return "Group{" + "pkId=" + pkId + ", fdGroupCode='" + fdGroupCode + '\'' + ", fdGroupName='" + fdGroupName + '\'' + ", fdJurisdiction=" + fdJurisdiction + ", fdOwner='" + fdOwner + '\'' + ", fdEnable=" + fdEnable + '}';
    }

    public String getEnableStr() {
        return fdEnable == 0 ? "休息中" : "运行中";
    }

    public String getJurisdictionStr() {
        return fdJurisdiction == 0 ? "无权限" : "有权限";
    }

    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    public String getFdGroupCode() {
        return fdGroupCode;
    }

    public void setFdGroupCode(String fdGroupCode) {
        this.fdGroupCode = fdGroupCode;
    }

    public String getFdGroupName() {
        return fdGroupName;
    }

    public void setFdGroupName(String fdGroupName) {
        this.fdGroupName = fdGroupName;
    }

    public int getFdJurisdiction() {
        return fdJurisdiction;
    }

    public void setFdJurisdiction(int fdJurisdiction) {
        this.fdJurisdiction = fdJurisdiction;
    }

    public String getFdOwner() {
        return fdOwner;
    }

    public void setFdOwner(String fdOwner) {
        this.fdOwner = fdOwner;
    }

    public int getFdEnable() {
        return fdEnable;
    }

    public void setFdEnable(int fdEnable) {
        this.fdEnable = fdEnable;
    }
}
