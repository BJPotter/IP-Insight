package com.example.mp.pojo;

import lombok.Data;

@Data
public class IpInfo {
    private String query;
    private String city;
    private String regionName;
    private String country;
    private String isp;
}
