package model;

public class helpers {

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }


    public static int roundUpDown(int number, int by){
        int result=number;
        if(by>0){
            result =number+(by-number%by);
        }else if(by <0){
            if(number%by==0){
                number--;
                result = number-number%by;
            }else {
                result =number-number%by;
            }
        }else {
            return result;
        }
        return result;
    }

}
