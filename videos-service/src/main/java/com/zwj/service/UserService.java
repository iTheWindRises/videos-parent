package com.zwj.service;


import com.zwj.pojo.Users;
import org.apache.catalina.User;

public interface UserService {
    public Boolean queryUsernameIsExist(String username);

    public void saveUser(Users user);


    Users selectUser(String username, String password);

    //修改用户信息
    void updateUserInfo(Users user);

    Users queryUserInfo(String userId);
}
