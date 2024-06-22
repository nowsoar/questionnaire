package github.nowsoar.questionnaire.service;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
public interface LoginService {

    public String login(String username, String password);

    public String register(String username, String password);
}
