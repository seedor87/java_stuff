package GradDataWarehousing.HW4;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import static Utils.ConsolePrinting.*;


/**
 * This class is used to store the hard-coded values that the database (db) needs to know for connection utilities.
 */
public class MyMysqlDataSource {

    public static String INPUT_FILE = "C:\\Users\\rseedorf\\IdeaProjects\\java_stuff\\output3.txt";
    private static ResultSet result_set = null;
    private static Connection connection = null;
    private static Statement statement = null;

//    private static final String DB = "seedor87";
//    private static final String SERVER = "elvis.rowan.edu";
//    private static final String USER = "seedor87";
//    private static final String PASSWORD = "penguin";
    private static com.mysql.jdbc.jdbc2.optional.MysqlDataSource DATA_SOURCE;

    /**
     * static method that returns one DataSource instance that encapsulates the connection to the db.
     *
     * @return - DataSource the
     */
    public static com.mysql.jdbc.jdbc2.optional.MysqlDataSource getMySQLDataSource() {
        com.mysql.jdbc.jdbc2.optional.MysqlDataSource mysqlDS = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
        mysqlDS.setServerName(SERVER);
        mysqlDS.setUser(USER);
        mysqlDS.setPassword(PASSWORD);
        mysqlDS.setDatabaseName(DB);
        return mysqlDS;
    }

    public MyMysqlDataSource() {
        DATA_SOURCE = getMySQLDataSource();
        try {
            connection = DATA_SOURCE.getConnection();
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert(Object... params) throws SQLException{
        String query = String.format("insert into Transactions values (?, ?, ?, ?, ?, ?, ?)");
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        for (int i = 1; i <= params.length; i++) {
            preparedStmt.setString(i, params[i-1].toString());
        }
        preparedStmt.execute();
    }

    public static void insert(String date_of_purchase,
                                 String customer_number,
                                 String item_number,
                                 String SKU,
                                 String price,
                                 String items_left,
                                 String total_cases_ordered) throws SQLException{

        String query = String.format("insert into Transactions values (?, ?, ?, ?, ?, ?, ?)");

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, date_of_purchase);
        preparedStmt.setString(2, customer_number);
        preparedStmt.setString(3, item_number);
        preparedStmt.setString(4, SKU);
        preparedStmt.setString(5, price);
        preparedStmt.setString(6, items_left);
        preparedStmt.setString(7, total_cases_ordered);
        preparedStmt.execute();
    }

    public static void printResSet(ResultSet set) throws SQLException {
        ResultSetMetaData metadata = set.getMetaData();
        int numberOfColumns = metadata.getColumnCount();
        while (set.next()) {
            int i = 1;
            while(i <= numberOfColumns) {
                print(set.getString(i++), "");
            }
        }
    }

    public static void demo() {
        try {
            insert("20170101", "1", "1", "000000000", "101.10", "69", "777");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String query = String.format("select * from Transactions");
            result_set = statement.executeQuery(query);
            printResSet(result_set);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void execute() {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(INPUT_FILE));
            line = br.readLine();   // throw away header
            println(line);
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] fields = line.split(cvsSplitBy);
                insert(fields);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String args[]) {
        new MyMysqlDataSource();
        execute();
    }
}
