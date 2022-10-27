package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import service.Service;
import service.impl.ServiceImpl;

public class GetAlternativesCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public GetAlternativesCommand (ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {
        Object result = service.getAlternatives();
        response.setData(result);
        return response;
    }
}