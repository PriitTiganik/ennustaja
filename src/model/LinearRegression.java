package model;

import java.util.ArrayList;
import java.util.List;


public class LinearRegression {
    public LinearRegression(){
        //
    }

    public static double[] calc_coefs(int id) {
        double[] coefs;
        Postgresql sql = new Postgresql();
        String query = "SELECT id, weight, height from height_weight where id !="+id+" ;";
        ArrayList<List> dataList = sql.select(query);
        int[][] data = ArrayListToArray(dataList); //data: kahe veeruga array, kus soltuv muutuja esimene
        System.out.println(data.length);
        coefs = regression(data);

        return coefs;
    }

    private static int[][] ArrayListToArray(ArrayList<List> dataList)  { //array list to array
        int[][] data = new int[dataList.size()][];
        for (int i = 0; i < data.length; i++) {
            int y = Integer.parseInt((String)dataList.get(i).get(1));
            int x = Integer.parseInt((String)dataList.get(i).get(2));
            data[i]= new int[]{y,x};
        }
        return data;
    }

    private static double[] regression(int[][] aa){ //
        int[][] products = new int[aa.length][3];//x, {x^2, y^2, xy}
        for (int i = 0; i < aa.length; i++) {
            products[i][0]= (aa[i][1])*(aa[i][1]);//x^2
            products[i][1]= (aa[i][0])*(aa[i][0]);//y^2
            products[i][2]= (aa[i][1])*(aa[i][0]);//xy
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


