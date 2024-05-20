package com.example.mp.controller;

import com.example.mp.pojo.IpInfo;
import com.example.mp.service.IpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
public class IpInfoController {
    @Autowired
    private IpInfoService ipInfoService;

    @GetMapping("/info")
    public String getIpInfo(Model model, HttpServletRequest request) {
        String ip = getClientIp(request);
        IpInfo ipInfo = ipInfoService.getIpInfo(ip);
        model.addAttribute("ipInfo", ipInfo);
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
