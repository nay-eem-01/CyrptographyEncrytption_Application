package com.practice.dataencryptionapplication.Controller;

import com.practice.dataencryptionapplication.Model.DataForm;
import com.practice.dataencryptionapplication.Service.AESEncryptionService;
import com.practice.dataencryptionapplication.Service.MonoAlphabeticEncryptionService;
import com.practice.dataencryptionapplication.Service.MonoNumericEncryptionService;
import com.practice.dataencryptionapplication.Service.RSAEncryptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/crypto/project")
public class EncryptionController {

    private final RSAEncryptionService rsaEncryptionService;
    private final MonoAlphabeticEncryptionService monoAlphabeticEncryption;
    private final MonoNumericEncryptionService monoNumericEncryption;
    private final AESEncryptionService aesEncryptionService;
    private final DataForm dataForm;



    public EncryptionController(RSAEncryptionService rsaEncryptionService, MonoAlphabeticEncryptionService monoAlphabeticEncryption, MonoNumericEncryptionService monoNumericEncryption, AESEncryptionService aesEncryptionService, DataForm dataForm) {
        this.rsaEncryptionService = rsaEncryptionService;
        this.monoAlphabeticEncryption = monoAlphabeticEncryption;
        this.monoNumericEncryption = monoNumericEncryption;
        this.aesEncryptionService = aesEncryptionService;
        this.dataForm = dataForm;
    }

    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("dataForm", dataForm);
        return "index";
    }

    @PostMapping("/app")
    public String encryptionApplication(@ModelAttribute DataForm dataForm, Model model, @RequestParam("action") String action) {

        String algorithm = dataForm.getAlgorithm();
        String result;

        switch (action.toLowerCase()) {
            case "encrypt":
                switch (algorithm.toUpperCase()) {
                    case "RSA":
                        result = rsaEncryptionService.RSA(dataForm.getPlainText(), "Encrypt");
                        break;
                    case "AES":
                        result = aesEncryptionService.encrypt(dataForm.getPlainText(), dataForm.getKey());
                        break;
                    case "MONOALPHABETIC":
                        result = monoAlphabeticEncryption.monoAlphabeticEncryption(dataForm.getPlainText(), "Encrypt");
                        break;
                    case "MONONUMERIC":
                        result = monoNumericEncryption.monoNumericEncryption(dataForm.getPlainText(), "Encrypt");
                        break;
                    default:
                        result = "Encryption not implemented for " + algorithm;
                }
                break;

            case "decrypt":
                switch (algorithm.toUpperCase()) {
                    case "RSA":
                        result = rsaEncryptionService.RSA(dataForm.getPlainText(), "Decrypt");
                        break;
                    case "AES":
                        result = aesEncryptionService.decrypt(dataForm.getCipherText(), dataForm.getKey());
                        break;
                    case "MONOALPHABETIC":
                        result = monoAlphabeticEncryption.monoAlphabeticEncryption(dataForm.getPlainText(), "Decrypt");
                        break;
                    case "MONONUMERIC":
                        result = monoNumericEncryption.monoNumericEncryption(dataForm.getCipherText(), "Decrypt");
                        break;
                    default:
                        result = "Decryption not implemented for " + algorithm;
                }
                break;

            default:
                result = "Invalid action";
        }

        // Set the result into the DataForm object
        dataForm.setCipherText(result);
        model.addAttribute("dataForm", dataForm);

        return "index"; // Return to the same page
    }


}
