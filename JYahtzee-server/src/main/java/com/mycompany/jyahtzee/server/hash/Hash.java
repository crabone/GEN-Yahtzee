package com.mycompany.jyahtzee.server.hash;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	private String message;
	
	public String createHash(String message) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] result = digest.digest(message.getBytes());
        StringBuffer hash = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            hash.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return hash.toString();
	}
	
	public boolean testHash(String messageHash, String message) throws NoSuchAlgorithmException{
		return messageHash.equals(createHash(message));
	}
	
	public static void main(String... args) throws NoSuchAlgorithmException{
		Hash a = new Hash();
		String msg = "V12zr!";
		String hash = a.createHash(msg);
		System.out.println(hash);
		System.out.println(hash.length());
	}
}
