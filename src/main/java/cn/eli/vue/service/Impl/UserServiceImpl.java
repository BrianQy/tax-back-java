package cn.eli.vue.service.Impl;

import cn.eli.vue.mapper.UserMapper;
import cn.eli.vue.service.UserService;
import cn.eli.vue.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUsers() {
        return userMapper.selectUsers();
    }

    @Override
    public User findUser(String username){

        return userMapper.findUser(username);
    }

   @Override
    public boolean Addnew(String username, String password){
        User addnewr = new User();
        addnewr.setUsername(username);
        addnewr.setPassword(password);
        userMapper.addUser(addnewr);
        return true;
    }




}
