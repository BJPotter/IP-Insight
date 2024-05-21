package com.example.mp.controller;

import com.example.mp.pojo.IpInfo;
import com.example.mp.service.IpInfoService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Enumeration;

@Controller
public class IpInfoController {
    @Autowired
    private IpInfoService ipInfoService;

    @GetMapping("/info")
    public String getIpInfo(Model model, HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ip = getClientIp(request);
        IpInfo ipInfo = ipInfoService.getIpInfo(ip);
        IpInfo ipInfoLocal = getIpInfoLocal(ip);
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


    public IpInfo getIpInfoLocal(String ip) throws IOException, GeoIp2Exception {
        IpInfo ipInfo = new IpInfo();
        ipInfo.setQuery(ip);
        File asnDatabase = new File("/opt/soft/java/db/GeoLite2-ASN.mmdb");
        File cityDatabase = new File("/opt/soft/java/db/GeoLite2-City.mmdb");
        DatabaseReader asnReader = new DatabaseReader.Builder(asnDatabase).build();
        DatabaseReader cityReader = new DatabaseReader.Builder(cityDatabase).build();
        InetAddress ipAddress = InetAddress.getByName(ip);
        AsnResponse asnResponse = asnReader.asn(ipAddress);
        CityResponse cityResponse = cityReader.city(ipAddress);
        Country country = cityResponse.getCountry();
        String countryName = country.getName(); // 'United States'
        ipInfo.setCountry(countryName);
        City city = cityResponse.getCity();
        String cityName = city.getName();
        ipInfo.setCity(cityName);
        String regionName = city.getName();
        ipInfo.setRegionName(regionName);
        String isp = asnResponse.getAutonomousSystemOrganization();
        ipInfo.setIsp(isp);
        Location location = cityResponse.getLocation();
        Double lat = location.getLatitude(); // 44.9733
        ipInfo.setLat(lat);
        Double lon = location.getLongitude(); // -93.2323
        ipInfo.setLon(lon);
        return ipInfo;
    }

}
