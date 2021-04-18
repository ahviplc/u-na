package com.lc.una.consumer.web;

import com.lc.una.consumer.client.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    ProviderClient providerClient;

    @GetMapping("/hi-feign")
    public String hiFeign(){
        System.out.println("...ConsumerController...hi-feign...");
        return providerClient.hi("feign");
    }
}
