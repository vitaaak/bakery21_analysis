package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import service.Service;
import service.impl.ServiceImpl;

public class GetProductsCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public GetProductsCommand (ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {
        Object result = service.getProducts();
        response.setData(result);
        return response;
    }
}
