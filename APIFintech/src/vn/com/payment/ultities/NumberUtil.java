package vn.com.payment.ultities;

public class NumberUtil {

    private static char[] numbers = {'0', '1','2','3','4','5','6','7','8','9'};

    public static boolean checkIsNotNumber(char x){
        boolean isNotNumber = true;
        for (char number:numbers) {
            if (x == number){
                isNotNumber = false;
            }
        }
        return isNotNumber;
    }

    public static String removeSpecialChar(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            if (checkIsNotNumber(sb.charAt(i))){
                sb.setCharAt(i, ' ');
            }
        }
        return sb.toString();
    }
}
