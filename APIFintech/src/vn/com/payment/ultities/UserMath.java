package vn.com.payment.ultities;

public class UserMath{
	public static int roundUp(float x)
	{
		int rtValue;
		rtValue = Math.round(x);
		if (x > rtValue)
			return rtValue + 1;
		else return rtValue;
	}

}
