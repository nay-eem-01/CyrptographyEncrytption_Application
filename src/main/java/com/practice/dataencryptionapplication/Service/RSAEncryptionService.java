package com.practice.dataencryptionapplication.Service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Service
public class RSAEncryptionService {

    public String RSA(String message, String formula) {
        BigInteger p = largePrime(512);
        BigInteger q = largePrime(512);
        BigInteger n = n(p, q);
        BigInteger phi = getPhi(p, q);
        BigInteger e = genE(phi);
        BigInteger d = extEuclid(e, phi)[1];
        if (d.compareTo(BigInteger.ZERO) < 0) {
            d = d.add(phi); // Ensure d is positive
        }

//        System.out.println("p: " + p);
//        System.out.println("q: " + q);
//        System.out.println("n: " + n);
//        System.out.println("Phi: " + phi);
//        System.out.println("e: " + e);
//        System.out.println("d: " + d);

        List<BigInteger> chunks = chunkMessage(message, n);
        List<BigInteger> encryptedChunks = encryptChunks(chunks, e, n);

       // String encryptedMessage = encryptChunks(chunks,e,n);
        String decryptedMessage = decryptChunks(encryptedChunks, d, n);

        StringBuilder encryptedMessage = new StringBuilder();
        for (BigInteger chunk : encryptedChunks) {
            encryptedMessage.append(chunk.toString()).append("|"); // Use '|' as a delimiter
        }

        // Remove the trailing delimiter
        if (!encryptedMessage.isEmpty()) {
            encryptedMessage.setLength(encryptedMessage.length() - 1); // Trim the last '|'
        }

        System.out.println("Original message: " + message);
        System.out.println("encrypted message: " + encryptedMessage);
        System.out.println("decrypted message: " + decryptedMessage);

        if (formula.equalsIgnoreCase("Encrypt")){
            return encryptedMessage.toString();
        }

        return decryptedMessage;
    }

    public BigInteger stringCipher(String message) {
        StringBuilder cipherString = new StringBuilder();
        for (char ch : message.toCharArray()) {
            cipherString.append(String.format("%03d", (int) ch)); // Pad to 3 digits
        }
        return new BigInteger(cipherString.toString());
    }

    public String cipherToString(BigInteger message) {
        String cipherString = message.toString();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < cipherString.length(); i += 3) {
            if (i + 3 > cipherString.length()) break; // Handle incomplete chunks
            int asciiValue = Integer.parseInt(cipherString.substring(i, i + 3));
            output.append((char) asciiValue);
        }
        return output.toString();
    }

    public BigInteger getPhi(BigInteger p, BigInteger q) {
        return (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    public static BigInteger largePrime(int bits) {
        Random randomInteger = new Random();
        return BigInteger.probablePrime(bits, randomInteger);
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return a;
        } else {
            return gcd(b, a.mod(b));
        }
    }

    public static BigInteger[] extEuclid(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) return new BigInteger[] { a, BigInteger.ONE, BigInteger.ZERO };
        BigInteger[] values = extEuclid(b, a.mod(b));
        BigInteger d = values[0];
        BigInteger p = values[2];
        BigInteger q = values[1].subtract(a.divide(b).multiply(values[2]));
        return new BigInteger[] { d, p, q };
    }

    public static BigInteger genE(BigInteger phi) {
        BigInteger e = new BigInteger("65537");
        if (!gcd(e, phi).equals(BigInteger.ONE)) {
            throw new IllegalArgumentException("e and phi(n) are not coprime");
        }
        return e;
    }

    public List<BigInteger> encryptChunks(List<BigInteger> chunks, BigInteger e, BigInteger n) {
        List<BigInteger> encryptedChunks = new ArrayList<>();
        for (BigInteger chunk : chunks) {
            encryptedChunks.add(encrypt(chunk, e, n));
        }
        return encryptedChunks;
    }
    public String decryptChunks(List<BigInteger> encryptedChunks, BigInteger d, BigInteger n) {
        StringBuilder decryptedMessage = new StringBuilder();
        for (BigInteger chunk : encryptedChunks) {
            BigInteger decryptedChunk = decrypt(chunk, d, n);
            decryptedMessage.append(cipherToString(decryptedChunk));
        }
        return decryptedMessage.toString();
    }

    public static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    public static BigInteger decrypt(BigInteger message, BigInteger d, BigInteger n) {
        return message.modPow(d, n);
    }

    public BigInteger n(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }
    public List<BigInteger> chunkMessage(String message, BigInteger n) {
        List<BigInteger> chunks = new ArrayList<>();
        int chunkSize = n.bitLength() / 8 - 1; // Ensure each chunk is smaller than n
        for (int i = 0; i < message.length(); i += chunkSize) {
            String chunk = message.substring(i, Math.min(i + chunkSize, message.length()));
            chunks.add(stringCipher(chunk));
        }
        return chunks;
    }

}
