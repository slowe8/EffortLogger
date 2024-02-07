//Taehoon Kwon and Sean Lowe
//Encrypts and Decrypts strings using AES with the help
//of the java cryptography package

package application;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Encryptor {

    //Advanced Encryption Standard
    //128 bit
    byte[] IV = { 0x01, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
    
    private final static String algorithm = "AES/CBC/PKCS5Padding";
    
    private static final SecureRandom Random = new SecureRandom();
    
    public static String getNextSalt() {
        byte[] salt = new byte[16];
        Random.nextBytes(salt);
//        return salt;
        return Base64.getEncoder().encodeToString(salt);

    }
    
    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }
    
    public static SecretKey getKeyFromPassword(String password, String salt) 
    		throws NoSuchAlgorithmException, InvalidKeySpecException {
    	
    	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    	KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
    	SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    	return secret;
    }
    
    public static byte[] generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
        //return new IvParameterSpec(iv);
    }
    
    public static String encrypt(String input, SecretKey key, IvParameterSpec iv) 
    		throws NoSuchPaddingException, NoSuchAlgorithmException, 
    		InvalidAlgorithmParameterException, InvalidKeyException,
    	    BadPaddingException, IllegalBlockSizeException {
    	    
    	    Cipher cipher = Cipher.getInstance(algorithm);
    	    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    	    byte[] cipherText = cipher.doFinal(input.getBytes());
    	    return Base64.getEncoder()
    	        .encodeToString(cipherText);
   	}
    
    //Encrypts a String parameter using an array as the secret key
    public String hoon_encrypt(String input, byte[] secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
    	/*
    	 * This encrypt function encrypts data using
    	 * 1. a Cipher object which takes an array of bytes and a series of other parameters in order to 
    	 * create a secure encryption
    	 * 2. a base64 encoder which converts the byte array encryption into a base64 string that can be
    	 * written to file (or a database in later prototypes
    	 */
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"algorithm/mode/padding"
        SecretKeySpec key = new SecretKeySpec(secretKey,"AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText); //Returns encrypted String
    }
    
    public static String decrypt(String cipherText, SecretKey key,
    	    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
    	    InvalidAlgorithmParameterException, InvalidKeyException,
    	    BadPaddingException, IllegalBlockSizeException {
    	    
    	try {
    		Cipher cipher = Cipher.getInstance(algorithm);
        	cipher.init(Cipher.DECRYPT_MODE, key, iv);
        	byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        	return new String(plainText);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return ""; 
    }

    //Decrypts a String parameter using an array as the secret key
    public String hoon_decrypt(String cipherText, byte[] secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
    	/*
    	 * This decrypt function decrypts data using
    	 * 1. a base64 decoder that returns a byte array of the cipherText 
    	 * 2. a Cipher object which takes the cipher bytes and decrypts them into their original form using the 
    	 * original key
    	 */
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"algorithm/mode/padding"
        SecretKeySpec key = new SecretKeySpec(secretKey,"AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText); //Returns String as regular text
    }
}
