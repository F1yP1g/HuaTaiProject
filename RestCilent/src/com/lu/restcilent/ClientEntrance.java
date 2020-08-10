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
	//·��
	public static String path = "/restServer/rest/users";
	//�����б�
	public static String[] names = new String[]{"alice","bob","cindy",
						"david","eva","fox","greedy","isa","zhangsan"};
	static {
		System.out.println("**********�ͻ��˽���**********");
		System.out.println("ģ��Է�����û�������ɾ�Ĳ鹦��");
		System.out.println("�ӿڲ���ʹ�ò�ѯ����û����û�");
		System.out.println("-----------------------------");
		System.out.println("���ؾ���  \t���");
		System.out.println("�������  \t1");
		System.out.println("��ѯ����  \t2");
		System.out.println("��Ȩ��ѯ  \t3");
		System.out.println("һ����Hash\t4");
		System.out.println("*****************************");
	}
	
	public static void main(String[] arg) {
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.print("���ؾ�����Ա�ţ�");
			int index = getInput(in);
			System.out.print("���ô�����");
			int count = getInput(in);
			if(index <= 0 || index >4 || count <=0) {
				System.out.println("���󣺲������Ϸ�");
				System.out.println("-----------------------------");
				continue;
			}
			startConnect(index,count);
			System.out.println("-----------------------------");
		}
	}
	
	/**
	 * ��ȡ���룬�Ƿ����뷵��-1
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
	 * ���ݲ��Ա�Ż�ȡip
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
	 * ѡ�������������
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
		System.out.println("���������ͳ�ƣ�");
		for(String key:addressCountMap.keySet()) {
			System.out.println(key + "���ô�����"+addressCountMap.get(key));
		}
	}
}
