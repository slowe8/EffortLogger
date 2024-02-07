package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;

// Authored by Sean Lowe

public class SecretKeyStore {

	private static String path = "keystore.jks";
	private static char[] password = "password".toCharArray();
	
	public static void store(String username, SecretKey keyToStore) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		//File file = new File(path);
		createKeyStore(username);
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream fis = new FileInputStream(username + path);
		/*if(file.exists()) {
			keyStore.load(new FileInputStream(file), password);
		} else {
			keyStore.load(null, null);
			keyStore.store(new FileOutputStream(file), password);
		}*/
		keyStore.load(fis, password);
		ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);
		SecretKeyEntry secretKeyEntry = new SecretKeyEntry(keyToStore);
		keyStore.setEntry("key", secretKeyEntry, protectionParam);
		FileOutputStream fos = new FileOutputStream(username + path);
		keyStore.store(fos, password);
	}
	
	public static SecretKey load(String username) {
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream readStream = new FileInputStream(username + path);
			keyStore.load(readStream, password);
			SecretKey key = (SecretKey) keyStore.getKey("key", password);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void createKeyStore(String username) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(null, password);
		try(FileOutputStream fos = new FileOutputStream(username + path)) {
			keyStore.store(fos, password);
		}
	}
}
