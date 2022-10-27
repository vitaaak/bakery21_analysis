package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import service.Service;
import service.impl.ServiceImpl;

public class DeleteUserCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public DeleteUserCommand (ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {
        String userLogin = request.getData().toString();
        boolean result = service.deleteUser(userLogin);
        response.setSuccess(result);
        return response;
    }
}