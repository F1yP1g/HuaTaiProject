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
			getAllUsers(serverUri);//查询所有用户
			delUserByName(serverUri, "alice");//删除 alice
			getUserByName(serverUri, "alice");//查询 alice
			updateUser(serverUri, "lu", "24");//添加 lu
			getUserByName(serverUri,"lu");//查询 lu
		}
	}
    /**
     * 添加用户
     * @param name
     * @param age
     */
    public static void updateUser(String serverUri,String name, String age) {
    	System.out.println("-------------添加指定用户-------------");  
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
     * 删除用户 
     */  
     public static void delUserByName(String serverUri,String name) {  
         System.out.println("-------------删除指定用户-------------");  
         target = client.target(serverUri + "/"+name);  
         response = target.request().delete(); 
         String value = response.readEntity(String.class);  
    	 System.out.println(value);  
         response.close();  
    }  
    
     /**
            * 根据用户名查询用户信息/Get
      * @param serverUri 地址
      * @param name 用户名
      */
     public  static void getUserByName(String serverUri,String name){  
    	 //System.out.println("-------------查询指定用户-------------");  
         target = client.target(serverUri+"/"+name);  
         response = target.request().get();  
 		 String value = response.readEntity(String.class);  
 		//System.out.println(value);  
		 response.close();
    } 
    /** 
     * 查询所有用户 
     */  
     public static void getAllUsers(String serverUri) {
         System.out.println("-------------查询所有用户-------------");  
         WebTarget target = client.target(serverUri);  
         response = target.request().get();
 		 String value = response.readEntity(String.class);  
    	 System.out.println(value);  
		 response.close();
     }  
}  