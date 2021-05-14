package vn.com.payment.ultities;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TripleDES {

	public static String encryptPaynment(String plainText, String key) {
		try {
			byte[] arrayBytes = getValidKey(key);
			KeySpec ks = new DESedeKeySpec(arrayBytes);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			SecretKey seckey = skf.generateSecret(ks);
			//
			cipher.init(Cipher.ENCRYPT_MODE, seckey);
			byte[] plainByte = plainText.getBytes("UTF8");
			byte[] encryptedByte = cipher.doFinal(plainByte);
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(encryptedByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encrypt1(String key, String input)
			throws  Exception {

		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		// Cipher encipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		bytes = input.getBytes("UTF-8");
		sKey = getKey(key);
		// Encrypt
		byte[] enc;
		encipher.init(Cipher.ENCRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		return bytesToHex(enc);
	}
	
	private static SecretKey getKey(String key) {
		//	byte[] bKey = hexToBytes(key);
			byte[] bKey = key.getBytes();
			try {
				DESedeKeySpec keyspec = new DESedeKeySpec(bKey);
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");

				SecretKey lclSK = keyFactory.generateSecret(keyspec);

				return lclSK;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}
	
	private static String bytesToHex(final byte[] bytes) {
		final StringBuilder buf = new StringBuilder(bytes.length * 2);
		for (final byte b : bytes) {
			final String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				buf.append("0");
			}
			buf.append(hex);
		}
		return buf.toString();
	}
	

	public static String decryptPaynment(String encryptData, String key) {
		try {
			byte[] arrayBytes = getValidKey(key);
			KeySpec ks = new DESedeKeySpec(arrayBytes);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			SecretKey seckey = skf.generateSecret(ks);
			//
			cipher.init(Cipher.DECRYPT_MODE, seckey);
			BASE64Decoder decode = new BASE64Decoder();
			byte[] encryptByte = decode.decodeBuffer(encryptData);
			byte[] plainByte = cipher.doFinal(encryptByte);
			return new String(plainByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static byte[] getValidKey(String key) {
		try {
			String sTemp = MD5.hash(key).substring(0,24);
			return sTemp.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getEncodeHMACSHA256(String data, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] bytes = sha256_HMAC.doFinal(data.getBytes());
        String hash = new String(Base64.getEncoder().encode(bytes));
        return hash;
    }
	
	public static String encrypt(String strClearText,String strKey) throws Exception{
	    String strData="";

	    try {
	        SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
	        Cipher cipher=Cipher.getInstance("Blowfish");
	        cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
	        byte[] encrypted=cipher.doFinal(strClearText.getBytes());
	        strData=new String(encrypted);

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception(e);
	    }
	    return strData;
	}
	
	public static String decrypt(String strEncrypted,String strKey) throws Exception{
	    String strData="";

	    try {
	        SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
	        Cipher cipher=Cipher.getInstance("Blowfish");
	        cipher.init(Cipher.DECRYPT_MODE, skeyspec);
	        byte[] decrypted=cipher.doFinal(strEncrypted.getBytes());
	        strData=new String(decrypted);

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception(e);
	    }
	    return strData;
	}
	public static String encrypt55(String strClearText,String strKey){
	    String strData="";

	    try {
	        SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
	        Cipher cipher=Cipher.getInstance("Blowfish");
	        cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
	        byte[] encrypted=cipher.doFinal(strClearText.getBytes());
	        strData=new String(encrypted);

	    } catch (Exception e) {
	        e.printStackTrace();	       
	    }
	    return strData;
	}
	public static String decrypt55(String strEncrypted,String strKey){
	    String strData="";

	    try {
	        SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
	        Cipher cipher=Cipher.getInstance("Blowfish");
	        cipher.init(Cipher.DECRYPT_MODE, skeyspec);
	        byte[] decrypted=cipher.doFinal(strEncrypted.getBytes());
	        strData=new String(decrypted);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return strData;
	}
	
	public static String encrypt11(String key, String input)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, NoSuchProviderException, Exception {

		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		// Cipher encipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		bytes = input.getBytes("UTF-8");
		sKey = getKey(key);
		// Encrypt
		byte[] enc;
		encipher.init(Cipher.ENCRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		return bytesToHex(enc);
	}
	
	public static String decrypt11(String key, String cipher)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		// Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING",
		// "BC");
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		bytes = hexToBytes(cipher);
		sKey = getKey(key);
		// Encrypt
		byte[] enc;
		encipher.init(Cipher.DECRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		return new String(enc);

	}

	private static byte[] hexToBytes(final String hex) {
		final byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
		}
		return bytes;
	}

	
	public static void main(String args[]) throws Exception {
		//TripleDes cho độ dài mã hóa 24 ký tự
		String key = "5CDB37A77918CEEDD89551B129E3276660BBAE2F0B95B2D671478B6492C2CE6C";
		String a = "yQ/N3ntKC40cDV9Q0ha4J5b3X77Ws0tcE616aZJhy+E\u003d";
//		String decrypt1 = decryptPaynment(a, key);
//		System.out.println("Decrypt: " + decrypt1);
		String plainText = "99209_20190620013803_IwR3u5kTGOK823gw";
		
//		System.out.println("getEncodeHMACSHA256: "+ getEncodeHMACSHA256(plainText));
		
		String encrypt =  encrypt55(key, plainText);
//		encrypt = "ncj0WqE0spNjofnBl9ZDv2IIZgfhccTUmw3dxgHyp1+wX/x+cFkRlIAey1OKGSvpHi1crfSN89Wsp9etENGYajFUJpCHiwNLqfc6SJlcnQo=";
		String decrypt = decrypt55(key, encrypt);
		System.out.println("Encrypt: " + encrypt);
		System.out.println("Decrypt: " + decrypt);
		//kcTbBqO3JdeoF8owLCz6gw==
		//kcTbBqO3JdeoF8owLCz6gw==
	}

}