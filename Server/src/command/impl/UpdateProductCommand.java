package command.impl;

        import command.Command;
        import cooperation.ClientRequest;
        import cooperation.ServerResponse;
        import entity.Products;
        import entity.User;
        import org.codehaus.jackson.JsonParseException;
        import org.codehaus.jackson.map.JsonMappingException;
        import org.codehaus.jackson.map.ObjectMapper;
        import org.modelmapper.ModelMapper;
        import service.Service;
        import service.impl.ServiceImpl;

        import java.io.IOException;


public class UpdateProductCommand  implements Command {
    private ClientRequest request;
    private ServerResponse response;
    private Service service;

    public UpdateProductCommand(ClientRequest request, ServerResponse response) {
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
            System.out.println(product.getProductName());
            boolean result = service.updateProduct(product);
            response.setSuccess(result);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response;
        }
    }
}
