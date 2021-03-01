package com.fdchen.xiyin.impl;

import com.fdchen.xiyin.dao.UserDao;
import com.fdchen.xiyin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * 检查用户是否存在/是否已经注册
     * @param phone 用户手机号
     * @return 1为已经存在，0为不存在，-1为查询失败
     */
    @Override
    public int isExist(String phone) {
        String sql = "select count(*) from users where phone=:phone;";

        Map<String, String> param = new HashMap<>();

        param.put("phone", phone);

        // 返回查询到的值
        int res = jdbcTemplate.queryForObject(sql, param, Integer.class);

        if (res == 1) return 1;
        if (res == 0) return 0;
        return -1;
    }

    /**
     * 用户注册
     * @param user 用户对象，包含用户注册的信息
     * @return 0插入成功，-1插入失败
     */
    @Override
    public int add(User user) {
        String sql = "insert into users(phone,pwd,name)" +
                "values(:phone,:pwd,:name);";
        Map<String, Object> param = new HashMap<>();

        param.put("phone", user.phone);
        param.put("pwd", user.pwd);
        param.put("name", user.name);

        // 返回受影响条数
        int res = jdbcTemplate.update(sql, param);
        if (res == 1) return 0;
        return -1;
    }

    /**
     * 用户验证，登录
     * @param user 用户对象，包含用户信息
     * @return 登录成功返回uid，-1登录失败
     */
    @Override
    public int check(User user) {
        String sql = "select pwd from users where phone =:phone";

        Map<String, String> param = new HashMap<>();

        param.put("phone", user.phone);

        String pwd = jdbcTemplate.queryForObject(sql, param, String.class);

        if (pwd != null && pwd.equals(user.pwd)) {

            List<User> users = null;

            sql = "select * from users where phone =" + user.phone;

            users = jdbcTemplate.query(sql, new UserRowMapperImpl());

            User user_check = null;

            user_check = users.get(0);

            return user_check.uid;
        }
        return -1;

    }


    /**
     * 更新用户信息，依据不为空的信息进行更新
     * @param user 用户对象，包含将要更新的信息
     * @return 0为更新成功，-1为更新失败
     */
    @Override
    public int update(User user) {
        String sql = "";     // 执行的sql语句
        int res = 0;      // 返回受影响的条数
        Map<String, Object> param = new HashMap<>();

        if (user.uid != -1) {
            param.put("uid", user.uid);      // 检索条件，uid
        }

        // 修改信息
        if (user.pwd != null) {
            param.put("pwd", user.pwd);
            param.put("phone", user.phone);
            sql = "update users set pwd=:pwd where phone=:phone;";
        } else if (user.avatar != null) {
            param.put("avatar", user.avatar);
            sql = "update users set avatar=:avatar where uid=:uid;";
        } else if (user.name != null) {
            param.put("name", user.name);
            sql = "update users set name=:name where uid=:uid;";
        } else if (user.sex != -1) {
            param.put("sex", user.sex);
            sql = "update users set sex=:sex where uid=:uid;";
        } else if (user.age != -1) {
            param.put("age", user.age);
            sql = "update users set age=:age where uid=:uid;";
        } else if (user.region != null) {
            param.put("region", user.region);
            sql = "update users set region=:region where uid=:uid;";
        } else if (user.intro != null) {
            param.put("intro", user.intro);
            sql = "update users set intro=:intro where uid=:uid;";
        } else if (user.interests != null) {
            param.put("interests", user.interests);
            sql = "update users set interests=:interests where uid=:uid;";
        }

        if (sql != "")
            res = jdbcTemplate.update(sql, param);

        if (res >= 1) return 0;
        return -1;
    }


    /**
     * 根据uid返回用户详细信息
     * @param uid 用户uid
     * @return user对象
     */
    @Override
    public User findUser(int uid) {
        List<User> users = null;
        String sql = "select * from users where uid=" + uid;

        users = jdbcTemplate.query(sql, new UserRowMapperImpl());

        User user = null;

        user = users.get(0);

        return user;
    }

    /**
     * 根据关键词返回模糊检索的用户列表
     * 如果关键字为纯数字，额外进行uid的精确检索
     * @param keyword 检索关键字
     * @return user列表
     */
    @Override
    public List<User> findUserList(String keyword) {
        List<User> users = null, users_uid = null;

        // name字段模糊检索
        String sql_name = "select * from users where name like " + "\'%" + keyword + "%\';";

        users = jdbcTemplate.query(sql_name, new UserRowMapperImpl());


        // 判断是否为纯数字，如果不为纯数字就不进行id字段的精确精索
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (pattern.matcher(keyword).matches() == true) {
            // id字段精确检索
            String sql_uid = "select * from users where uid=" + keyword;

            users_uid = jdbcTemplate.query(sql_uid, new UserRowMapperImpl());


            // 合并两个检索结果
            int index = 0;  // 合并开始的索引值
            if (users.size() > 0) {
                index = users.size() - 1;
            }
            users.addAll(index, users_uid);
        }

        return users;
    }

    /**
     * 用户注销，暂未实现
     * @param uid 用户uid
     * @return 0为成功，-1为失败
     */
    @Override
    public int delete(int uid) {
        return -1;
    }
}
