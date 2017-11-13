package com.isolation.portalhelper;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encryption and hash functions
 */
public class CryptoHelper {
	
	/**
	 * Hashes input with MD5 and encodes bytes in Base64
	 * 
	 * @param inputString
	 * @return
	 */
	private static String b64MD5(String inputString) {
		try{
		    MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(inputString.getBytes());
	
		    byte[] digest = md.digest();

			System.out.println(digest);

		    String b64 = Base64.encodeToString(digest, Base64.DEFAULT);
		    return b64.substring(0, b64.length() - 2);
		}
		catch(NoSuchAlgorithmException e){
		}
		
		return null;
	}
	
	/**
	 * Encrypts data with key using HMAC-MD5
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	private static String hmacMD5(String key, String data) {
		try{
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacMD5");
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(signingKey);
			return toHexString(mac.doFinal(data.getBytes()));
		}
		catch(NoSuchAlgorithmException e){
		}
		catch (InvalidKeyException e) {
		}
		
		return null;
	}
	private static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();
		
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}

		return formatter.toString();
	}
	
	// ===== Passwords =====
	
	public static String calcPW(String pskey, String pass){
		return hmacMD5(pskey, b64MD5(pass));
	}
	public static String calcDBPW(String pskey, String pass){
		return hmacMD5(pskey, pass.toLowerCase());
	}
	
	public static void main(String[] args){
		String pskey = "20F7180207F044B65ED27A1830830C5652374BD557B27A764447242F38586328";
		String pass = "larry";
		
		System.out.println("dbpw:	" + calcDBPW(pskey, pass));
		System.out.println("pw:	" + calcPW(pskey, pass));
	}
}
