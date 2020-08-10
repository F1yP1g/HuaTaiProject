package com.lu.rest.bean;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement  
public class User {  
  
    private String userName;  
    private String age;  
      
    public User() {};  
      
    public User( String userName, String age) {   
        this.userName = userName;  
        this.age = age;  
    }
    public String getUserName() {  
        return userName;  
    }  
    public void setUserName(String userName) {  
        this.userName = userName;  
    }  
    public String getAge() {  
        return age;  
    }  
    public void setAge(String age) {  
        this.age = age;  
    }
}  