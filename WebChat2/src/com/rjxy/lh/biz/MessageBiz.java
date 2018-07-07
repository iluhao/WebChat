package com.rjxy.lh.biz;

import java.util.List;
import java.util.Map;

import com.rjxy.lh.entity.Message;

public interface MessageBiz {
	// 读取未读消息的验证消息集合
	public List<Message> selectCheckMessage(String id);
	// 读取聊天消息列表
	public List<Message> selectMessage(String fromid, String toid);
	// 查询未读消息个数
	public int selectNoReadMessageCount(Map map);
	// 发送聊天消息
	public int insertMessage(Message message);
	


}
