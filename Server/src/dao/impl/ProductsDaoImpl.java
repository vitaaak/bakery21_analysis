package dao.impl;

import dao.ProductsDao;
import entity.Products;
import org.codehaus.jackson.map.ObjectMapper;
import util.ConnectionPool;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ProductsDaoImpl implements ProductsDao {
    private static final ProductsDaoImpl INSTANCE = new ProductsDaoImpl();

    public static ProductsDao getInstance() {
        return INSTANCE;
    }

    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public String getProductsObject() {
        try {
            ArrayList<Products> productsList = new ArrayList<Products>();
            Connection connection = pool.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT product.productname, product.price, product.kcal, product.weight, categories.categoryname FROM product INNER JOIN categories ON product.idcategory=categories.idcategory");
            while (resultSet.next()) {

                String productName = resultSet.getString("productname");
                String categoryName = resultSet.getString("categories.categoryname");
                double price = resultSet.getDouble("price");
                int kcal = resultSet.getInt("kcal");
                int weight = resultSet.getInt("weight");


                Products product = new Products(categoryName, productName, price, kcal, weight);
                productsList.add(product);
            }
            System.out.println(productsList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(productsList);
            pool.release(connection);
            return json;

        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
            return ("default");
        }

    }

    public String getAlternativesObject() {
        try {
            ArrayList<String> productsList = new ArrayList<String>();
            Connection connection = pool.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT productname FROM product");
            while (resultSet.next()) {

                String productName = resultSet.getString("productname");
                productsList.add(productName);
            }
            System.out.println(productsList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(productsList);
            pool.release(connection);
            return json;

        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
            return ("default");
        }
    }

    @Override
    public Object getCategoriesObject() {
        try {
            ArrayList<String> categoriesList = new ArrayList<String>();
            Connection connection = pool.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT categoryname FROM categories");
            while (resultSet.next()) {

                String categoryName = resultSet.getString("categoryname");
                categoriesList.add(categoryName);
            }
            System.out.println(categoriesList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(categoriesList);
            pool.release(connection);
            return json;

        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
            return ("default");
        }
    }

    @Override
    public boolean addCategory(String category) {
        try {
            Connection connection = pool.get();
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO categories(categoryname) VALUES (?)");

            statement.setString(1, category);
            statement.executeUpdate();
            pool.release(connection);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addProduct(Products product) {
        try {
            Connection connection = pool.get();
            PreparedStatement statement =
                    connection.prepareStatement("SELECT idcategory FROM categories WHERE categoryname = ?");
            statement.setString(1, product.getCategoryName());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idcategory");
                if (insertIntoProduct(id, product)) return true;
            }

            pool.release(connection);
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }


    boolean insertIntoProduct(int id, Products product) {
        try {
            Connection connection2 = pool.get();
            PreparedStatement statement2 =
                    connection2.prepareStatement("INSERT INTO product(productname, price, kcal, weight, idcategory) VALUES (?,?,?,?,?)");

            statement2.setString(1, product.getProductName());
            statement2.setDouble(2, product.getPrice());
            statement2.setInt(3, product.getKcal());
            statement2.setInt(4, product.getWeight());
            statement2.setInt(5, id);
            statement2.executeUpdate();
            pool.release(connection2);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProductName(String productName) {
        try {
            Connection connection = pool.get();
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM product WHERE productname = ?");
            statement.setString(1, productName);
            statement.executeUpdate();
            pool.release(connection);
            return true;
        } catch (SQLException throwables) {

            System.out.println("Не удалось удалить продукт.");
            throwables.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateProductInfo(Products product) {
        try {
            Connection connection = pool.get();

            if (product.getPrice() != 0) {
                PreparedStatement statement =
                        connection.prepareStatement("UPDATE product SET price = ? WHERE productname = ?");
                statement.setDouble(1, product.getPrice());
                statement.setString(2, product.getProductName());
                statement.executeUpdate();
            }
            if (product.getKcal() != 0) {
                PreparedStatement statement =
                        connection.prepareStatement("UPDATE product SET kcal = ? WHERE productname = ?");
                statement.setInt(1, product.getKcal());
                statement.setString(2, product.getProductName());
                statement.executeUpdate();
            }
            if (product.getWeight() != 0) {
                PreparedStatement statement =
                        connection.prepareStatement("UPDATE product SET weight = ? WHERE productname = ?");
                statement.setInt(1, product.getWeight());
                statement.setString(2, product.getProductName());
                statement.executeUpdate();
            }

            pool.release(connection);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Object getFavouriteProductsObject(String login) {

        try {
            ArrayList<Products> productsList = new ArrayList<Products>();

            Connection connection = pool.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT product.productname, product.price, product.kcal, product.weight, favourites.userslogin FROM product INNER JOIN favourites ON product.idproduct=favourites.productsid");
            while (resultSet.next()) {
                if (resultSet.getString("favourites.userslogin").equals(login)) {
                    String productName = resultSet.getString("productname");
                    String categoryName = "";
                    double price = resultSet.getDouble("price");
                    int kcal = resultSet.getInt("kcal");
                    int weight = resultSet.getInt("weight");
                    Products product = new Products(categoryName, productName, price, kcal, weight);
                    productsList.add(product);
                }

            }
            System.out.println(productsList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(productsList);
            pool.release(connection);
            return json;

        } catch (SQLException | IOException sqlException) {
            sqlException.printStackTrace();
            return "default";
        }


    }

    @Override
    public boolean deleteFavProducts(String login) {
        try {
            Connection connection = pool.get();
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM favourites WHERE userslogin = ?");
            statement.setString(1, login);
            statement.executeUpdate();
            pool.release(connection);
            return true;
        } catch (SQLException throwables) {

            System.out.println("Не удалось удалить записи.");
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addProdToFavourites(Products product) {
        try {


            Connection connection = pool.get();

            PreparedStatement statement =
                    connection.prepareStatement("SELECT idproduct FROM product WHERE productname = ?");
            statement.setString(1, product.getProductName());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idproduct");
                PreparedStatement statement2 =
                        connection.prepareStatement("INSERT INTO favourites(userslogin, productsid) VALUES (?,?)");

                statement2.setString(1, product.getCategoryName());
                statement2.setDouble(2, id);
                statement2.executeUpdate();
                pool.release(connection);

            }
            return true;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }


}


