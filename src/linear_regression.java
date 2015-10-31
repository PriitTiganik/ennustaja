package regressioon;

import java.util.Arrays;

/**
 * Created by Miku on 31.10.2015.
 */
public class regress {
    public static void main(String[] args) {
        int[][] data = insertData();
        double[] coefs = regression(data);

        System.out.println(coefs[0]+","+coefs[1]);
    }

    private static int[][] insertData() {
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


