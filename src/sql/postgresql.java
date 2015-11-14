package sql;

/**
 * Created by priit on 07-Nov-15.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

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
        select();

    }


    public static int[][] select() {

        connect();

        Statement stmt = null;

            try {
                c.setAutoCommit(false);

                stmt = c.createStatement();
                stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery( "SELECT id, weight, height from height_weight;" );

                ArrayList a = new ArrayList();
                while ( rs.next() ) {
                    a.add(new int[]{rs.getInt("id"),rs.getInt("height"),rs.getInt("weight")});
                }
                System.out.println((a));
                int[][] resultTable = new int[a.size()][];
                int i =0;
                rs.beforeFirst();//scrollib rs algusesse
                while ( rs.next() ) {
                    resultTable[i] =new int[]{rs.getInt("id"),rs.getInt("height"),rs.getInt("weight")};

                   // resultTable[i][0]=rs.getInt("id");
                    //resultTable[i][1]=rs.getInt("height");
                    //resultTable[i][2]=rs.getInt("weight");

                    i++;
                }
                //System.out.println(Arrays.toString(resultTable));
                //System.out.println(resultTable[1]);
                rs.close();
                stmt.close();
                return resultTable;

            } catch ( Exception e ) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
            System.out.println("Operation done successfully");
        return new int[0][];
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