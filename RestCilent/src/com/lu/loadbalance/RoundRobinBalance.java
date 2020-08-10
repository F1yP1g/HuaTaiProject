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
 
        // 2.ȡ����key,�ŵ�set��
        Set<String> ipset=ipServerMap.keySet();
 
        // 3.set�ŵ�list��Ҫѭ��listȡ��
        ArrayList<String> iplist=new ArrayList<String>();
        iplist.addAll(ipset);
 
        String serverName=null;
 
        // 4.����һ��ѭ����ֵ���������set�ʹ�0��ʼ
        synchronized(pos){
            if (pos>=ipset.size()){
                pos=0;
            }
            serverName=iplist.get(pos);
            //��ѯ+1
            pos ++;
        }
        return serverName;
    }
 
    public static void main(String[] args) {
        RoundRobinBalance testRoundRobin=new RoundRobinBalance();
        
    }
}