package main.util.sec;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashCalculator {
    public String hashString(final String password, final String salt) {
        if (password != null && salt != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                String passwordToHash = "592" + password + salt;
                messageDigest.update(passwordToHash.getBytes("UTF-8"));

                byte[] digest = messageDigest.digest();
                return String.format("%064x", new java.math.BigInteger(1, digest));
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("Password can not be empty");
    }

    public String generateSalt() {
        final Character[] characters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U', 'W', 'V', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

        final SecureRandom secureRandom = new SecureRandom();
        final StringBuilder saltStringBuilder = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            saltStringBuilder.append(characters[secureRandom.nextInt(characters.length)]);
        }
        return saltStringBuilder.toString();
    }
}
