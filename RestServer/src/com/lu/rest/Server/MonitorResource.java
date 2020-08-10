package com.lu.rest.Server;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/monitor")
public class MonitorResource {

	@POST
	public String getPost() {
		StringBuilder res = new StringBuilder("<UseMonitor>");
		for(String key : UserResource.countMap.keySet()) {
			res.append(key +"():        "+ UserResource.countMap.get(key) + " times </br>");
		}
		return res.toString();
	}
	
}
