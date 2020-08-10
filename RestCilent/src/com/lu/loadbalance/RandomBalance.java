package com.lu.loadbalance;
import com.lu.address.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RandomBalance{

  public static String getRandomServer() {
      Map<String,Integer> ipServerMap=new ConcurrentHashMap<>();
      ipServerMap.putAll(ServerAddresses.serverWeightMap);

      Set<String> ipSet=ipServerMap.keySet();

      //����һ��list������server
      ArrayList<String> ipArrayList=new ArrayList<String>();
      ipArrayList.addAll(ipSet);

      //ѭ�������
      Random random=new Random();
      //�������list������ȡ��1-list.size��
      int pos=random.nextInt(ipArrayList.size());
      String serverNameReturn= ipArrayList.get(pos);
      return  serverNameReturn;
  }
  public static void main(String[] args) {
  }
}