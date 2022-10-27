package cooperation;

public class ClientRequest {
    private String commandName;
    private Object data;

    public ClientRequest() {}

    public ClientRequest(String commandName, Object data) {
        this.commandName = commandName;
        this.data = data;
    }

    public String getCommandName() {
        return commandName;
    }

    public Object getData() {
        return data;
    }
}
