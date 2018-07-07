package com.rjxy.lh.biz.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjxy.lh.biz.ChatUserBiz;
import com.rjxy.lh.entity.ChatUser;
import com.rjxy.lh.entity.Message;
import com.rjxy.lh.mapper.ChatUserMapper;
import com.rjxy.lh.mapper.MessageMapper;
import com.rjxy.lh.util.DateUtil;

@Service(value="chatUserBiz")
public class ChatUserBizImpl implements ChatUserBiz {
	public MessageMapper getMessageMapper() {
		return messageMapper;
	}

	public void setMessageMapper(MessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}

	@Autowired
	private ChatUserMapper chatUserMapper;
	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public int insert(ChatUser record) {
		return chatUserMapper.insert(record);
	}
	
	//查找可以添加的好友列表
	@Override
	public List<ChatUser> selectNotFriends(String id) {
		return chatUserMapper.selectNotFriends(id);
	}
	
	@Override
	public List<ChatUser> selectUserFriends(String id) {
		return chatUserMapper.selectUserFriends(id);
	}
	
	@Override
	public ChatUser selectLoginUser(Map<String, String> map) {
		return chatUserMapper.selectLoginUser(map);
	}
	
	public ChatUserMapper getChatUserMapper() {
		return chatUserMapper;
	}

	public void setChatUserMapper(ChatUserMapper chatUserMapper) {
		this.chatUserMapper = chatUserMapper;
	}

	@Override
	public int insertChatUserFriend(ChatUser user, String friendid) {
		// 获取好友的基础信息
		ChatUser friend = chatUserMapper.selectByPrimaryKey(friendid);
		// 判断好友的身份类型
		if (friend.getChecktype() == 0) {
			// 如果允许添加好友，则直接添加
			Map<String, String> pmMap = new HashMap<>();
			pmMap.put("mineid", user.getId());
			pmMap.put("friendid", friendid);
			chatUserMapper.insertUserFriend(pmMap);
			
			pmMap.put("friendid", user.getId());
			pmMap.put("mineid", friendid);
			chatUserMapper.insertUserFriend(pmMap);
		} else {
			// 如果需要身份验证，则添加验证消息
			Message message = new Message();
			message.setFromid(user.getId());
			message.setToid(friendid);
			message.setContent(user.getNickname()+"申请加你为好友");
			message.setStatus(0);
			message.setTime(new Date());
			message.setType(0); // 验证消息
			message.setId(DateUtil.getId());
			
			messageMapper.insert(message);
		}
		return 0;
	}
	@Override
	public int doCheckMessage(String mid, String resu) {
		// 更新消息状态
		messageMapper.updateMessageStatusById(mid);
		// 判断处理意见
		if (resu.equals("1")) {
			// 同意 获取message对象
			Message message = messageMapper.selectByPrimaryKey(mid);
			Map<String, String> pmMap = new HashMap<>();
			pmMap.put("mineid", message.getFromid());
			pmMap.put("friendid", message.getToid());
			chatUserMapper.insertUserFriend(pmMap);
			
			pmMap.put("mineid", message.getToid());
			pmMap.put("friendid", message.getFromid());
			chatUserMapper.insertUserFriend(pmMap);
		} 
		return 0;
	}
	
}