package vn.com.payment.ultities;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
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
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class TripleDesEnDe {
	public static String encrypt(String key, String input)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, NoSuchProviderException {
		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5Padding", "BC");
		bytes = input.getBytes("UTF-8");
		sKey = getKey(key);
		// Encrypt
		byte[] enc;
		encipher.init(Cipher.ENCRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		return Base64Utils.base64Encode(enc);
	}

	public static String decrypt(String key, String cipher)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		byte[] bytes = null;
		SecretKey sKey = null;
		Security.addProvider(new BouncyCastleProvider());
		Cipher encipher = Cipher.getInstance("DESede/ECB/PKCS5Padding", "BC");
		bytes = Base64.decode(cipher);
		sKey = getKey(key);
		byte[] enc;
		encipher.init(Cipher.DECRYPT_MODE, sKey);
		enc = encipher.doFinal(bytes);
		return new String(enc);
	}

	private static SecretKey getKey(String key) {
		key = key.substring(0, 24);
		byte[] bKey = key.getBytes();
		try {
			DESedeKeySpec keyspec = new DESedeKeySpec(bKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey lclSK = keyFactory.generateSecret(keyspec);
			return lclSK;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
