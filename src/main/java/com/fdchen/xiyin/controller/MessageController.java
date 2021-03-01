package com.fdchen.xiyin.controller;


import com.fdchen.xiyin.dao.MessageDao;
import com.fdchen.xiyin.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private MessageDao msgDao;

    /**
     * 发送消息
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> sendMessage(@RequestBody Map<String,String> map){

        Message msg= new Message();

        msg.fromUid=Integer.valueOf(map.get("fromUid"));
        msg.toUid=Integer.valueOf(map.get("toUid"));
        msg.msgContent=map.get("msgContent");
        msg.msgTypeId=Integer.valueOf(map.get("msgTypeId"));

        Map<String,Integer> res = new HashMap<>();
        int code=msgDao.add(msg);

        res.put("code",code);

        return res;
    }

    /**
     * 修改信息状态
     */
    @RequestMapping(value = "/recept", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> recept(@RequestParam("uid") int uid,
                                    @RequestParam("fromUid") int fromUid){

        Map<String,Object> res = new HashMap<>();

        int code=msgDao.update(uid,fromUid);

        res.put("code",code);

        return res;
    }

    /**
     * 检索与指定用户的聊天信息
     */
    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> chat(@RequestParam("fromUid") int fromUid,
                                      @RequestParam("toUid") int toUid){

        Map<String,Object> res = new HashMap<>();

        List<Message> msgs=msgDao.findChatMsgList(fromUid,toUid);
        if(msgs!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("msgs",msgs);

        return res;
    }

    /**
     * 检索当前用户最近的所有聊天信息
     */
    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> recent(@RequestParam("uid") int uid){

        Map<String,Object> res = new HashMap<>();

        List<Message> msgs=msgDao.findMsgList(uid);
        if(msgs!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("msgs",msgs);

        return res;
    }
}
