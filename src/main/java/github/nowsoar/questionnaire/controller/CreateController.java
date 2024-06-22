package github.nowsoar.questionnaire.controller;

import github.nowsoar.questionnaire.service.CreateService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@RestController
@RequestMapping("/api")
public class CreateController {

    @Autowired
    private CreateService createService;

    @GetMapping("/createQuestionnaire")
    public String createQuestionnaire(String username) {
        if (username == null || username.equals("")) {
            username = "admin";
        }
        return createService.createQuestionnaire(username);
    }

    @GetMapping("/getQuestionList")
    public String getQuestionList(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionList(questionnaireId);
    }

    @GetMapping("/getQuestionnaireOutline")
    public String getQuestionnaireOutline(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionnaireOutline(questionnaireId);
    }
}
