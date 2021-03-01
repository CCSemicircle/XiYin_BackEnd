package com.fdchen.xiyin.impl;

import com.fdchen.xiyin.entity.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库posts表记录映射为Post对象
 */
public class PostRowMapperImpl implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        Post post = new Post();
        post.id=rs.getInt("id");
        post.uid = rs.getInt("uid");
        post.postTypeId = rs.getInt("postTypeId");
        post.location=rs.getString("location");
        post.content=rs.getString("content");
        post.postTime=rs.getString("postTime");
        post.upList=rs.getString("upList");

        return post;
    }
}