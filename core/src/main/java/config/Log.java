package config;

public class Log {
    private String id;
    private String create_time;
    private String ip;
    private String message;
    private String success;
    private String type;
    private String user_id;

    public Log(){}

    public Log(String id, String create_time, String ip, String message, String success, String type, String user_id) {
        this.id = id;
        this.create_time = create_time;
        this.ip = ip;
        this.message = message;
        this.success = success;
        this.type = type;
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id='" + id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", ip='" + ip + '\'' +
                ", message='" + message + '\'' +
                ", success='" + success + '\'' +
                ", type='" + type + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}