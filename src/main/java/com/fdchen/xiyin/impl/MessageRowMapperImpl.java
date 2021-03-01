package com.fdchen.xiyin.impl;

import com.fdchen.xiyin.entity.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库messages表记录映射为Message对象
 */
public class MessageRowMapperImpl implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        Message msg = new Message();
        msg.id=rs.getInt("id");
        msg.fromUid = rs.getInt("fromUid");
        msg.toUid = rs.getInt("toUid");
        msg.msgContent=rs.getString("msgContent");
        msg.msgTypeId=rs.getInt("msgTypeId");
        msg.msgState=rs.getInt("msgState");
        msg.msgTime=rs.getString("msgTime");

        return msg;
    }
}
