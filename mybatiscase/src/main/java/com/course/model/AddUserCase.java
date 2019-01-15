package com.course.model;

import lombok.Data;

@Data
public class AddUserCase {
	private int orderid;
	private String username;
	private String names;
	private String password;
	private long money;
}
