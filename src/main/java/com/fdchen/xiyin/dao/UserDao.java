package com.fdchen.xiyin.dao;

import com.fdchen.xiyin.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    // 检查用户是否已经注册
    int isExist(String phone);

    // 添加用户
    int add(User user);

    // 验证用户信息
    int check(User user);

    // 更新用户信息
    int update(User user);

    // 查找用户详细信息,除账号密码外
    User findUser(int uid);

    // 检索用户列表
    List<User> findUserList(String keyword);

    // 注销用户
    int delete(int uid);
}
