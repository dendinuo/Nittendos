package com.course.model;

import lombok.Data;

@Data
public class User {
	private int orderid;
	private String username;
	private String names;
	private String password;
	private long money;
	
	@Override
	public String toString() {
		return(
				"{"+"orderid"+orderid+","+
						"username"+username+","+
						"names"+names+","+
						"password"+password+","+
						"money"+money+"}");
	}
}

