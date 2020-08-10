package com.lu.loadbalance;
import com.lu.address.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RandomBalance{

  public static String getRandomServer() {
      Map<String,Integer> ipServerMap=new ConcurrentHashMap<>();
      ipServerMap.putAll(ServerAddresses.serverWeightMap);

      Set<String> ipSet=ipServerMap.keySet();

      //定义一个list放所有server
      ArrayList<String> ipArrayList=new ArrayList<String>();
      ipArrayList.addAll(ipSet);

      //循环随机数
      Random random=new Random();
      //随机数在list数量中取（1-list.size）
      int pos=random.nextInt(ipArrayList.size());
      String serverNameReturn= ipArrayList.get(pos);
      return  serverNameReturn;
  }
  public static void main(String[] args) {
  }
}