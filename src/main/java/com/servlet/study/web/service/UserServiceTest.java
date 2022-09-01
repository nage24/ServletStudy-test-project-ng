package com.servlet.study.web.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servlet.study.web.domain.user.UserRepositoryTest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceTest { // user정보를 담아온 list를 json으로 바꿔서 ajax에서 실행할 서블릿
	
	@NonNull
	private UserRepositoryTest userRepositoryTest;
	
	public String getUserList() {
		Gson gson = new GsonBuilder()
					.setPrettyPrinting()
					.create();
		
		return gson.toJson(userRepositoryTest.getUserList());
	}
	
}
