package vn.com.payment.ultities;
 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class EmailFormatValidator {
    private Pattern pattern;
    private Matcher matcher;
 
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
    public EmailFormatValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
 
    public boolean validate(final String email) { 
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static void main(String[] args) {
		EmailFormatValidator valid = new EmailFormatValidator();
		System.out.println(valid.validate("@vietdavnptepay.com.vn.xx"));
	}
}