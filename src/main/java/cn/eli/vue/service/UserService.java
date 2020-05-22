package cn.eli.vue.service;






import cn.eli.vue.entity.User;
import cn.eli.vue.mapper.UserMapper;

import java.util.List;

public interface UserService {


	public boolean Addnew(String username, String password);

	 public List<User> selectUsers();

	public User findUser(String username);
}
