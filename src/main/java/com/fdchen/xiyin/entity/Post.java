package com.fdchen.xiyin.entity;

import java.util.List;

public class Post {
    public int id;
    public int uid;
    public User user;
    public int postTypeId;
    public String location;
    public String content;
    public String postTime;
    public String upList;
    public List<User> upUsers;
}
