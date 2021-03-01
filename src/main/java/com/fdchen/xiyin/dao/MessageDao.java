package com.fdchen.xiyin.dao;

import com.fdchen.xiyin.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao {

    // 添加信息
    public int add(Message msg);

    // 修改信息状态
    public int update(int uid, int fromUid);

    // 检索与指定用户的聊天信息
    public List<Message> findChatMsgList(int fromUid, int toUid);

    // 检索当前用户最近的所有聊天信息
    public List<Message> findMsgList(int uid);

}
