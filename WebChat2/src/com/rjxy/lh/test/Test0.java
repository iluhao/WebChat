package com.rjxy.lh.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rjxy.lh.entity.ChatUser;
import com.rjxy.lh.mapper.ChatUserMapper;


// 测试spring容器和mybatis是否集成成功


public class Test0 {
	public static void main(String[] args) {
		ApplicationContext aContext = 
				new ClassPathXmlApplicationContext("applicationContext.xml", "spring_mvc.xml");
		ChatUserMapper userMapper = (ChatUserMapper) aContext.getBean("chatUserMapper");
		ChatUser user = userMapper.selectByPrimaryKey("201111120112");
		System.out.println(user.getNickname());
		
		
	}
}
