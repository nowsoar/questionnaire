package github.nowsoar.questionnaire.service.impl;

import github.nowsoar.questionnaire.entity.User;
import github.nowsoar.questionnaire.mapper.UserMapper;
import github.nowsoar.questionnaire.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(String username, String password) {
        //TODO加密密码
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return "wrong";
        }
        if (user.getPassword().equals(password)) {
            user.setLastLoginTime(new Date());
            userMapper.update(user);
            return "success";
        } else {
            return "wrong";
        }
    }

    @Override
    public String register(String username, String password) {
        if (userMapper.findByUsername(username) != null) {
            return "wrong";
        }
        User user = new User();
        user.setCreateTime(new Date());
        user.setUsername(username);
        user.setPassword(password);
        userMapper.insert(user);
        return "success";
    }
}
