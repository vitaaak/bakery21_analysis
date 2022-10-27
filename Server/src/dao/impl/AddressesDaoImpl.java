package dao.impl;

import dao.AddressesDao;
import entity.Addresses;
import org.codehaus.jackson.map.ObjectMapper;
import util.ConnectionPool;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AddressesDaoImpl implements AddressesDao {
    private static final AddressesDaoImpl INSTANCE = new AddressesDaoImpl();

    public static AddressesDao getInstance() {
        return INSTANCE;
    }

    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public String getAddressesObject() {
        try {
            ArrayList<Addresses> addressesList = new ArrayList<Addresses>();
            Connection connection = pool.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT city, address, telephone FROM addresses");
            while (resultSet.next()) {

                String cityName = resultSet.getString("city");
                String address = resultSet.getString("address");
                int telephone = resultSet.getInt("telephone");


                Addresses addresses = new Addresses(cityName, address, telephone);
                addressesList.add(addresses);
            }
            System.out.println(addressesList);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(addressesList);
            pool.release(connection);
            return json;

        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
            return ("default");
        }

    }

    @Override
    public boolean addAddress(Addresses address) {
        try {
            Connection connection2 = pool.get();
            PreparedStatement statement2 =
                    connection2.prepareStatement("INSERT INTO addresses(city, address, telephone) VALUES (?,?,?)");

            statement2.setString(1, address.getCityName());
            statement2.setString(2, address.getAddress());
            statement2.setInt(3, address.getTelephone());
            statement2.executeUpdate();
            pool.release(connection2);

            addToTextFile(address);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void addToTextFile(Addresses address){
        try(FileWriter writer = new FileWriter("addressFile.txt", true))
        {
            String text = address.getCityName()+", "+address.getAddress()+", телефон: "+address.getTelephone();
            writer.write(text);
            writer.write("\r\n");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println("Не удалось записать в файл");
        }
    }

    @Override
    public boolean deleteAddress(String addressDel) {
        try {
            Connection connection = pool.get();
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM addresses WHERE address = ?");
            statement.setString(1, addressDel);
            statement.executeUpdate();
            pool.release(connection);
            return true;
        } catch (SQLException throwables) {
            System.out.println("Не удалось удалить запись.");
            throwables.printStackTrace();
            return false;
        }
    }

}
