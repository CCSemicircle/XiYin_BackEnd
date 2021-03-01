package com.fdchen.xiyin.controller;

import com.fdchen.xiyin.dao.FanFollowDao;
import com.fdchen.xiyin.dao.UserDao;
import com.fdchen.xiyin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/fanfollow")
public class FanFollowController {

    @Autowired
    private FanFollowDao fanFollowDao;

    /**
     * 检查用户是否已经关注
     */
    @RequestMapping(value = "/isFollow", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> isFollow(@RequestParam("uid") int uid,
                                       @RequestParam("followUid") int followUid) {

        Map<String,Integer> res = new HashMap<>();
        int code=fanFollowDao.isFollow(uid,followUid);

        res.put("code",code);

        return res;
    }

    /**
     * 获取关注列表
     */
    @RequestMapping(value = "/getFollows", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getFollows(@RequestParam("uid") int uid) {

        Map<String,Object> res = new HashMap<>();
        List<User> users = fanFollowDao.getFollowList(uid);

        if(users!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("users",users);

        return res;
    }

    /**
     * 获取粉丝列表
     */
    @RequestMapping(value = "/getFans", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getFans(@RequestParam("uid") int uid) {

        Map<String,Object> res = new HashMap<>();
        List<User> users = fanFollowDao.getFanList(uid);

        if(users!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("users",users);

        return res;
    }

    /**
     * 添加关注
     */
    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> follow(@RequestParam("uid") int uid,
                                        @RequestParam("followUid") int followUid) {

        Map<String,Integer> res = new HashMap<>();
        int code=fanFollowDao.addFollow(uid,followUid);

        res.put("code",code);

        return res;
    }

    /**
     * 取消关注
     */
    @RequestMapping(value = "/unfollow", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Integer> unfollow(@RequestParam("uid") int uid,
                                      @RequestParam("followUid") int followUid) {

        Map<String,Integer> res = new HashMap<>();
        int code=fanFollowDao.deleteFollow(uid,followUid);

        res.put("code",code);

        return res;
    }

    /**
     * 检索关注列表，支持模糊查找
     */
    @RequestMapping(value = "/searchFollows", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> searchFollows(@RequestParam("uid") int uid,
                                     @RequestParam("keyword") String keyword){

        Map<String,Object> res = new HashMap<>();
        List<User> users=fanFollowDao.findFollowList(uid,keyword);
        if(users!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("users",users);

        return res;
    }

    /**
     * 检索粉丝列表，支持模糊查找
     */
    @RequestMapping(value = "/searchFans", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> searchFans(@RequestParam("uid") int uid,
                                     @RequestParam("keyword") String keyword){

        Map<String,Object> res = new HashMap<>();
        List<User> users=fanFollowDao.findFanList(uid,keyword);
        if(users!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("users",users);

        return res;
    }

    /**
     * 检索联系人（粉丝/关注）列表
     */
    @RequestMapping(value = "/searchFansFollows", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> searchFansFollows(@RequestParam("uid") int uid,
                                         @RequestParam("keyword") String keyword){

        Map<String,Object> res = new HashMap<>();
        List<User> users=fanFollowDao.findFanFollowList(uid,keyword);
        if(users!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("users",users);

        return res;
    }
}
