package com.practice.dataencryptionapplication.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForm {
    private String operation;
    private String plainText;
    private String cipherText;
    private String encrypt;
    private String decrypt;
}
