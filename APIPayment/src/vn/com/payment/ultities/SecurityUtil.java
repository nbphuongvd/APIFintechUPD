package vn.com.payment.ultities;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class SecurityUtil {
  public static String KEY = "14249605-99c5-4df7-af25-e5a2cfbd0298"; // Key
  // for gen account & password

  public static String KEY_SERIAL = "3190a60e-7315-464d-a185-60db1786184f"; // for

  // ma
  // hoa
  // card

  public static String encrypt(String key, String data) throws Exception {
    if (StringUtils.isEmpty(key))
      key = KEY;
    Cipher cipher = Cipher.getInstance("TripleDES");
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(key.getBytes(), 0, key.length());
    String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(0, 24);
    SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");
    cipher.init(Cipher.ENCRYPT_MODE, keyspec);
    byte[] stringBytes = data.getBytes();
    byte[] raw = cipher.doFinal(stringBytes);
    BASE64Encoder encoder = new BASE64Encoder();
    String base64 = encoder.encode(raw);
    return base64;
  }

  public static String decrypt(String key, String data) throws Exception {
    if (StringUtils.isEmpty(key))
      key = KEY;
    Cipher cipher = Cipher.getInstance("TripleDES");
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(key.getBytes(), 0, key.length());
    String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(0, 24);
    SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");
    cipher.init(Cipher.DECRYPT_MODE, keyspec);
    BASE64Decoder decoder = new BASE64Decoder();
    byte[] raw = decoder.decodeBuffer(data);
    byte[] stringBytes = cipher.doFinal(raw);
    String result = new String(stringBytes);
    return result;
  }

  public static String createSign(String data, String filePath) {
    try {
      final File privKeyFile = new File(filePath);
      final byte[] privKeyBytes = readFile(privKeyFile);
      final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      final PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privKeyBytes);
      final PrivateKey pk = (PrivateKey) keyFactory.generatePrivate(privSpec);

      final Signature sg = Signature.getInstance("SHA1withRSA");

      sg.initSign(pk);
      sg.update(data.getBytes());
      final byte[] bDS = sg.sign();
      return new String(org.apache.commons.codec.binary.Base64.encodeBase64(bDS));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return "";
  }

  public static boolean checkSign(String sign, String data, String publicKeyFile) {
    try {
      File pubKeyFile = new File(publicKeyFile);
      byte[] pubKeyBytes = readFile(pubKeyFile);
      X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubKeyBytes);

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PublicKey k = (RSAPublicKey) keyFactory.generatePublic(pubSpec);

      Signature signature = Signature.getInstance("SHA1withRSA");
      signature.initVerify(k);
      signature.update(data.getBytes());

      return signature.verify(org.apache.commons.codec.binary.Base64.decodeBase64(sign.getBytes()));

    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println(ex.getMessage());
    }

    return false;
  }

  public static boolean checkSignXml(String sign, String data, String publicKey) {
    try {
      byte[] expBytes = Base64.decodeBase64(getXmlValue(publicKey, "Exponent"));
      byte[] modBytes = Base64.decodeBase64(getXmlValue(publicKey, "Modulus"));

      BigInteger modules = new BigInteger(1, modBytes);
      BigInteger exponent = new BigInteger(1, expBytes);
      RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(modules, exponent);

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PublicKey k = (RSAPublicKey) keyFactory.generatePublic(pubSpec);

      Signature signature = Signature.getInstance("SHA1withRSA");
      signature.initVerify(k);
      signature.update(data.getBytes());

      return signature.verify(org.apache.commons.codec.binary.Base64.decodeBase64(sign.getBytes()));

    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println(ex.getMessage());
    }

    return false;
  }

  public static String getXmlValue(String xml, String tagName) {
    String openTag = "<" + tagName + ">";
    String closeTag = "</" + tagName + ">";
    int f = xml.indexOf(openTag) + openTag.length();
    int l = xml.indexOf(closeTag);
    return (f > l) ? "" : xml.substring(f, l);
  }

  public static byte[] readFile(final File file) throws FileNotFoundException, IOException {
    DataInputStream dis = null;
    try {
      dis = new DataInputStream(new FileInputStream(file));
      final byte[] data = new byte[(int) file.length()];
      dis.readFully(data);
      return data;
    } finally {
      if (dis != null) {
        dis.close();
      }
    }
  }

  public static void main(String[] a) throws Exception {
	  	//String path = "E:\\file_key\\" + "PRIVATE_KEY" + ".pem";
//	  	String path = "E:\\file_key\\" + "360VIETNAM" + ".pem";
//	  	String adđ = "3254657fsagfdshfdhfasdfsadgds";
//		String sign = createSign(adđ, path);
//		System.out.println("sign = " +sign);
  }
}
