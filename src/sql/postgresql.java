package sql;

/**
 * Created by priit on 07-Nov-15.
 */

import java.sql.*;

//http://docs.oracle.com/javase/tutorial/java/javaOO/classvars.html
//https://jdbc.postgresql.org/download.html alustuseks tomba alla draiver ja pane see paketi sisse. parem klikk ja make library vms
//http://www.postgresql.org/docs/7.4/static/jdbc.html

//https://www.youtube.com/watch?v=32IgeS5xaxM

public class postgresql {
    static Connection c = null;
    public static void main(String args[]) {

        connect();
        //insert();
        //delete();
        select();

    }

    private static void select()  {

        Statement stmt = null;
        try {
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * from height_weight;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int weight  = rs.getInt("weight");
                int height  = rs.getInt("height");
                System.out.println("name = "+ name);
                System.out.println( "weight = " + weight );
                System.out.println( "height = " + height );
                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    private static void delete() {
    }

    private static void insert() {
        Statement stmt = null;
        try {

            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    private static void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "java", "java");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

}