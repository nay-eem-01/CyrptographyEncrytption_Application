package com.practice.dataencryptionapplication.Controller;

import com.practice.dataencryptionapplication.Model.DataForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crypto/project")
public class EncryptionController {

    @GetMapping("/index")
    public String getIndex (DataForm dataForm){

        return "index page loading";
    }
}
