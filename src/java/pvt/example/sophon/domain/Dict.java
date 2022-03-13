package pvt.example.sophon.domain;

/**
 * 类&emsp;&emsp;名：Dict <br/>
 * 描&emsp;&emsp;述：字典Dict表
 */
public class Dict {
    private int pkId;
    private String fdKey;
    private String fdValue;

    @Override
    public String toString() {
        return "Dict{" + "pkId=" + pkId + ", fdKey='" + fdKey + '\'' + ", fdValue='" + fdValue + '\'' + '}';
    }

    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    public String getFdKey() {
        return fdKey;
    }

    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
    }

    public String getFdValue() {
        return fdValue;
    }

    public void setFdValue(String fdValue) {
        this.fdValue = fdValue;
    }
}
