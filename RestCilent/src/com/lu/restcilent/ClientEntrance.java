package com.lu.restcilent;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.lu.address.ServerAddresses;
import com.lu.loadbalance.ConsistentHashBalance;
import com.lu.loadbalance.RandomBalance;
import com.lu.loadbalance.RoundRobinBalance;
import com.lu.loadbalance.WeightRoundRobinBalance;

public class ClientEntrance {
	private static String serverUri = "192.168.3.103:8081/restServer/rest/users"; 
	//路径
	public static String path = "/restServer/rest/users";
	//姓名列表
	public static String[] names = new String[]{"alice","bob","cindy",
						"david","eva","fox","greedy","isa","zhangsan"};
	static {
		System.out.println("**********客户端进程**********");
		System.out.println("模拟对服务端用户数据增删改查功能");
		System.out.println("接口测试使用查询随机用户名用户");
		System.out.println("-----------------------------");
		System.out.println("负载均衡  \t编号");
		System.out.println("随机策略  \t1");
		System.out.println("轮询策略  \t2");
		System.out.println("加权轮询  \t3");
		System.out.println("一致性Hash\t4");
		System.out.println("*****************************");
	}
	
	public static void main(String[] arg) {
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.print("负载均衡策略编号：");
			int index = getInput(in);
			System.out.print("调用次数：");
			int count = getInput(in);
			if(index <= 0 || index >4 || count <=0) {
				System.out.println("错误：参数不合法");
				System.out.println("-----------------------------");
				continue;
			}
			startConnect(index,count);
			System.out.println("-----------------------------");
		}
	}
	
	/**
	 * 获取输入，非法输入返回-1
	 * @param in
	 * @return
	 */
	public static int getInput(Scanner in) {
		int num = -1;
		try {
			String index = in.next();
			num = Integer.parseInt(index);
		}catch(Exception e) {
			return -1;
		}
		return num;
	}
	/**
	 * 根据策略编号获取ip
	 * @param index
	 * @param key
	 * @return
	 */
	public static String getAddress(int index,String key) {
		String addressString = "";
		if(index == 1) addressString =RandomBalance.getRandomServer();
		else if(index == 2) addressString = RoundRobinBalance.getRoundRobinServer();
		else if(index == 3) addressString = WeightRoundRobinBalance.getWeightRoundRobin();
		else if(index == 4) addressString = ConsistentHashBalance.getConsistentHashServer(key);
		return addressString;
	}
	/**
	 * 选择服务器，连接
	 * @param index
	 * @param count
	 */
	public static void startConnect(int index,int count) {
		Set<String> set = ServerAddresses.serverWeightMap.keySet();
		Map<String, Integer> addressCountMap = new HashMap<>();
		for(String s : set) {
			addressCountMap.put(s, 0);
		}
		Random random=new Random();
		for(int i = 0; i < count; i++) {
			int pos=random.nextInt(names.length);
			String name = names[pos];
			String address = getAddress(index, name);
			addressCountMap.put(address, addressCountMap.get(address)+1);
			RestClient.getUserByName("http://"+address+path,"cindy");
		}
		System.out.println("服务端请求统计：");
		for(String key:addressCountMap.keySet()) {
			System.out.println(key + "调用次数："+addressCountMap.get(key));
		}
	}
}
