package gaussiansolver.dbClients;


import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Josef Mayer on 23.06.2017.
 */

@Configuration
public class MySQLClient_2 {

    public MySQLClient_2 mySQLclient(){
        return new MySQLClient_2();
    }

    private final String driverClassName = "com.mysql.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/gaussian";
    private final String user = "root";
    private final String password = "root";

    public void createTable() {
        //String query = "CREATE TABLE gaussian21 (jdoc JSON);";
        String query = "CREATE TABLE gaussian2 (id int not null primary key auto_increment, jdoc JSON);";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)){
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insert(String indocument, String result) {
        int counter = 0;

        String querySelect = "SELECT id FROM gaussian2 ORDER BY id DESC";      // get number of rows in database for inserting in JSON
        //String query = "INSERT INTO `gaussian21` (`jdoc`) VALUES (JSON_OBJECT('id', ?, 'time', ?, 'document', ?));";
        String queryInsert = "INSERT INTO `gaussian2` (`id`, `jdoc`) VALUES (NULL, JSON_OBJECT('id', ?, 'time', ?, 'document', ?, 'resultcalc', ?));";

          try (Connection conn = DriverManager.getConnection(url, user, password);
               Statement statement = conn.createStatement();
               ResultSet rs = statement.executeQuery(querySelect);
               PreparedStatement psInsert = conn.prepareStatement(queryInsert)){

              if (rs.next()) {
                  counter = rs.getInt("id");
              }
              psInsert.setInt(1, counter + 1);
              psInsert.setTimestamp(2, new Timestamp((System.currentTimeMillis())));
              psInsert.setString(3, indocument);
              psInsert.setString(4, result);
              psInsert.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Object> getAll() {
        String queryString = "SELECT jdoc FROM gaussian2";
        List<Object> resultList = new ArrayList<Object>();

        ResultSetHandler<List<Object>> h = new ResultSetHandler<List<Object>>() {
            public List<Object> handle(ResultSet rs) throws SQLException {
                List<Object> resultList = new ArrayList<Object>();
                while (rs.next()){
                    resultList.add(rs.getString("jdoc"));
                }
                return resultList;
            }
        };

        QueryRunner run = new QueryRunner(getDataSource());
        try {
            resultList = run.query(queryString, h);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }


    public List<Object> getAll_clas() {
        String query = "SELECT jdoc FROM gaussian2";
        List<Object> resultList = new ArrayList<Object>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)){

            while (rs.next()){
                resultList.add(rs.getString("jdoc"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

//    public List<Object> getAll() {
//        String queryString = "SELECT jdoc FROM gaussian2";
//        List<Object> resultList = new ArrayList<Object>();
//
//        try (Connection conn = DriverManager.getConnection(url, user, password);){
//
//        new QueryRunner()
//                .query(conn, queryString, new ArrayListHandler())
//                .stream()
//                //.map(array -> new Schema(
//                //    (String) array[0],
//                //))
//                .forEach(System.out::println);
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return resultList;
//    }

    private DataSource getDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

}


