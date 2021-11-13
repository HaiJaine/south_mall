package top.top6699.mall.model.request;

/**
 * @author LongHaiJiang
 * @date 2021/11/14 0:09
 * @description
 **/
public class UserReq {
    private String username;
    private String password;
    private String signature;

    @Override
    public String toString() {
        return "UserReq{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
