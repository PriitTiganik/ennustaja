package sql;

/**
 * Created by priit on 07-Nov-15.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        //select_example();
        //select();

    }


    public static ArrayList<List> select(String query) {
        Statement stmt = null;
        for (int i = 0; i < 2; i++) {
            try {
                c.setAutoCommit(false);
                stmt = c.createStatement();
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
                //System.out.println((a));
                /*int[][] resultTable = new int[a.size()][];
                int j = 0;
                rs.beforeFirst();//scrollib rs algusesse
                while (rs.next()) {
                    resultTable[j] = new int[]{rs.getInt("id"), rs.getInt("height"), rs.getInt("weight")};
                    j++;
                }*/
                rs.close();
                stmt.close();
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

    private static void select_example()  {

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