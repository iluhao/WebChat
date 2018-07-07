package com.rjxy.lh.mapper;

import java.util.List;
import java.util.Map;

import com.rjxy.lh.entity.ChatUser;

public interface ChatUserMapper {
	// 增加好友信息
	public int insertUserFriend(Map map);
	// 查找登录用户的好友列表
	public List<ChatUser> selectUserFriends(String id);
	// 登录的方法
	public ChatUser selectLoginUser(Map<String, String> map);
	// 查找可以添加的好友列表
	public List<ChatUser> selectNotFriends(String id);
	
    int deleteByPrimaryKey(String id);

    int insert(ChatUser record);

    int insertSelective(ChatUser record);

    ChatUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChatUser record);

    int updateByPrimaryKey(ChatUser record);
}