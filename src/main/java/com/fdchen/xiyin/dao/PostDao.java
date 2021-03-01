package com.fdchen.xiyin.dao;

import com.fdchen.xiyin.entity.Post;
import com.fdchen.xiyin.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao {

    // 添加寻友贴
    int add(Post post);

    // 根据定位返回所有公开寻友贴
    List<Post> findPostListThruLocation(String location);

    // 根据关键字检索寻友贴列表
    List<Post> findPostListThruKwd(String keyword, String location);

    // 返回用户所发寻友贴列表
    List<Post> findPostListThruUid(int uid);

    // 检索用户所发寻友贴列表
    List<Post> findPostListThruUidKwd(int uid, String keyword);

    // 获取某寻友贴的所有顶帖用户列表
    List<User> findUserList(String upList);

    // 顶帖
    int up(int id,int uid);

    // 删除帖子
    int delete(int id);
}
