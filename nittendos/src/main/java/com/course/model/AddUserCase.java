package com.course.model;

import lombok.Data;

@Data
public class AddUserCase {
	private int id;
	private String userName;
	private String age;
	private String sex;
	private String permission;
	private String isDelete;
	private String password;
	private String expected;
}
