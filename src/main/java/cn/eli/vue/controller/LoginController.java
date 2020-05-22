package cn.eli.vue.controller;

import cn.eli.vue.api.CommonResult;
import cn.eli.vue.entity.User;
import cn.eli.vue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.eli.vue.service.RedisService;

import java.util.List;
import java.util.UUID;

/**
 * @author: Yi Qin
 * @Date: 2019-11-14 11:33:26
 * @Description：
 */

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody User user_in) {
        List<User> users = userService.selectUsers();
        int flag;
        flag = 0;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user_in.getUsername().equals(user.getUsername()) && user_in.getPassword().equals(user.getPassword())) {
                flag = 1;
               String token = UUID.randomUUID().toString();
                //token = token.toUpperCase();
                String token1=token;
                String username=user.getUsername();
               redisService.set(token, username);

                //return CommonResult.success("登陆成功"+"欢迎"+user_in.getUsername()+"token:");
              //  return TokenResult.success("登陆成功!"+" 欢迎"+user_in.getUsername()+" token:"+token);
                return CommonResult.success(username,token);
            }
        }
        if(flag==0) {
            return CommonResult.validateFailed();
        }
        else{
            return CommonResult.validateFailed();
        }
    }


    @RequestMapping(value="/logout",method = RequestMethod.POST)
    public CommonResult logout(@RequestHeader("token")String token ){
      Boolean delete= redisService.delete(token);
      if(!delete) {
          return CommonResult.failed("登出失败，请检查是否登陆！");
      }
      else{
          return CommonResult.success("登出成功，欢迎再次使用！","已失效");
      }

    }
}
