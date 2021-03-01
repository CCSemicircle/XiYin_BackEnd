package com.fdchen.xiyin;

import com.fdchen.xiyin.dao.UserDao;
import com.fdchen.xiyin.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = XiyinApplication.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert(){
        User user=new User();
        userDao.add(user);
        System.out.println("插入成功！");
    }
}
