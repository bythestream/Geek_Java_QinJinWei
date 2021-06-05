import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class JDBCSample {
   static final String DB_URL = "jdbc:mysql://localhost/student";
   static final String USER = "guest";
   static final String PASS = "guest123";
   private static DataSource datasource;

   public static DataSource getDataSource() {
      if (datasource == null) {
         HikariConfig config = new HikariConfig();

         config.setJdbcUrl(DB_URL);
         config.setUsername(USER);
         config.setPassword(PASS);

         config.setMaximumPoolSize(10);
         config.setAutoCommit(false);
         config.addDataSourceProperty("cachePrepStmts", "true");
         config.addDataSourceProperty("prepStmtCacheSize", "250");
         config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

         datasource = new HikariDataSource(config);
      }
      return datasource;
   }

   public static void main(String[] args) {
      final String QUERY = "SELECT id, first, last, age FROM student";

      // Open a connection
      try

      {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         Connection conn = java.sql.DriverManager.getConnection(DB_URL, USER, PASS);
         java.sql.Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY);
         // Extract data from result set
         while (rs.next()) {
            // Retrieve by column name
            System.out.print("ID: " + rs.getInt("id"));
            System.out.print(", Age: " + rs.getInt("age"));
            System.out.print(", First: " + rs.getString("first"));
            System.out.println(", Last: " + rs.getString("last"));
         }

         String sql = "update student set First=?,Last=? where ID=?";
         PreparedStatement preparedStatement = conn.prepareStatement(sql);
         preparedStatement.setString(1, "Gary");
         preparedStatement.setString(2, "Larson");
         preparedStatement.setLong(3, 123);
         int rowsAffected = preparedStatement.executeUpdate();
      } catch (SQLException | ClassNotFoundException e) {
         e.printStackTrace();
      }

      Connection conn_hikari = null;
      PreparedStatement pstmt = null;
      ResultSet resultSet = null;
      try {
         DataSource dataSource = JDBCSample.getDataSource();
         conn_hikari = dataSource.getConnection();
         pstmt = conn_hikari.prepareStatement("SELECT * FROM student");

         System.out.println("The Connection Object is of Class: " + conn_hikari.getClass());

         resultSet = pstmt.executeQuery();
         while (resultSet.next()) {
            System.out
            .println(resultSet.getString(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
         }

      } catch (Exception e) {
         try {
            conn_hikari.rollback();
         } catch (SQLException e1) {
            e1.printStackTrace();
         }
         e.printStackTrace();
      }
   }
}
