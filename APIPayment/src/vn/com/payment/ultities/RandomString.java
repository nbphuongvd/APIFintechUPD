package vn.com.payment.ultities;
import java.util.Random;



public class RandomString {
	
  private static final char[] symbols = new char[36];

  static {
    for (int idx = 0; idx < 10; ++idx)
      symbols[idx] = (char) ('0' + idx);
    for (int idx = 10; idx < 36; ++idx)
      symbols[idx] = (char) ('a' + idx - 10);
  }

  private final Random random = new Random();

  private final char[] buf;

  public RandomString(int length) {
    if (length < 1)
      throw new IllegalArgumentException("length < 1: " + length);
    buf = new char[length];
  }

  public String nextString() {
    for (int idx = 0; idx < buf.length; ++idx) 
      buf[idx] = symbols[random.nextInt(symbols.length)];
    return new String(buf);
  }
  public static String sub84(String input){
	  String temp ="";
		if(input.substring(0, 2).equals("84")){
			temp = "0"+ input.substring(2);
			return temp;
		}else
			return input;
	  
  }
  
  public static void main(String[] args) {
	  String test ="090789001";
	  System.out.println(sub84(test));
}

}