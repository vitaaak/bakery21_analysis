package command.factory;

import command.Command;
import command.impl.*;
import cooperation.ClientRequest;
import cooperation.ServerResponse;

public class CommandFactory {
    private static final CommandFactory INSTANCE = new CommandFactory();

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory() {
    }

    public Command createCommand(String name, ClientRequest request, ServerResponse response) {
        switch (name) {
            case "registerUser": return new RegisterUserCommand(request,response);
            case "loginUser": return new LoginUserCommand(request, response);
            case "getProducts": return new GetProductsCommand(request, response);
            case "getUsers": return new GetUsersCommand(request, response);
            case "getAlternatives": return new GetAlternativesCommand(request, response);
            case "getCategories": return new GetCategoriesCommand(request, response);
            case "addAnalysis": return new AddAnalysisCommand(request, response);
            case "getUserInfo": return new UserInfoCommand(request, response);
            case "insertCategory": return new InsertCategoryCommand(request, response);
            case "insertProduct": return new InsertProductCommand(request, response);
            case "deleteProduct": return new DeleteProductCommand(request, response);
            case "updateProduct": return new UpdateProductCommand(request, response);
            case "deleteUser": return new DeleteUserCommand(request, response);
            case "getAnalysis": return new GetAnalysisCommand(request, response);
            case "getAddresses": return new GetAddressesCommand(request, response);
            case "insertAddress": return new InsertAddressCommand(request, response);
            case "deleteAddress": return new DeleteAddressCommand (request, response);
            case "getFavouriteProducts": return new GetFavouriteProductsCommand (request, response);
            case "deleteFavouriteProduct": return new DeleteFavouriteProductCommand (request, response);
            case "insertProdToFavourites":return  new InsertProdToFavouritesCommand (request, response);
        }
        throw new RuntimeException();
    }
}