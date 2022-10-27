package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.User;
import org.modelmapper.ModelMapper;
import service.Service;
import service.impl.ServiceImpl;

public class UserInfoCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public UserInfoCommand(ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }
    @Override
    public ServerResponse execute() {
        String login = request.getData().toString();
        String result = service.getUserInfo(login);
        response.setData(result);
        return response;
    }
}