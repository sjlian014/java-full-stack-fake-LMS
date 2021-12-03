package com.github.sjlian014.jlms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class MailingAddressController {

    @GetMapping(path = "/{studentId}/mailing_address/")
    public String helloWorld() {
        System.out.println("called");
        return "hello there!";
    }
}
