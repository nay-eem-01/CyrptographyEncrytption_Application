package com.practice.dataencryptionapplication.Service;

import org.springframework.stereotype.Service;

@Service
public class MonoNumericEncryptionService {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String monoNumericEncryption(String message, String formula) {
        StringBuilder result = new StringBuilder();

        // Encrypt the message
        if ("Encrypt".equalsIgnoreCase(formula)) {
            for (char character : message.toCharArray()) {
                if (Character.isLetter(character)) {
                    // Encrypt: Convert the letter to its position (A=1, B=2, ..., Z=26)
                    int position = ALPHABET.indexOf(Character.toUpperCase(character)) + 1;
                    if (Character.isLowerCase(character)) {
                        // Preserve the case of the letter
                        result.append(position).append(" ");
                    } else {
                        result.append(position).append(" ");
                    }
                } else {
                    result.append(character); // Non-letters are preserved
                }
            }

            // Decrypt the message
        } else if ("Decrypt".equalsIgnoreCase(formula)) {
            String[] parts = message.split("\\s+"); // Split the input by spaces
            for (String part : parts) {
                try {
                    int position = Integer.parseInt(part); // Convert to integer
                    if (position >= 1 && position <= 26) {
                        // Convert position back to letter and preserve case
                        char letter = ALPHABET.charAt(position - 1);
                        result.append(letter); // Append the decrypted letter
                    } else {
                        result.append("?"); // For invalid numbers
                    }
                } catch (NumberFormatException e) {
                    result.append(part); // Keep non-numeric parts unchanged
                }
            }

        } else {
            return "Invalid formula"; // In case the formula is not 'Encrypt' or 'Decrypt'
        }

        return result.toString().trim(); // Remove any trailing spaces
    }
}
