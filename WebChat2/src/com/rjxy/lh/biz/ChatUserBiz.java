package com.rjxy.lh.biz;

import java.util.List;
import java.util.Map;

import com.rjxy.lh.entity.ChatUser;
import com.rjxy.lh.entity.Message;

public interface ChatUserBiz {
	// 处理验证消息 消息id 处理结果
	public int doCheckMessage(String mid, String resu);
	// 增加好友的功能
	public int insertChatUserFriend(ChatUser user, String friendid);
	// 查找可以添加的好友列表
	public List<ChatUser> selectNotFriends(String id);
	// 查找登录用户的好友列表
	public List<ChatUser> selectUserFriends(String id);
	// 登录方法
	public ChatUser selectLoginUser(Map<String, String> map);
	// 注册新用户
	int insert(ChatUser record);
	
}
