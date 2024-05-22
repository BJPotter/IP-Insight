package com.example.mp.service.impl;

import com.example.mp.annotation.CacheResult;
import com.example.mp.pojo.IpInfo;
import com.example.mp.pojo.IpInfoCacheKey;
import com.example.mp.service.IpInfoService;
import com.example.mp.service.ThreeApi;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Author: Zhi Liu
 * Date: 2024/5/22 14:18
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Service
public class IpInfoServiceImpl implements IpInfoService{

    @Autowired
    ThreeApi threeApi;

    @CacheResult(cacheKey = "#this.getCacheOnlineKey(#ip)")
    public IpInfo getIpInfoOnline(String ip) {
        return fetchIpInfoFromRemote(ip);
    }

    @CacheResult(cacheKey = "#this.getCacheLocalKey(#ip)")
    public IpInfo getIpInfoLocal(String ip) throws IOException, GeoIp2Exception {
        // 执行业务逻辑,获取 IpInfo 对象
        return fetchIpInfoFromLocal(ip);
    }

    public String getCacheOnlineKey(String ip) {
        return IpInfoCacheKey.getIpInfoOnlineKey(ip);
    }

    public String getCacheLocalKey(String ip) {
        return IpInfoCacheKey.getIpInfoLocalKey(ip);
    }

    public IpInfo fetchIpInfoFromRemote(String ip) {
        return threeApi.getIpInfo(ip);
    }

    public IpInfo fetchIpInfoFromLocal(String ip) throws IOException, GeoIp2Exception {
        return getIpInfoLocalDb(ip);
    }

    public IpInfo getIpInfoLocalDb(String ip) throws IOException, GeoIp2Exception {
        IpInfo ipInfo = new IpInfo();
        ipInfo.setQuery(ip);
        File asnDatabase = new File("/opt/soft/java/db/GeoLite2-ASN.mmdb");
        File cityDatabase = new File("/opt/soft/java/db/GeoLite2-City.mmdb");
        //File asnDatabase = new File("D:\\Desktop\\IPDATA\\GeoLite2-ASN_20240517\\GeoLite2-ASN.mmdb");
        //File cityDatabase = new File("D:\\Desktop\\IPDATA\\GeoLite2-City_20240517\\GeoLite2-City.mmdb");
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
