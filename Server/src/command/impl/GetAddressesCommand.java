package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import service.Service;
import service.impl.ServiceImpl;

public class GetAddressesCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public GetAddressesCommand (ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    public ServerResponse execute() {
        Object result = service.getAddresses();
        response.setData(result);
        return response;
    }
}
