package com.example.mp.service;


import com.example.mp.pojo.IpInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ip-api", url = "${ip-api.url}")
public interface IpInfoService {
    @GetMapping("/json/{ip}")
    IpInfo getIpInfo(@PathVariable String ip);
}
