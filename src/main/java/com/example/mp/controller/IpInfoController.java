package com.example.mp.controller;

import com.example.mp.pojo.IpInfo;
import com.example.mp.service.IpInfoService;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Controller
public class IpInfoController {
    @Autowired
    private IpInfoService ipInfoService;


    @GetMapping("/info")
    public String getIpInfo(Model model, HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ip = getClientIp(request);
        IpInfo ipInfo = ipInfoService.getIpInfoOnline(ip);
        IpInfo ipInfoLocal = ipInfoService.getIpInfoLocal(ip);
        model.addAttribute("ipInfo", ipInfo);
        model.addAttribute("ipInfoLocal", ipInfoLocal);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            model.addAttribute(headerName, request.getHeader(headerName));
        }

        return "index";
    }

    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }




}
