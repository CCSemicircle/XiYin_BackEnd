package com.fdchen.xiyin.dao;

import com.fdchen.xiyin.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FanFollowDao {

    // 验证用户之间是否关注
    public int isFollow(int uid,int followUid);

    // 获取关注列表
    public List<User> getFollowList(int uid);

    // 获取粉丝列表
    public List<User> getFanList(int uid);

    // 添加关注
    public int addFollow(int uid,int followUid);

    // 取消关注
    public int deleteFollow(int uid,int followUid);

    // 检索关注列表
    public List<User> findFollowList(int uid,String keyword);

    // 检索粉丝列表
    public List<User> findFanList(int uid,String keyword);

    // 检索联系人列表
    public List<User> findFanFollowList(int uid,String keyword);
}
