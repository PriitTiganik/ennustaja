package sql;

/**
 * Created by priit on 07-Nov-15.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//http://docs.oracle.com/javase/tutorial/java/javaOO/classvars.html
//https://jdbc.postgresql.org/download.html alustuseks tomba alla draiver ja pane see paketi sisse. parem klikk ja make library vms
//http://www.postgresql.org/docs/7.4/static/jdbc.html

//https://www.youtube.com/watch?v=32IgeS5xaxM

public class Postgresql {
    static Connection c = null;
    public Postgresql(){
        //connect(); //pole vaja midagi teha
    }

    public static ArrayList<List> select(String query) {
        connect();
        Statement stmt = null;
        for (int i = 0; i < 2; i++) {
            try {
                c.setAutoCommit(false);
                stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); //et mitu korda rs extiga labi kaia peab olema scrollable
                ResultSet rs = stmt.executeQuery(query);

                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsCount = rsmd.getColumnCount();
                ArrayList<List> dataRows = new ArrayList(); //http://stackoverflow.com/questions/26154120/how-to-get-value-from-2d-arraylist
                while (rs.next()) {
                    ArrayList dataRow = new ArrayList();
                    for (int j = 1; j < columnsCount+1; j++) {
                        dataRow.add(rs.getString(j));
                    }
                    dataRows.add(dataRow);
                }
                rs.close();
                stmt.close();
                c.close();
                return dataRows;

            } catch (Exception e) { //kui ei ole veel yhendust avatud, siis avab. Kui ei onnestu, siis error ja exit
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.out.println("Trying to connect");
                connect();
                if (i == 1){System.exit(0);}
            }
            System.out.println("Operation done successfully");
        }
        return null;
    }

    public static void execute_query(String query) {
        Statement stmt = null;
        connect();
            try {
                c.setAutoCommit(false);
                stmt = c.createStatement();
                stmt.executeUpdate(query);
                stmt.close();
                c.commit();
                c.close();
            } catch (Exception e) { //kui ei ole veel yhendust avatud, siis avab. Kui ei onnestu, siis error ja exit
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Operation done successfully");

    }

    private static void insert() { //pole kasutatud
        connect();
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

    public static void connect() {
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