package com.zwj.service.serviceImpl;


import com.zwj.mapper.UsersMapper;
import com.zwj.pojo.Users;
import com.zwj.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);
        Users result = usersMapper.selectOne(user);


        return result== null ?false:true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {
        String userId = sid.nextShort();
        user.setId(userId);
        usersMapper.insert(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users selectUser(String username, String password) {
        Example e = new Example(Users.class);
        Example.Criteria criteria = e.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);
        Users result = usersMapper.selectOneByExample(e);
        return result;
    }

    @Override
    public void updateUserInfo(Users user) {
        Example e = new Example(Users.class);
        Example.Criteria criteria = e.createCriteria();
        criteria.andEqualTo("id",user.getId());
        usersMapper.updateByExampleSelective(user,e);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {

        Example e = new Example(Users.class);
        Example.Criteria criteria = e.createCriteria();
        criteria.andEqualTo("id",userId);
        Users user = usersMapper.selectOneByExample(e);

        return user;
    }


}
