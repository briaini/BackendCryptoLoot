package com.universityoflimerick.sdaa.BackendCryptoLoot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {


    @GetMapping(path = "/bri")
    public String getComment() {
        return "hello";
    }
}
