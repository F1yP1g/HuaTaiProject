package com.lu.rest.Server;

import java.util.HashMap;   
import java.util.Map;  
  
import javax.ws.rs.Path;  
import javax.ws.rs.Produces;  
import javax.ws.rs.PathParam;  
import javax.ws.rs.core.MediaType;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;  
import javax.ws.rs.POST;  

import com.lu.rest.bean.User;  
  
@Path("/users")  
public class UserResource {
	static Map<String,User> userMap = new HashMap<>();
	static Map<String,Integer> countMap = new HashMap<>();
	static {
		countMap.put("getUserByName", 0);
		countMap.put("getUserByAge", 0);
		countMap.put("deleteUser", 0);
		countMap.put("getAllUsers", 0);
		countMap.put("updateUser", 0);
	}
	static {
		User alice = new User("alice","22");
		User bob = new User("bob","12");
		User cindy = new User("cindy","12");
		userMap.put(alice.getUserName(), alice);
		userMap.put(bob.getUserName(), bob);
		userMap.put(cindy.getUserName(), cindy);
	}

	@GET
	@Path("/age/{j}") 
	@Produces(MediaType.TEXT_XML)
	public String getuserByAge(@PathParam("j") String age) {
	 
		String res = "<Users>";
		for(String id : userMap.keySet()) {
			User user = userMap.get(id);
			if(age.contentEquals(user.getAge())) {
				res +="\n\t<User>\n" + "\t\t<Name>" + user.getUserName() + "</Name>\n" 
						+ "\t\t<Age>" + user.getAge() + "</Age>\n" + "\t</User>";
			}
		}
		countMap.put("getUserByAge", countMap.get("getUserByAge")+1);
		return res + "\n</Users>";
	}
	
	@GET
	@Path("/{j}") 
	@Produces(MediaType.TEXT_XML)
	public String getuserByName(@PathParam("j") String name) {
		String res = "<Users>";
		for(String id : userMap.keySet()) {
			User user = userMap.get(id);
			if(name.contentEquals(user.getUserName())) {
				res +="\n\t<User>\n" +"\t\t<Name>" + user.getUserName() + "</Name>\n" 
						+ "\t\t<Age>" + user.getAge() + "</Age>\n" + "\t</User>";
			}
		}
		countMap.put("getUserByName", countMap.get("getUserByName")+1);
		return res + "\n</Users>";
	}
	
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public String getAllUsers() {
		String res = "<Users>";
		for(String id : userMap.keySet()) {
			User user = userMap.get(id);
			res +="\n\t<User>\n"+"\t\t<Name>" +  user.getUserName() + "</Name>\n" + 
					"\t\t<Age>" + user.getAge() + "</Age>\n" + "\t</User>";
		}
		countMap.put("getAllUsers", countMap.get("getAllUsers")+1);
		return res+"\n</Users>";
	}
	
	@DELETE
	@Path("/{i}")
	public String deleteUser(@PathParam("i") String name) {
		String res = "<Delete>";
		if(!userMap.containsKey(name)) {
			res+= "\n\t<Error>No such user in server.</Error>\n";
		}else {
			userMap.remove(name);
			res += "\n\t<Success>delete: "+name+"</Success>\n";
		}
		countMap.put("deleteUser", countMap.get("deleteUser")+1);
		return res + "</Delete>";
	}
	
	@POST  
	@Produces(MediaType.TEXT_XML)
    public String updateUser(@FormParam("name") String name,@FormParam("age") String age){   
		userMap.put(name, new User(name,age));
		
		countMap.put("updateUser", countMap.get("updateUser")+1);
        return "<Success>update: "+name+"</Success>\n"; 
    }
}