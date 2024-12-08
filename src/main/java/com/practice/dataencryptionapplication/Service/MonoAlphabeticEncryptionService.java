package com.practice.dataencryptionapplication.Service;

import org.springframework.stereotype.Service;

@Service
public class MonoAlphabeticEncryptionService {

    // Normal alphabet and coded alphabet arrays
    private static final char[] normalChar =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] codedChar =
            {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};

    // Encrypt the message based on the mapping
    public String monoAlphabeticEncryption(String message, String formula) {
        StringBuilder resultMessage = new StringBuilder();

        // Process the message based on encryption or decryption
        if (formula.equalsIgnoreCase("Encrypt")) {
            for (char ch : message.toCharArray()) {
                if (Character.isLowerCase(ch)) {
                    // Encrypt lowercase letters
                    resultMessage.append(encryptChar(Character.toLowerCase(ch)));
                } else if (Character.isUpperCase(ch)) {
                    // Encrypt uppercase letters
                    resultMessage.append(Character.toUpperCase(encryptChar(Character.toLowerCase(ch))));
                } else {
                    resultMessage.append(ch); // Keep non-alphabet characters unchanged
                }
            }
        } else if (formula.equalsIgnoreCase("Decrypt")) {
            for (char ch : message.toCharArray()) {
                if (Character.isLowerCase(ch)) {
                    // Decrypt lowercase letters
                    resultMessage.append(decryptChar(Character.toLowerCase(ch)));
                } else if (Character.isUpperCase(ch)) {
                    // Decrypt uppercase letters
                    resultMessage.append(Character.toUpperCase(decryptChar(Character.toLowerCase(ch))));
                } else {
                    resultMessage.append(ch); // Keep non-alphabet characters unchanged
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid formula. Use 'Encrypt' or 'Decrypt'.");
        }

        return resultMessage.toString();
    }

    // Encrypt a character using the mapping
    private char encryptChar(char ch) {
        for (int i = 0; i < normalChar.length; i++) {
            if (normalChar[i] == ch) {
                return codedChar[i]; // Return the corresponding encrypted character
            }
        }
        return ch; // Default if not found (non-alphabet character)
    }

    // Decrypt a character using the mapping
    private char decryptChar(char ch) {
        for (int i = 0; i < codedChar.length; i++) {
            if (codedChar[i] == ch) {
                return normalChar[i]; // Return the corresponding decrypted character
            }
        }
        return ch; // Default if not found (non-alphabet character)
    }
}
