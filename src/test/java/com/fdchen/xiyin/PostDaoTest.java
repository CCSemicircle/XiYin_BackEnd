package com.fdchen.xiyin;

import com.fdchen.xiyin.dao.PostDao;
import com.fdchen.xiyin.entity.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = XiyinApplication.class)
public class PostDaoTest {
    @Autowired
    private PostDao postDao;

    @Test
    public void testInsert(){
        Post post=new Post();
        postDao.add(post);
        System.out.println("插入成功！");
    }
}
