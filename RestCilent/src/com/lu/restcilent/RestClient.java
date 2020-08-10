package com.lu.restcilent;

import javax.ws.rs.client.ClientBuilder;  
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.client.Entity;   
import javax.ws.rs.core.Response;


import javax.ws.rs.client.Client;  

public class RestClient {  
	private static String serverUri = "http://localhost:8080/restServer/rest/users"; 
	static Client client = ClientBuilder.newClient();
	static WebTarget target = null;
	static Response  response = null;
	public static void main(String[] agr) {
		for(int i = 0; i <20; i++) {
			getAllUsers(serverUri);//��ѯ�����û�
			delUserByName(serverUri, "alice");//ɾ�� alice
			getUserByName(serverUri, "alice");//��ѯ alice
			updateUser(serverUri, "lu", "24");//��� lu
			getUserByName(serverUri,"lu");//��ѯ lu
		}
	}
    /**
     * ����û�
     * @param name
     * @param age
     */
    public static void updateUser(String serverUri,String name, String age) {
    	System.out.println("-------------���ָ���û�-------------");  
		target = client.target(serverUri);
		MultivaluedMap<String, String> form= new MultivaluedHashMap<>();
		form.add("name", name);
		form.add("age", age);
		response = target.request().buildPost(Entity.form(form)).invoke();
		String result = response.readEntity(String.class);
		System.out.println(result);
        response.close();  
    }
       
    /** 
     * ɾ���û� 
     */  
     public static void delUserByName(String serverUri,String name) {  
         System.out.println("-------------ɾ��ָ���û�-------------");  
         target = client.target(serverUri + "/"+name);  
         response = target.request().delete(); 
         String value = response.readEntity(String.class);  
    	 System.out.println(value);  
         response.close();  
    }  
    
     /**
            * �����û�����ѯ�û���Ϣ/Get
      * @param serverUri ��ַ
      * @param name �û���
      */
     public  static void getUserByName(String serverUri,String name){  
    	 //System.out.println("-------------��ѯָ���û�-------------");  
         target = client.target(serverUri+"/"+name);  
         response = target.request().get();  
 		 String value = response.readEntity(String.class);  
 		//System.out.println(value);  
		 response.close();
    } 
    /** 
     * ��ѯ�����û� 
     */  
     public static void getAllUsers(String serverUri) {
         System.out.println("-------------��ѯ�����û�-------------");  
         WebTarget target = client.target(serverUri);  
         response = target.request().get();
 		 String value = response.readEntity(String.class);  
    	 System.out.println(value);  
		 response.close();
     }  
}  