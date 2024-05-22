package com.example.mp.pojo;

/**
 * Author: Zhi Liu
 * Date: 2024/5/22 14:07
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class IpInfoCacheKey {
    public static String getIpInfoOnlineKey(String ip) {
        return ip + ":ONLINE";
    }

    public static String getIpInfoLocalKey(String ip) {
        return ip + ":DB";
    }
}
