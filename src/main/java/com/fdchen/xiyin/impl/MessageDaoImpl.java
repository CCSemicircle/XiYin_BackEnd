package com.fdchen.xiyin.impl;

import com.fdchen.xiyin.dao.MessageDao;
import com.fdchen.xiyin.dao.UserDao;
import com.fdchen.xiyin.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDaoImpl implements MessageDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;


    /**
     * 添加信息
     * @param msg 信息对象
     * @return 0代表添加成功，-1代表添加失败
     */
    @Override
    public int add(Message msg) {
        String sql = "insert into messages(fromUid,toUid,msgContent,msgTypeId,msgTime)" +
                "values(:fromUid,:toUid,:msgContent,:msgTypeId,now());";
        Map<String, Object> param = new HashMap<>();

        param.put("fromUid", msg.fromUid);
        param.put("toUid", msg.toUid);
        param.put("msgContent", msg.msgContent);
        param.put("msgTypeId", msg.msgTypeId);

        // 返回受影响条数
        int res = jdbcTemplate.update(sql, param);
        if (res == 1) return 0;
        return -1;
    }

    /**
     * 修改信息状态，
     * @param uid     当前用户uid
     * @param fromUid 目标对象uid
     * @return 0代表修改成功，-1代表修改失败
     */
    @Override
    public int update(int uid, int fromUid) {
        String sql = " update messages set msgState=1 where fromUid=:fromUid and toUid=:toUid;";

        Map<String, Object> param = new HashMap<>();

        param.put("fromUid", fromUid);
        param.put("toUid", uid);

        // 返回受影响条数
        int res = jdbcTemplate.update(sql, param);

        if (res >= 1) return 0;
        return -1;
    }

    /**
     * 检索与指定用户的聊天信息
     * @param fromUid 当前用户uid
     * @param toUid   目标对象uid
     * @return message列表
     */
    @Override
    public List<Message> findChatMsgList(int fromUid, int toUid) {
        List<Message> msgs = null;

        String sql = "select * from messages where (fromUid=" + fromUid +
                " and toUid=" + toUid + ") or (fromUid=" + toUid + " and toUid=" + fromUid + ");";

        msgs = jdbcTemplate.query(sql, new MessageRowMapperImpl());

        return msgs;
    }

    /**
     * 检索当前用户最近的所有聊天信息，效率有待提升
     * @param uid 当前用户uid
     * @return message列表
     */
    @Override
    public List<Message> findMsgList(int uid) {

        List<Message> msgs_send = null, msgs_rept=null;

        // 按聊天对象uid进行分组,再获取每一组的最近消息
        String sql_send = "select * from messages where id in (select max(id) from messages where fromUid=" + uid + " group by toUid);";

        msgs_send = jdbcTemplate.query(sql_send, new MessageRowMapperImpl());

        String sql_rept = "select * from messages where id in (select max(id) from messages where toUid=" + uid + " group by fromUid);";

        msgs_rept = jdbcTemplate.query(sql_rept, new MessageRowMapperImpl());

        // 找到msgs_send.toUid=msgs_rept.fromUid的两条记录，删去其中id比较小的（id小的记录不是最近的）
        for (int i = 0; i < msgs_send.size(); i++) {
            for (int j = 0; j < msgs_rept.size(); j++) {
                if (msgs_send.get(i).toUid == msgs_rept.get(j).fromUid) {
                    if (msgs_send.get(i).id < msgs_rept.get(j).id) {
                        msgs_send.remove(i);
                    } else {
                        msgs_rept.remove(j);
                    }
                    break;
                }
            }
        }

        msgs_send.addAll(msgs_rept);

        // 获取非当前用户的信息，即目标对象信息
        for (int i = 0; i < msgs_send.size(); i++) {
            int oid = msgs_send.get(i).fromUid;
            if (msgs_send.get(i).fromUid == uid) {
                oid = msgs_send.get(i).toUid;
            }
            msgs_send.get(i).user = userDao.findUser(oid);
        }

        return msgs_send;
    }
}
