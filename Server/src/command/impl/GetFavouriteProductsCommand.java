package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import service.Service;
import service.impl.ServiceImpl;

public class GetFavouriteProductsCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public GetFavouriteProductsCommand (ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {
        String login = request.getData().toString();
        Object result = service.getFavouriteProducts(login);
        response.setData(result);
        return response;
    }
}
