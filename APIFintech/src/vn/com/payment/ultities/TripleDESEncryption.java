package vn.com.payment.ultities;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;

public class TripleDESEncryption implements IEncryption {

	static final byte[] SALT = {
			(byte) 0x09, /*
							 * snip - randomly chosen but static salt
							 */ };
	static final int ITERATIONS = 11;

	public static String generateKey() {
		// Get a key generator for Triple DES (a.k.a DESede)
		KeyGenerator keygen;

		try {
			keygen = KeyGenerator.getInstance("DESede");
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
			DESedeKeySpec keyspec;
			try {
				keyspec = (DESedeKeySpec) keyfactory.getKeySpec(keygen.generateKey(), DESedeKeySpec.class);
				byte[] rawkey = keyspec.getKey();

				// return Base64Utils.base64Encode(rawkey);
				return bytesToHex(rawkey);
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		// Use it to generate a key
	}

	public static String generateHexKey() {
		// Get a key generator for Triple DES (a.k.a DESede)
		KeyGenerator keygen;

		try {
			keygen = KeyGenerator.getInstance("DESede");
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
			DESedeKeySpec keyspec;
			try {
				keyspec = (DESedeKeySpec) keyfactory.getKeySpec(keygen.generateKey(), DESedeKeySpec.class);
				byte[] rawkey = keyspec.getKey();

				return Commons.bytesToHex(rawkey);
				// return bytesToHex(rawkey);
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		// Use it to generate a key
	}

//	private static SecretKey getKey(String key) {
//		byte[] bKey = hexToBytes(key);
//		try {
//			DESedeKeySpec keyspec = new DESedeKeySpec(bKey);
//			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
//
//			SecretKey lclSK = keyFactory.generateSecret(keyspec);
//
//			return lclSK;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//
//	}
	
	private static SecretKey getKey(String key) {
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

	private static SecretKey getKey2(String key) {
		byte[] bKey = hexToBytes(key);
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

	private static SecretKey getKeyBase64String(String key) {
		BASE64Decoder decode = new BASE64Decoder();

		try {
			byte[] bKey = decode.decodeBuffer(key);
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

	/**
	 * Ham ma hoa, su dung decrypt1 de giai ma
	 * 
	 * @param key
	 *            hexString
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchProviderException
	 * @throws Exception
	 */

	/**
	 * Phuong thuc su dung trong ma hoa giai ma, giai ma su dung ham decrypt2 du
	 * lieu tra ve cac clients
	 * 
	 * @param key
	 *            base64String
	 * @param input
	 *            String
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchProviderException
	 * @throws Exception
	 */
	public static String encrypt2(String key, String input)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, NoSuchProviderException, Exception {

		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		// Cipher encipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		bytes = input.getBytes("UTF-8");
		sKey = getKeyBase64String(key);
		// Encrypt
		byte[] enc;
		encipher.init(Cipher.ENCRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		// return new String(enc);
		// return Base64.encodeBase64(enc);
		return bytesToHex(enc);
	}

	/**
	 * Ham giai ma
	 * 
	 * @param key
	 *            key dang Base64string
	 * @param cipher
	 *            chuoi~ hex
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchProviderException
	 * @throws IllegalArgumentException
	 */
	public static String decrypt2(String key, String cipher)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, IllegalArgumentException {
		// System.out.println("key: "+key);
		// System.out.println("cipher: "+cipher);
		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		// Cipher encipher = Cipher.getInstance("DESede/ECB/Nopadding", "BC");
		bytes = hexToBytes(cipher);
		sKey = getKeyBase64String(key);
		// System.out.println("Key value: "+key);
		// Encrypt
		byte[] enc = null;

		encipher.init(Cipher.DECRYPT_MODE, sKey);

		enc = encipher.doFinal(bytes);

		return new String(enc);
	}

	public static String decryptDataFromCoreCharging(String key, String cipher)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, IllegalArgumentException {
		// System.out.println("key: "+key);
		// System.out.println("cipher: "+cipher);
		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		// Cipher encipher = Cipher.getInstance("DESede/ECB/Nopadding", "BC");
		bytes = hexToBytes(cipher);
		sKey = getKey(key);
		// System.out.println("Key value: "+key);
		// Encrypt
		byte[] enc = null;

		encipher.init(Cipher.DECRYPT_MODE, sKey);

		enc = encipher.doFinal(bytes);

		return new String(enc);
	}

	// /**
	// * Phuong thuc su dung trong giai ma du lieu client gui len
	// * co ma hoa
	// * @param key
	// * @param cipher
	// * @return
	// * @throws NoSuchAlgorithmException
	// * @throws NoSuchPaddingException
	// * @throws UnsupportedEncodingException
	// * @throws InvalidKeyException
	// * @throws IllegalBlockSizeException
	// * @throws BadPaddingException
	// * @throws NoSuchProviderException
	// * @throws IllegalArgumentException
	// */
	// public static String decryptBase64String(String key, String cipher)
	// throws NoSuchAlgorithmException, NoSuchPaddingException,
	// UnsupportedEncodingException, InvalidKeyException,
	// IllegalBlockSizeException, BadPaddingException,
	// NoSuchProviderException, IllegalArgumentException {
	// // System.out.println("key: "+key);
	// // System.out.println("cipher: "+cipher);
	// byte[] bytes = null;
	// SecretKey sKey = null;
	// Security.addProvider(new BouncyCastleProvider());
	// Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
	// // Cipher encipher = Cipher.getInstance("DESede/ECB/Nopadding", "BC");
	// // bytes = Base64Utils.base64Decode(cipher);
	// bytes = hexToBytes(cipher);
	// sKey = getKeyBase64String(key);
	// // System.out.println("Key value: "+key);
	// // Encrypt
	// byte[] enc = null;
	//
	// encipher.init(Cipher.DECRYPT_MODE, sKey);
	//
	// enc = encipher.doFinal(bytes);
	//
	// // return bytesToHex(enc);
	// return new String(enc);
	//
	// }

	
	

//	public static ByteString decryptbytesting1(String key, ByteString cipher)
//			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
//			IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
//		byte[] bytes = null;
//		SecretKey sKey = null;
//		Security.addProvider(new BouncyCastleProvider());
//		// Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING",
//		// "BC");
//		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
//		bytes = cipher.toByteArray();
//		sKey = getKey2(key);
//		// Encrypt
//		byte[] enc;
//		encipher.init(Cipher.DECRYPT_MODE, sKey);
//		enc = encipher.doFinal(bytes);
//		return ByteString.copyFrom(enc);
//
//	}

	public static byte[] decryptbytes(String key, byte[] cipher)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		// Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING",
		// "BC");
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		sKey = getKey2(key);
		// Encrypt
		byte[] enc;
		encipher.init(Cipher.DECRYPT_MODE, sKey);
		enc = encipher.doFinal(cipher);
		return enc;

	}

//	public static ByteString encryptbytestring1(String key, ByteString input)
//			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
//			BadPaddingException, UnsupportedEncodingException, NoSuchProviderException, Exception {
//
//		byte[] bytes = null;
//		SecretKey sKey = null;
//		Security.addProvider(new BouncyCastleProvider());
//		// Cipher encipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
//		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
//		bytes = input.toByteArray();
//		sKey = getKey2(key);
//		// Encrypt
//		byte[] enc;
//		encipher.init(Cipher.ENCRYPT_MODE, sKey);
//		enc = encipher.doFinal(bytes);
//		return ByteString.copyFrom(enc);
//	}

	public static byte[] encryptbytes(String key, byte[] input)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, NoSuchProviderException, Exception {

		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		// Cipher encipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		bytes = input;
		sKey = getKey2(key);
		// Encrypt
		byte[] enc;
		encipher.init(Cipher.ENCRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		return enc;
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

	private static byte[] hexToBytes(final String hex) {
		final byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
		}
		return bytes;
	}
	
	public static String encryptNow(String key, String input)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, NoSuchProviderException, Exception {

		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		bytes = input.getBytes("UTF-8");
		sKey = getKey(key);
		byte[] enc;
		encipher.init(Cipher.ENCRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		return bytesToHex(enc);
	}
	public static String decryptNow(String key, String cipher)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		bytes = hexToBytes(cipher);
		sKey = getKey(key);
		byte[] enc;
		encipher.init(Cipher.DECRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		return new String(enc);

	}
	public static String encryptPayment(String key, String input)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, NoSuchProviderException, Exception {
		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5PADDING", "BC");
		bytes = input.getBytes("UTF-8");
		sKey = getKey(key);
		// Encrypt
		byte[] enc;
		encipher.init(Cipher.ENCRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		return bytesToHex(enc);
	}
	
	public static String decryptPayment(String key, String cipher)
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
	
	public static void main(String[] args) {
		String key = "A699C338AA366F2BD79FDD9F";
		String chuoi = "aaaaaaaaaaass_aaaaaaaa1111111";
		
		try {
			String chuoimahoa = encrypt2(key, chuoi);
			System.out.println("chuoimahoa: "+ chuoimahoa);
			String giaima = decrypt2(key, chuoimahoa);
			System.out.println("Chuoigiaima: "+ giaima);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}
