package com.example.ip.controller;

import com.example.ip.model.Ip;
import com.example.ip.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IpController {
    @Autowired
    IpService ipService;

    @GetMapping("/ips")
    List<Ip> list(){
        return ipService.list();
    }
    @GetMapping("/alive")
    String alive(){
        return "OK";
    }
}
