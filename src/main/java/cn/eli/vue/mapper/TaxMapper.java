package cn.eli.vue.mapper;

import cn.eli.vue.entity.Tax;

import java.util.List;

public interface TaxMapper {


	List<Tax> selectUsers(String user_name);

	void updateUserByName(Tax tax_list, String user_name);

	void addUser(Tax tax_user, String user_name);

	//void deleteData(User user);

	//void GetAdmin(User user);

	int selectByName(String username, String user_name);//需要两个参数！！！！！


}
