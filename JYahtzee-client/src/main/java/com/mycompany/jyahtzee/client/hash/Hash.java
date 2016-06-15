package com.mycompany.jyahtzee.client.hash;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : Cette classe gère les création des hashs des mots de passes
 */
public class Hash {
    private String message;
	
    /**
     * Cette méthode permet de creer un hash sha-256
     * @param message le message a hasher
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public String createHash(String message) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] result = digest.digest(message.getBytes());
        StringBuffer hash = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            hash.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return hash.toString();
    }
	
 
    /**
     * Cette méthode permet de creer un hash sha256 a partir d'un message et de le le comparet
     * a un auntre message deja haché
     * @param messageHash le message déja haché
     * @param message le message a tester
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public boolean testHash(String messageHash, String message) throws NoSuchAlgorithmException{
            return messageHash.equals(createHash(message));
    }

}
