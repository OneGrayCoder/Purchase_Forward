package com.aki.purchaseforward.service.impl;

import com.aki.purchaseforward.dao.UserDao;
import com.aki.purchaseforward.dao.impl.UserDaoImpl;
import com.aki.purchaseforward.pojo.User;
import com.aki.purchaseforward.service.UserService;

import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 19:01
 */
public class UserServiceImpl  implements UserService {
    UserDao users = new UserDaoImpl();

    @Override
    public boolean checkUser(User user) {
        return users.checkUser(user);
    }

    @Override
    public List<User> findAllUser(){
        return users.findUser();
    }

    @Override
    public int userNumber() {
        return users.userNumber();
    }


    @Override
    public boolean addUser(User user){
        return users.addUsers(user);
    }
}
