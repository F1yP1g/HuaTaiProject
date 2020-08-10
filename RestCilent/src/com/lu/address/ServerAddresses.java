package com.lu.address;
import java.util.HashMap;

public class ServerAddresses {
	//µÿ÷∑map
	public static HashMap<String, Integer> serverWeightMap =
            							new HashMap<String, Integer>();
	public static String[] servers = {"192.168.3.103:8080",
			"192.168.3.103:8081","192.168.3.103:8082"};
    static
    {
        serverWeightMap.put("192.168.3.103:8080", 1);
        serverWeightMap.put("192.168.3.103:8081", 2);
        serverWeightMap.put("192.168.3.103:8082", 1);
    }
}
