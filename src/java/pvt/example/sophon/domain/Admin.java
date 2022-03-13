package pvt.example.sophon.domain;

/**
 * 类&emsp;&emsp;名：Admin <br/>
 * 描&emsp;&emsp;述：管理员用户
 */
public class Admin {
    private int pkId;
    private String fdAccount;
    private String fdNickname;
    private String fdGroup;
    private int fdRole;

    @Override
    public String toString() {
        return "Admin{" + "pkId=" + pkId + ", fdAccount='" + fdAccount + '\'' + ", fdNickname='" + fdNickname + '\'' + ", fdGroup='" + fdGroup + '\'' + ", fdRole=" + fdRole + '}';
    }

    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    public String getFdAccount() {
        return fdAccount;
    }

    public void setFdAccount(String fdAccount) {
        this.fdAccount = fdAccount;
    }

    public String getFdNickname() {
        return fdNickname;
    }

    public void setFdNickname(String fdNickname) {
        this.fdNickname = fdNickname;
    }

    public String getFdGroup() {
        return fdGroup;
    }

    public void setFdGroup(String fdGroup) {
        this.fdGroup = fdGroup;
    }

    public int getFdRole() {
        return fdRole;
    }

    public void setFdRole(int fdRole) {
        this.fdRole = fdRole;
    }
}
