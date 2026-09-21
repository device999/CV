//package com.org.phase.multithreads.chatGPT;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;

public class FileCrypto {

    // Your private key/password
    private static final String SECRET = "check_telegram_with_me";
    
    private static final String plain = "/home/orbisu/projects/java-senior/Phase-1/src/main/java/com/org/phase/multithreads/chatGPT/plain.txt";
    
    private static final String code = "/home/orbisu/projects/java-senior/Phase-1/src/main/java/com/org/phase/multithreads/chatGPT/code.txt";

    public static void main(String[] args) throws Exception {

    	decryptFile(code, plain);

        // if ("encrypt".equalsIgnoreCase(args[0])) {
        //     encryptFile("plain.txt", "code.txt");
        //     System.out.println("Encryption completed.");
        // } else if ("decrypt".equalsIgnoreCase(args[0])) {
        //     decryptFile("code.txt", "plain_restored.txt");
        //     System.out.println("Decryption completed.");
        // } else {
        //     System.out.println("Unknown command: " + args[0]);
        // }
    }

    private static void encryptFile(String inputFile, String outputFile) throws Exception {
        String plainText = Files.readString(Paths.get(inputFile));
        System.out.println(plainText);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());

        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        String base64 = Base64.getEncoder().encodeToString(encrypted);

        Files.writeString(Paths.get(outputFile), base64);
    }

    private static void decryptFile(String inputFile, String outputFile) throws Exception {
        String base64 = Files.readString(Paths.get(inputFile));

        byte[] encrypted = Base64.getDecoder().decode(base64);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getKey());

        byte[] decrypted = cipher.doFinal(encrypted);
        String plainText = new String(decrypted, StandardCharsets.UTF_8);

        Files.writeString(Paths.get(outputFile), plainText);
    }

    private static SecretKeySpec getKey() throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(SECRET.getBytes(StandardCharsets.UTF_8));

        byte[] key16 = new byte[16];
        System.arraycopy(key, 0, key16, 0, 16);

        return new SecretKeySpec(key16, "AES");
    }
}
