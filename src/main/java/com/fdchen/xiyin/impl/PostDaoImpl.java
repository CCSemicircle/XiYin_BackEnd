    package com.fdchen.xiyin.impl;
    
    import com.fdchen.xiyin.dao.PostDao;
    import com.fdchen.xiyin.entity.Post;
    import com.fdchen.xiyin.entity.User;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
    import org.springframework.stereotype.Repository;
    
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    
    @Repository
    public class PostDaoImpl implements PostDao {
    
        @Autowired
        private NamedParameterJdbcTemplate jdbcTemplate;
    
        /**
         * 发布帖子
         * @param post 发布帖子信息
         * @return 0为成功，-1为失败
         */
        @Override
        public int add(Post post) {
            String sql = "insert into posts(uid,postTypeId,location,content,postTime)" +
                    "values(:uid,:postTypeId,:location,:content,now());";
            Map<String, Object> param = new HashMap<>();
    
            param.put("uid", post.uid);
            param.put("postTypeId", post.postTypeId);
            param.put("location", post.location);
            param.put("content", post.content);

            // 返回受影响条数
            int res = jdbcTemplate.update(sql, param);
            if (res == 1) return 0;
            return -1;
        }
    
        /**
         * 根据定位返回所有公开寻友贴
         * @param location 用户当前定位
         * @return post列表
         */
        @Override
        public List<Post> findPostListThruLocation(String location) {
            List<Post> posts = null;
            String sql = "select * from posts where location like \'%" + location + "%\';";
    
            posts = jdbcTemplate.query(sql, new PostRowMapperImpl());
    
            for (int i = 0; i < posts.size(); i++) {
                posts.get(i).user = findUserList(posts.get(i).uid + "").get(0);
                posts.get(i).upUsers = findUserList(posts.get(i).upList);
            }
    
            return posts;
        }
    
        /**
         * 根据关键词和当前定位检索寻友贴列表
         * @param keyword 搜索关键字
         * @return post列表
         */
        @Override
        public List<Post> findPostListThruKwd(String keyword, String location) {
            List<Post> posts = null;
    
            // 所有文字字段模糊检索，发布者昵称、寻友贴类型、定位、内容、发布时间
            String sql = "select * from posts as p where concat(" +
                    "(select name from users as u where p.uid=u.uid)," +
                    "(select postType from postTypes as pt where p.postTypeId= pt.id)," +
                    "location,content,postTime)" +
                    "like " + "\'%" + keyword + "%\' and location like \'%" + location + "%\';";
    
            System.out.println(sql);
    
            posts = jdbcTemplate.query(sql, new PostRowMapperImpl());
    
            for (int i = 0; i < posts.size(); i++) {
                posts.get(i).user = findUserList(posts.get(i).uid + "").get(0);
                posts.get(i).upUsers = findUserList(posts.get(i).upList);
            }
    
            return posts;
        }
    
        /**
         * 返回用户所发寻友贴列表
         * @param uid 用户uid
         * @return post列表
         */
        @Override
        public List<Post> findPostListThruUid(int uid) {
            List<Post> posts = null;
    
            String sql = "select * from posts where uid=" + uid;
    
            posts = jdbcTemplate.query(sql, new PostRowMapperImpl());
    
            for (int i = 0; i < posts.size(); i++) {
                posts.get(i).user = findUserList(posts.get(i).uid + "").get(0);
                posts.get(i).upUsers = findUserList(posts.get(i).upList);
            }
    
            return posts;
        }

        /**
         * 检索用户所发寻友贴列表
         * @param uid 用户uid
         * @param keyword 检索关键字
         * @return post列表
         */
        public List<Post> findPostListThruUidKwd(int uid, String keyword){
            List<Post> posts=null;
    
            String sql = "select * from posts as p where uid="+ uid + " and concat(" +
                    "(select name from users as u where p.uid=u.uid)," +
                    "(select postType from postTypes as pt where p.postTypeId= pt.id)," +
                    "location,content,postTime)" +
                    "like " + "\'%" + keyword + "%\';";
    
            System.out.println(sql);
    
            posts = jdbcTemplate.query(sql, new PostRowMapperImpl());
    
            for (int i = 0; i < posts.size(); i++) {
                posts.get(i).user = findUserList(posts.get(i).uid + "").get(0);
                posts.get(i).upUsers = findUserList(posts.get(i).upList);
            }
    
            return posts;
        }
    
        /**
         * 获取顶帖列表，根据每一个uid进行遍历
         * @param upList 顶帖用户id列表
         * @return user列表
         */
        @Override
        public List<User> findUserList(String upList) {
    
            List<User> users = null;
    
            String sql = "select * from users where uid in (" + upList + ");";
    
            users = jdbcTemplate.query(sql, new UserRowMapperImpl());
    
            return users;
    
        }
    
        /**
         * 用户顶帖
         * @param id,uid 寻友贴id，用户uid
         * @return 1为已经顶帖，0为成功，-1为失败
         */
        @Override
        public int up(int id, int uid) {
    
            Map<String, Object> param = new HashMap<>();
    
            // 判断用户是否已经顶帖
            String isUpSql = "select upList from posts where id=:id";
    
            param.put("id", id);
    
            String upList = jdbcTemplate.queryForObject(isUpSql, param, String.class);

            if (upList != null && upList.length() != 0) {
                String[] uids = upList.split(",");
    
                // 如果在upList中找到了对应的uid，代表该用户已经顶帖，返回1
                for (int i = 0; i < uids.length && uids[i]!=null ; i++) {
                    if (uid == Integer.valueOf(uids[i])) {
                        return 1;
                    }
                }
            }
    
            // 如果不存在，则修改upList并传入数据库
            if(upList == null){
                upList = "" + uid;
            }else {
                upList = upList + "," + uid;
            }
    
            String sql = "update posts set upList=:upList where id = :id;";
    
            param.put("upList", upList);
    
            int res = jdbcTemplate.update(sql, param);
    
            if (res == 1) return 0;
            return -1;
        }
    
        /**
         * 删除寻友贴，暂未实现
         * @param id 寻友贴id
         * @return 0为成功，-1为失败
         */
        @Override
        public int delete(int id) {
            return -1;
        }
    }
