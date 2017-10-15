package GradDataWarehousing.HW4;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

import static Utils.ConsolePrinting.print;


/**
 * This class is used to store the hard-coded values that the database (db) needs to know for connection utilities.
 */
public class MyMysqlDataSource {

    private static ResultSet result_set = null;
    private static Connection connection = null;
    private static Statement statement = null;

    private static final String DB = "seedor87";
    private static final String SERVER = "elvis.rowan.edu";
    private static final String USER = "seedor87";
    private static final String PASSWORD = "penguin";
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

    public static void insert(Object... params) {
        try {
            String query = String.format("insert into Transactions values (?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                preparedStmt.setString(i, params[i-1].toString());
            }
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static void printResSet(ResultSet set) {
        try {
            ResultSetMetaData metadata = set.getMetaData();
            int numberOfColumns = metadata.getColumnCount();
            while (set.next()) {
                int i = 1;
                while(i <= numberOfColumns) {
                    print(set.getString(i++), "");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new MyMysqlDataSource();

        try {
            insert("20170101", "1", "1", "000000000", "101.10", "69", "777");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String query = String.format("select * from Transactions");
            result_set = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printResSet(result_set);
    }
}
