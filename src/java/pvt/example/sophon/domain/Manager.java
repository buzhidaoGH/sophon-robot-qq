package pvt.example.sophon.domain;

/**
 * 类&emsp;&emsp;名：Manager <br/>
 * 描&emsp;&emsp;述：管理员映射Bean类
 */
public class Manager {
    private int pkId;
    private String fdAccount;
    private String fdNickname;
    private char fdRole;

    @Override
    public String toString() {
        return "Manager{" + "pkId=" + pkId + ", fdAccount='" + fdAccount + '\'' + ", fdNickName='" + fdNickname + '\'' + ", fdRole=" + fdRole + '}';
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

    public char getFdRole() {
        return fdRole;
    }

    public void setFdRole(char fdRole) {
        this.fdRole = fdRole;
    }
}
