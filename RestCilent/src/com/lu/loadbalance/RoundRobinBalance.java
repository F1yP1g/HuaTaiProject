package com.lu.loadbalance;
import com.lu.address.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RoundRobinBalance {
    // Integer sum=0;
    static Integer  pos = 0;
 
    public static String getRoundRobinServer(){
        Map<String,Integer> ipServerMap=new ConcurrentHashMap<>();
        ipServerMap.putAll(ServerAddresses.serverWeightMap);
 
        // 2.取出来key,放到set中
        Set<String> ipset=ipServerMap.keySet();
 
        // 3.set放到list，要循环list取出
        ArrayList<String> iplist=new ArrayList<String>();
        iplist.addAll(ipset);
 
        String serverName=null;
 
        // 4.定义一个循环的值，如果大于set就从0开始
        synchronized(pos){
            if (pos>=ipset.size()){
                pos=0;
            }
            serverName=iplist.get(pos);
            //轮询+1
            pos ++;
        }
        return serverName;
    }
 
    public static void main(String[] args) {
        RoundRobinBalance testRoundRobin=new RoundRobinBalance();
        
    }
}