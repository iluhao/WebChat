package com.rjxy.lh.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjxy.lh.biz.MessageBiz;
import com.rjxy.lh.entity.Message;
import com.rjxy.lh.mapper.ChatUserMapper;
import com.rjxy.lh.mapper.MessageMapper;

@Service(value="messageBiz")
public class MessageBizImpl implements MessageBiz {
	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public int selectNoReadMessageCount(Map map) {
		return messageMapper.selectNoReadMessageCount(map);
	}

	@Override
	public int insertMessage(Message message) {
		return messageMapper.insert(message);
	}	
	public MessageMapper getMessageMapper() {
		return messageMapper;
	}

	public void setMessageMapper(MessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}

	@Override
	public List<Message> selectMessage(String fromid, String toid) {
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("fromid", fromid);
		pMap.put("toid", toid);
		List<Message> messagesList = messageMapper.selectMessages(pMap);
		pMap.put("type", "1");
		messageMapper.updateMessageStatus(pMap);
		return messagesList;
	}

	@Override
	public List<Message> selectCheckMessage(String id) {
		return messageMapper.selectCheckMessage(id);
	}

	

	


}