package com.rjxy.lh.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rjxy.lh.biz.ChatUserBiz;
import com.rjxy.lh.entity.ChatUser;
import com.rjxy.lh.mapper.ChatUserMapper;

// 测试BIZ方法是否成功
public class Test {
	public static void main(String[] args) {
		ApplicationContext aContext = 
				new ClassPathXmlApplicationContext("applicationContext.xml", "spring_mvc.xml");
		ChatUserBiz chatUserBiz = (ChatUserBiz) aContext.getBean("chatUserBiz");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userid", "wumeng");
		paramMap.put("password", "123456");
		ChatUser user = chatUserBiz.selectLoginUser(paramMap);
		if (user == null) {
			System.out.println("密码错误");
		} else {
			System.out.println("欢迎:" + user.getNickname());
		}
		
		
	}
}
