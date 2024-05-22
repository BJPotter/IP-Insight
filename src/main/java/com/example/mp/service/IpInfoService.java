package com.example.mp.service;

import com.example.mp.pojo.IpInfo;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import java.io.IOException;

public interface IpInfoService {
   public IpInfo getIpInfoOnline(String ip);
   public IpInfo getIpInfoLocal(String ip) throws IOException, GeoIp2Exception;
}
