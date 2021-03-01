package com.fdchen.xiyin.impl;

import com.fdchen.xiyin.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 数据库users表记录映射为User对象
 */
public class UserRowMapperImpl implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int arg1) throws SQLException {
            // TODO Auto-generated method stub
            User user = new User();
            user.uid = rs.getInt("uid");
            user.avatar=rs.getString("avatar");
            user.name=rs.getString("name");
            user.sex=rs.getInt("sex");
            user.age=rs.getInt("age");
            user.region=rs.getString("region");
            user.intro=rs.getString("intro");
            user.interests=rs.getString("interests");

            return user;
        }
}
