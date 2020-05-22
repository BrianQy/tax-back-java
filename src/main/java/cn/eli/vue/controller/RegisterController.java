package cn.eli.vue.controller;


import cn.eli.vue.api.CommonResult;
import cn.eli.vue.entity.User;
import cn.eli.vue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult register(@RequestBody User user_in){
        if(userService.findUser(user_in.getUsername())== null) {
            userService.Addnew(user_in.getUsername(), user_in.getPassword());
            return CommonResult.success("注册成功,您的用户名是"+user_in.getUsername(),null);
        }
        else{
            return CommonResult.failed("用户名已存在");
        }

    }


}
