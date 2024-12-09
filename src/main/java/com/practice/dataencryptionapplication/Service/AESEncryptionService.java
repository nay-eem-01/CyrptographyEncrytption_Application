package com.practice.dataencryptionapplication.Service;
import org.springframework.stereotype.Service;

@Service
public class AESEncryptionService {

    // Method to encrypt the message using AES
    // Method to encrypt the message using AES
    public String encrypt(String plaintext, String key) {
        try {
            // Validate the key length
            // Inside your AES encryption method
            if (key.length() != 16 && key.length() != 24 && key.length() != 32) {
                return "Error: AES requires a key length of 16, 24, or 32 characters.";
            }


            AES aes = new AES();
            int[][] keyMatrix = aes.keySchedule(key);  // Generate the key matrix

            // Pad plaintext to be a multiple of 16 (AES block size)
            while (plaintext.length() % 16 != 0) {
                plaintext += " ";  // Add padding to the plaintext (could use other padding schemes like PKCS7)
            }

            // Convert the plaintext to a 4x4 matrix
            int[][] state = aes.stringToMatrix(plaintext);

            // Perform the AES encryption on the state
            aes.encryptState(state, keyMatrix);

            // Return the encrypted message as a string
            return AES.MatrixToString(state);
        } catch (Exception e) {
            return "Encryption Error: " + e.getMessage();
        }
    }

    public String decrypt(String ciphertext, String key) {
        try {
            AES aes = new AES();
            int[][] keyMatrix = aes.keySchedule(key);
            int[][] state = aes.stringToMatrix(ciphertext);
            aes.decryptState(state, keyMatrix);
            return AES.MatrixToString(state);
        } catch (Exception e) {
            return "Decryption Error: " + e.getMessage();
        }
    }
}
