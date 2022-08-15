package com.example.ip.controller;

import com.example.ip.model.Ip;
import com.example.ip.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class IpController {
    @Autowired
    IpService ipService;

    @GetMapping("/ips")
    HashMap list(){
        HashMap map = new HashMap();
        List<Ip> ips = this.ipService.list();
        map.put("size",ips.size());
        map.put("data",ips);
        return map;
    }
    @GetMapping("/alive")
    String alive(){
        return "OK";
    }
}
