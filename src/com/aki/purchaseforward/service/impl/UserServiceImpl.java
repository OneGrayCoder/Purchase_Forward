package com.aki.purchaseforward.service.impl;

import com.aki.purchaseforward.dao.UserDao;
import com.aki.purchaseforward.dao.impl.UserDaoImpl;
import com.aki.purchaseforward.pojo.User;
import com.aki.purchaseforward.service.UserService;

import javax.servlet.annotation.WebServlet;
import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 19:01
 */
public class UserServiceImpl  implements UserService {
    UserDao users = new UserDaoImpl();

    @Override
    public boolean regist(User user){
        return users.regist(user);
    }

    @Override
    public String securityPass(String password) {
        return users.securityPassword(password);
    }


    @Override
    public boolean checkName(String userName){
        return users.checkName(userName);
    }

    @Override
    public boolean checkLogin(User user){
        return users.User_Login(user);
    }

}
