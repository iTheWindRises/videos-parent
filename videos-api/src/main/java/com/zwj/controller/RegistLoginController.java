package com.zwj.controller;


import com.zwj.pojo.Users;
import com.zwj.pojo.vo.UsersVO;
import com.zwj.service.UserService;
import com.zwj.utils.JSONResult;
import com.zwj.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = "用户注册登录的接口")
public class RegistLoginController extends BasicController {

    @Autowired
    private UserService userService;

    //设置用户redis-session
    private UsersVO setUserRedisSessionToken(Users user) {
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":"+ user.getId(),uniqueToken,1000*60*30);

        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(user,userVO);
        userVO.setUserToken(uniqueToken);
        return userVO;
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody Users user) throws Exception {
        //1.判断用户名和密码不为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()))
            return JSONResult.errorMsg("用户名或密码为空");

        //2.判断用户名是否存在
        Boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());

        //3.保存用户,注册信息
        if (!usernameIsExist) {
            user.setNickname(user.getUsername());
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setFansCounts(0);
            user.setReceiveLikeCounts(0);
            user.setFollowCounts(0);
            userService.saveUser(user);

        }else {
            return JSONResult.errorMsg("用户名已存在");
        }
        user.setPassword("");

        UsersVO userVO = setUserRedisSessionToken(user);

        return JSONResult.ok(userVO);
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public JSONResult login(@RequestBody Users user) throws Exception {

        String username = user.getUsername();
        String password = user.getPassword();
        //1.判断用户名和密码不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            return JSONResult.errorMsg("用户名或密码为空");
        //2.判断用户名或密码是否正确
        Users userResult = userService.selectUser(username,MD5Utils.getMD5Str(password));

        if (userResult==null)
            return JSONResult.errorMsg("用户名或密码错误");

        userResult.setPassword("");

        UsersVO userVO = setUserRedisSessionToken(userResult);

        return JSONResult.ok(userVO);
    }

    @ApiOperation(value = "用户注销")
    @ApiImplicitParam(name = "userId" ,value = "用户id",required = true,
        dataType = "String" ,paramType = "query")
    @PostMapping("/logout")
    public JSONResult logout(String userId) throws Exception {
        redis.del(USER_REDIS_SESSION+":"+userId);

        return JSONResult.ok();
    }

}
