package sample.cooperation;

public class ServerResponse {
    private Object data;
    private Boolean isSuccess;
    private byte roleSuccess;

    public ServerResponse() {}

    public ServerResponse(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public byte getRoleSuccess() {
        return roleSuccess;
    }

    public void setRoleSuccess(byte roleSuccess) {
        this.roleSuccess = roleSuccess;
    }
}