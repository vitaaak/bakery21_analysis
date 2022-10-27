package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.User;
import org.modelmapper.ModelMapper;
import service.Service;
import service.impl.ServiceImpl;

public class RegisterUserCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public RegisterUserCommand(ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(request.getData(),User.class);
        boolean result = service.registerUser(user);
        response.setSuccess(result);
        return response;
    }
}