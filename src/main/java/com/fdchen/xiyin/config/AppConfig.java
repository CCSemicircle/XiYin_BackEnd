package com.fdchen.xiyin.config;

import com.fdchen.xiyin.impl.FanFollowDaoImpl;
import com.fdchen.xiyin.impl.MessageDaoImpl;
import com.fdchen.xiyin.impl.PostDaoImpl;
import com.fdchen.xiyin.impl.UserDaoImpl;
import org.apache.logging.log4j.message.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.fdchen.xiyin.config")
public class AppConfig {

    /*
    @Bean
    public UserDaoImpl getUserDAO() {
        return new UserDaoImpl(); // 直接new对象做演示
    }
    */

    /*
    @Bean
    public PostDaoImpl getPostDAO() {
        return new PostDaoImpl(); // 直接new对象做演示
    }*/

    @Bean
    public FanFollowDaoImpl getFanFollowDao() {
        return new FanFollowDaoImpl();
    }

    @Bean
    public MessageDaoImpl getMessageDao() {
        return new MessageDaoImpl();
    }
}
