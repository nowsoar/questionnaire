package github.nowsoar.questionnaire.controller;

import github.nowsoar.questionnaire.service.LoginService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@RestController
public class UserController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/api/login")
    public String login(@Param("username") String username, @Param("password") String password) {
        return loginService.login(username, password);
    }

    @PostMapping("/api/register")
    public String register(@Param("username") String username, @Param("password") String password) {
        return loginService.register(username, password);
    }
}
