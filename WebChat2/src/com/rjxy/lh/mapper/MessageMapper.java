package com.rjxy.lh.mapper;
import java.util.List;
import java.util.Map;

import com.rjxy.lh.entity.Message;

public interface MessageMapper {
	
	// 根据消息编号修改消息状态
	public int updateMessageStatusById(String id);
	
	// 读取未读消息的验证消息结合
	public List<Message> selectCheckMessage(String id);
	// 查询消息列表
	public List<Message> selectMessages(Map map);
	
	//修改消息状态
	public int updateMessageStatus(Map map);
	
	// 查询未读消息个数
	public int selectNoReadMessageCount(Map map);
	
    int deleteByPrimaryKey(String id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
}