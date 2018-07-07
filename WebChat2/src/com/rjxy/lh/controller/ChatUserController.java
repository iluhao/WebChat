package com.rjxy.lh.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.chainsaw.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjxy.lh.biz.ChatUserBiz;
import com.rjxy.lh.biz.MessageBiz;
import com.rjxy.lh.entity.ChatUser;
import com.rjxy.lh.entity.Message;
import com.rjxy.lh.mapper.MessageMapper;
import com.rjxy.lh.util.DateUtil;

@Controller
@RequestMapping(value="chatUser")
public class ChatUserController {
	@Autowired
	private ChatUserBiz chatUserBiz;
	@Autowired
	private MessageBiz messageBiz;
	
	@RequestMapping("main")
	public String main(HttpSession session, Map map, String friendid) {
		// 获取用户登录信息
		ChatUser user = (ChatUser) session.getAttribute("loginUser");
		if (user == null) {
			return "login"; // 如果没有登录，则重新登录
		}
		// 读取发送给登录用户的验证消息列表
		List<Message> checkList = messageBiz.selectCheckMessage(user.getId());
		map.put("checkList", checkList);
		// 判断是否有好友与之聊天 如果有则读取还有聊天记录
		if(friendid != null &&  !"".equals(friendid)) {
//			List<Message> messagesList = messageBiz.selectMessage(friendid, user.toString());
			List<Message> messagesList = messageBiz.selectMessage(friendid, user.getId());
			map.put("messageList", messagesList);
		}
		// 读取好友列表 并保存 然后跳转到首页
		List<ChatUser> friendList = chatUserBiz.selectUserFriends(user.getId());
		for (int i = 0; i < friendList.size(); i++) {
			Map<String, String> pMap = new HashMap<String, String>();
			ChatUser friend = friendList.get(i);
			pMap.put("fromid", friend.getId());
			pMap.put("toid", user.getId());
			int count = messageBiz.selectNoReadMessageCount(pMap);
			
			// 用暂时无用的属性checktype保存未读消息的个数
			friend.setChecktype(count);
		}
		map.put("friendList", friendList);
		return "index";
	}
	
	@RequestMapping("login")
	public String login(ChatUser user, HttpSession session) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userid", user.getUserid());
		paramMap.put("password", user.getPassword());
		ChatUser loginUser = chatUserBiz.selectLoginUser(paramMap);
		if (loginUser == null) {
			return "login";
		}
		session.setAttribute("loginUser", loginUser);
		return "redirect:/chatUser/main.do";
	}
	
	@RequestMapping("addmessage")
	public String addmessage(HttpSession session, Message message) {
		// 获取登录用户信息
		ChatUser user = (ChatUser) session.getAttribute("loginUser");
		if (user == null) {
			return "login"; // 如果没有登录 则重新登录
		}
		message.setStatus(0);
		message.setTime(new Date());
		message.setType(1);
		message.setId(DateUtil.getId());
		message.setFromid(user.getId());
		messageBiz.insertMessage(message);
		// 发送完毕后 返回主页
		return "redirect:/chatUser/main.do?friendid="+message.getToid();
	}
	
	@RequestMapping("notfriends")
	public String notfriends(HttpSession session, Map map) {
		// 获取登录用户信息
		ChatUser user = (ChatUser) session.getAttribute("loginUser");
		if (user == null) {
			return "login"; // 如果没有登录 则重新登录
		}
		List<ChatUser> notFriendList = chatUserBiz.selectNotFriends(user.getId());
		map.put("notFriendList", notFriendList);
		// 发送完毕后 返回好友列表页面
		return "friend";
	}
	
	@RequestMapping("adduser")
	public String adduser(HttpSession session, ChatUser user) {
		user.setId("1");
		chatUserBiz.insert(user);
		// 发送完毕后 返回主页
		return "login";
	}
	
	@RequestMapping("addfriend")
	public String addfriend(HttpSession session, String friendid) {
		// 获取登录用户信息
		ChatUser user = (ChatUser) session.getAttribute("loginUser");
		if (user == null) {
			return "login"; // 如果没有登录 则重新登录
		}
		chatUserBiz.insertChatUserFriend(user, friendid);
		// 发送完毕后 返回到主页
		return "redirect:/chatUser/main.do";
	}
	
	
	@RequestMapping(value="checkmessage")
	public String checkMessage(String mid, String resu) {
		chatUserBiz.doCheckMessage(mid, resu);
		return "redirect:/chatUser/main.do";
	}
	
}
