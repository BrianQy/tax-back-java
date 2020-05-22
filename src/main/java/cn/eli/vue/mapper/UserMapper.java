package cn.eli.vue.mapper;



import cn.eli.vue.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> selectUsers();

    void addUser(User user);

    User findUser(String username);

}
