package regression;

import sql.Postgresql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Priit on 31.10.2015.
 */
public class LinearRegression {



    public LinearRegression(){
        //pole vaja midagi teha
    }


    public static double[] calc_coefs(int id) {
        double[] coefs;
        Postgresql sql = new Postgresql();
        String query = "SELECT id, weight, height from height_weight where id !="+id+" ;";
        ArrayList<List> dataList = sql.select(query);
        //System.out.println((dataList.get(1).get(1)));
        int[][] data = insertData(dataList); //kahe veeruga array, kus soltuv muutuja esimene
        System.out.println(data.length);
        coefs = regression(data);

        return coefs;
    }

    private static int[][] insertData(ArrayList<List> dataList)  {
        int[][] data = new int[dataList.size()][];
        for (int i = 0; i < data.length; i++) {
            int y = Integer.parseInt((String)dataList.get(i).get(1));
            int x = Integer.parseInt((String)dataList.get(i).get(2));
            data[i]= new int[]{y,x};
        }
        return data;
    }

    private static double[] test() { //for testing
        int[][] data = insertSampleData();
        double[] coefs = regression(data);
        return coefs;
    }

    private static int[][] insertSampleData() {
        int[][] aa = new int[6][];

        aa[0]=new int[]{99, 43};
        aa[1]=new int[]{65, 21};
        aa[2]=new int[]{79, 25};
        aa[3]=new int[]{75, 42};
        aa[4]=new int[]{87, 57};
        aa[5]=new int[]{81, 59};
        return aa;
    }
    private static double[] regression(int[][] aa){
        int[][] products = new int[aa.length][3];//x, {x^2, y^2, xy}
        for (int i = 0; i < aa.length; i++) {
            products[i][0]= (aa[i][1])*(aa[i][1]);
            products[i][1]= (aa[i][0])*(aa[i][0]);
            products[i][2]= (aa[i][1])*(aa[i][0]);
        }

        int sumY =sumCol(aa, 0);
        int sumX =sumCol(aa, 1);
        int sumXX =sumCol(products, 0);
        int sumYY =sumCol(products, 1);
        int sumXY =sumCol(products, 2);

        double aCoef = (double)(sumY*sumXX-sumX*sumXY)/(aa.length*sumXX-sumX*sumX);
        double bCoef = (double)(aa.length*sumXY-sumX*sumY)/(aa.length*sumXX-sumX*sumX);

        double[] result =new double[]{aCoef,bCoef};
        return result;
    }
    private static int sumCol(int[][] data, int n){ //data[][] array with the nth col to sum
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum = sum + data[i][n];
        }
        return sum;
    }

}


