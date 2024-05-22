package com.example.mp.service;

import com.example.mp.pojo.IpInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Author: Zhi Liu
 * Date: 2024/5/22 14:26
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@FeignClient(name = "ip-api", url = "${ip-api.url}")
public interface ThreeApi {
    @GetMapping("/json/{ip}")
    IpInfo getIpInfo(@PathVariable String ip);
}
