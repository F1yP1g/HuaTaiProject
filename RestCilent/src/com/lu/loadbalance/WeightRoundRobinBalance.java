package com.lu.loadbalance;

import java.util.*;

import com.lu.address.ServerAddresses;

public class WeightRoundRobinBalance {
	 static Integer pos=0;

	    public static String getWeightRoundRobin()
	    {
	        // �ؽ�һ��Map������������������ߵ��µĲ�������
	        Map<String, Integer> serverMap =
	                new HashMap<String, Integer>();
	        serverMap.putAll(ServerAddresses.serverWeightMap);

	        // ȡ��Ip��ַList
	        Set<String> keySet = serverMap.keySet();
	        Iterator<String> iterator = keySet.iterator();

	        List<String> serverList = new ArrayList<String>();
	        while (iterator.hasNext())
	        {
	            String server = iterator.next();
	            int weight = serverMap.get(server);
	            for (int i = 0; i < weight; i++)
	                serverList.add(server);
	        }

	        String server = null;
	        synchronized (pos)
	        {
	            if (pos > keySet.size())
	                pos = 0;
	            server = serverList.get(pos);
	            pos ++;
	        }

	        return server;
	    }
	    
	    public static void main(String[] args) {
	    	WeightRoundRobinBalance testRoundRobin=new WeightRoundRobinBalance();
	        for (int i=0;i<15;i++){
	            String serverIp=testRoundRobin.getWeightRoundRobin();
	            System.out.println(serverIp);
	        }
	    }
}