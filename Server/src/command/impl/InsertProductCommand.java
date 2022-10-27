package command.impl;

import command.Command;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entity.Products;
import org.codehaus.jackson.map.ObjectMapper;
import service.Service;
import service.impl.ServiceImpl;

import java.io.IOException;


public class InsertProductCommand  implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public InsertProductCommand(ClientRequest request, ServerResponse response) {
        this.request = request;
        this.response = response;
        this.service = ServiceImpl.getInstance();
    }

    @Override
    public ServerResponse execute() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = request.getData().toString();
            Products product = objectMapper.readValue(jsonProd, Products.class);
            boolean result = service.setProduct(product);
            response.setSuccess(result);
            return response;
        } catch (IOException e) {
            response.setSuccess(false);
            return response;
        }
    }
}
