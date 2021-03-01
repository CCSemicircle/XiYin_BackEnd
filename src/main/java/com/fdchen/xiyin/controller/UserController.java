package com.fdchen.xiyin.controller;

import com.fdchen.xiyin.dao.UserDao;
import com.fdchen.xiyin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    /**
     * 检查用户是否已经注册
     */
    @RequestMapping(value = "/isExist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> isExist(@RequestParam("phone") String phone) {

        Map<String, Integer> res = new HashMap<>();
        int code = userDao.isExist(phone);

        res.put("code", code);

        return res;
    }

    /**
     * 用户注册请求
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> register(@RequestBody Map<String, String> map) {
        User user = new User();

        user.phone = map.get("phone");
        user.pwd = map.get("pwd");
        user.name = map.get("name");

        Map<String, Integer> res = new HashMap<>();
        int code = userDao.add(user);

        res.put("code", code);

        return res;
    }

    /**
     * 用户登录请求
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, String> map) {
        User user = new User();

        user.phone = map.get("phone");
        user.pwd = map.get("pwd");

        Map<String, Object> res = new HashMap<>();
        int code = userDao.check(user);

        User newUser = null;

        if (code != -1) {
            newUser = userDao.findUser(code);
            newUser.phone = user.phone;
            newUser.pwd = user.pwd;
            code = 0;
        }
        res.put("user", newUser);
        res.put("code", code);

        return res;
    }

    /**
     * 用户修改信息请求
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> update(@RequestBody Map<String, String> map) {
        User user = new User();

        if (map.get("uid") != null) {
            user.uid = Integer.valueOf(map.get("uid"));
        }
        if (map.get("pwd") != null) {
            user.pwd = map.get("pwd");
            user.phone = map.get("phone");
        } else if (map.get("avatar") != null) {
            user.avatar = map.get("avatar");
        } else if (map.get("name") != null) {
            user.name = map.get("name");
        } else if (map.get("sex") != null) {
            user.sex = Integer.valueOf(map.get("sex"));
        } else if (map.get("age") != null) {
            user.age = Integer.valueOf(map.get("age"));
        } else if (map.get("region") != null) {
            user.region = map.get("region");
        } else if (map.get("intro") != null) {
            user.intro = map.get("intro");
        } else if (map.get("interests") != null) {
            user.interests = map.get("interests");
        }

        Map<String, Integer> res = new HashMap<>();
        int code = userDao.update(user);

        res.put("code", code);

        return res;
    }

    /**
     * 用户信息检索请求
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getInfo(@RequestParam("uid") int uid) {

        Map<String, Object> res = new HashMap<>();
        User user = userDao.findUser(uid);
        if (user != null) {
            res.put("code", 0);
        } else {
            res.put("code", -1);
        }
        res.put("user", user);

        return res;
    }

    /**
     * 查找用户，支持模糊查找
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> search(@RequestParam("keyword") String keyword) {

        Map<String, Object> res = new HashMap<>();
        List<User> users = userDao.findUserList(keyword);
        if (users != null) {
            res.put("code", 0);
        } else {
            res.put("code", -1);
        }
        res.put("users", users);

        return res;
    }
}
