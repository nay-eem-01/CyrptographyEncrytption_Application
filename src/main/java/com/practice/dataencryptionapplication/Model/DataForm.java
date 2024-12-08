package com.practice.dataencryptionapplication.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class DataForm {
    private String algorithm;
    private String plainText;
    private String cipherText;
    private String encrypt;
    private String decrypt;
}
