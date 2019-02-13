package com.course.model;

import lombok.Data;

@Data
public class GetUserInfoCase {
	private int id;
	private String userId;
	private String expected;
}
