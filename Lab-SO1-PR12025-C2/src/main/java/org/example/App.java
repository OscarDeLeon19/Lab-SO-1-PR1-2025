package org.example;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32]; // 256 bits de seguridad
        random.nextBytes(key);
        String secretKey = Base64.getEncoder().encodeToString(key);
        System.out.println("Clave secreta generada: " + secretKey);
        System.out.println(secretKey);
    }
}
