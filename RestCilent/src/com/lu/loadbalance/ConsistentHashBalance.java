package com.lu.loadbalance;
import com.lu.address.*;
import java.util.LinkedList;  
import java.util.List;  
import java.util.SortedMap;  
import java.util.TreeMap;  

public class ConsistentHashBalance {

    //��ʵ����б�
    private static List<String> realNodes = new LinkedList<String>();  

    //����ڵ㣬key��ʾ����ڵ��hashֵ��value��ʾ����ڵ������  
    private static SortedMap<Integer, String> virtualNodes = new TreeMap<Integer, String>();  

    //����ڵ����Ŀ,һ����ʵ����Ӧ5������ڵ�  
    private static final int VIRTUAL_NODES = 5;  

    static{  
        //�Ȱ�ԭʼ�ķ�������ӵ���ʵ����б���  
        for(int i=0; i<ServerAddresses.servers.length; i++)  
            realNodes.add(ServerAddresses.servers[i]);  

        //���������ڵ�
        for (String str : realNodes){  
            for(int i=0; i<VIRTUAL_NODES; i++){  
                String virtualNodeName = str + "&&VN" + String.valueOf(i);  
                int hash = getHash(virtualNodeName);  
                virtualNodes.put(hash, virtualNodeName);  
            }  
        }  
        System.out.println();  
    }  

    //ʹ��FNV1_32_HASH�㷨
    private static int getHash(String str){  
        final int p = 16777619;  
        int hash = (int)2166136261L;  
        for (int i = 0; i < str.length(); i++)  
            hash = (hash ^ str.charAt(i)) * p;  
        hash += hash << 13;  
        hash ^= hash >> 7;  
        hash += hash << 3;  
        hash ^= hash >> 17;  
        hash += hash << 5;  

        // ����������ֵΪ������ȡ�����ֵ  
        if (hash < 0)  
            hash = Math.abs(hash);  
        return hash;  
    }  

    //�õ�Ӧ��·�ɵ��Ľ��  
    public  static String getConsistentHashServer(String key){  
       //�õ���key��hashֵ  
        int hash = getHash(key);  
        // �õ����ڸ�Hashֵ������Map  
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);  
        String virtualNode;  
        if(subMap.isEmpty()){  
           //���û�бȸ�key��hashֵ��ģ���ӵ�һ��node��ʼ  
           Integer i = virtualNodes.firstKey();  
           //���ض�Ӧ�ķ�����  
           virtualNode = virtualNodes.get(i);  
        }else{  
           //��һ��Key����˳ʱ���ȥ��node������Ǹ����  
           Integer i = subMap.firstKey();  
           //���ض�Ӧ�ķ�����  
           virtualNode = subMap.get(i);  
        }  
        //virtualNode����ڵ����ƽ�ȡ
        if(virtualNode != null){  
            return virtualNode.substring(0, virtualNode.indexOf("&&"));  
        }  
        return null;  
    } 
}