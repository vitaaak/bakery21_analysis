package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import service.Service;
import service.impl.ServiceImpl;

public class DeleteAddressCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public DeleteAddressCommand (ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {
        String addressDel = request.getData().toString();
        boolean result = service.deleteAddress(addressDel);
        response.setSuccess(result);
        return response;
    }
}
