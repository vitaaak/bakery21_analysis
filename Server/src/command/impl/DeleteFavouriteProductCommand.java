package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import service.Service;
import service.impl.ServiceImpl;

public class DeleteFavouriteProductCommand implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public DeleteFavouriteProductCommand (ClientRequest request, ServerResponse response){
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {

        String login = request.getData().toString();
        boolean result = service.deleteFavouriteProduct(login);
        response.setSuccess(result);
        return response;
    }

}
