package dao.impl;

import dao.AnalysisDao;
import entity.Analysis;
import entity.Products;
import org.codehaus.jackson.map.ObjectMapper;
import util.ConnectionPool;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AnalysisDaoImpl implements AnalysisDao {
    private static final AnalysisDaoImpl INSTANCE = new AnalysisDaoImpl();

    public static AnalysisDao getInstance() {
        return INSTANCE;
    }

    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public String getAnalysisObject() {
        try {
            ArrayList<Analysis> analysisList = new ArrayList<Analysis>();
            Connection connection = pool.get();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Product1, Product2, Product3, Product4, Mark1, Mark2, Mark3, Mark4 FROM analysis ");
            while (resultSet.next()) {

                String a1 = resultSet.getString("Product1");
                String a2 = resultSet.getString("Product2");
                String a3 = resultSet.getString("Product3");
                String a4 = resultSet.getString("Product4");
                double w1 = resultSet.getDouble("Mark1");
                double w2 = resultSet.getDouble("Mark2");
                double w3 = resultSet.getDouble("Mark3");
                double w4 = resultSet.getDouble("Mark4");


                Analysis analysis = new Analysis(a1, a2, a3, a4, w1, w2, w3, w4);
                analysisList.add(analysis);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(analysisList);
            pool.release(connection);
            return json;

        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
            return ("default");
        }
    }

    public boolean addAnalysisResults(Analysis analysis){
        try {
            Connection connection = pool.get();
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO analysis(Product1, Product2, Product3, Product4, Mark1, Mark2, Mark3, Mark4, ScaleSize, N) VALUES (?,?,?,?,?,?,?,?,?,?)");

            statement.setString(1, analysis.getAlternative1());
            statement.setString(2, analysis.getAlternative2());
            statement.setString(3, analysis.getAlternative3());
            statement.setString(4, analysis.getAlternative4());
            statement.setDouble(5, analysis.getW1());
            statement.setDouble(6, analysis.getW2());
            statement.setDouble(7, analysis.getW3());
            statement.setDouble(8, analysis.getW4());
            statement.setInt(9, analysis.getScale());
            statement.setInt(10, analysis.getN());
            statement.executeUpdate();
            pool.release(connection);

            addToTextFile(analysis);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void addToTextFile(Analysis analysis){
        try(FileWriter writer = new FileWriter("AnalysisFile.txt", true))
        {
            String text = "Альтернатива 1: "+analysis.getAlternative1()+", альтернатива 2: "+analysis.getAlternative2()+", альтернатива 3: "+analysis.getAlternative3()+", альтернатива 4: "+analysis.getAlternative4()+", искомые веса целей W1=: "+analysis.getW1()+", W2=: "+analysis.getW2()+", W3=: "+analysis.getW3()+", W4=: "+analysis.getW4()+", размер шкалы: "+analysis.getScale();
            writer.write(text);
            writer.write("\r\n");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println("Не удалось записать в файл");
        }
    }
}
