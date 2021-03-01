package com.fdchen.xiyin.controller;

import com.fdchen.xiyin.dao.PostDao;
import com.fdchen.xiyin.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostDao postDao;

    /**
     * 发布寻友贴
     */
    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> addPost(@RequestBody Map<String,String> map){

        Post post= new Post();

        post.uid=Integer.valueOf(map.get("uid"));
        post.postTypeId=Integer.valueOf(map.get("postTypeId"));
        post.location=map.get("location");
        post.content=map.get("content");

        Map<String,Integer> res = new HashMap<>();
        int code=postDao.add(post);

        res.put("code",code);

        return res;
    }

    /**
     * 根据用户当前定位返回所有公开寻友贴
     */
    @RequestMapping(value = "/getPosts", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPosts(@RequestParam("location") String location){

        Map<String,Object> res = new HashMap<>();

        List<Post> posts=postDao.findPostListThruLocation(location);
        if(posts!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("posts",posts);

        return res;
    }

    /**
     * 根据关键字和当前定位检索寻友贴
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> search(@RequestParam("keyword") String keyword,
                                      @RequestParam("location") String location){

        Map<String,Object> res = new HashMap<>();

        List<Post> posts=postDao.findPostListThruKwd(keyword,location);
        if(posts!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("posts",posts);

        return res;
    }

    /**
     * 返回用户所发寻友贴
     */
    @RequestMapping(value = "/userPost", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> userPost(@RequestParam("uid") int uid){

        Map<String,Object> res = new HashMap<>();

        List<Post> posts=postDao.findPostListThruUid(uid);
        if(posts!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("posts",posts);

        return res;
    }

    /**
     * 检索用户所发寻友贴
     */
    @RequestMapping(value = "/searchUserPost", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchUserPost(@RequestParam("uid") int uid,
                                        @RequestParam("keyword") String keyword){

        Map<String,Object> res = new HashMap<>();

        List<Post> posts=postDao.findPostListThruUidKwd(uid,keyword);
        if(posts!=null){
            res.put("code",0);
        }else{
            res.put("code",-1);
        }
        res.put("posts",posts);

        return res;
    }

    /**
     * 顶帖
     */
    @RequestMapping(value = "/upPost", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> upPost(@RequestBody Map<String,String> map){
       int id = Integer.valueOf(map.get("id"));
       int uid = Integer.valueOf(map.get("uid"));

       int code = postDao.up(id,uid);

        Map<String,Integer> res = new HashMap<>();

        res.put("code",code);

        return res;
    }
}
