package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import service.Service;
import service.impl.ServiceImpl;

public class GetUsersCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public GetUsersCommand (ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {
        Object result = service.getUsers();
        response.setData(result);
        return response;
    }
}