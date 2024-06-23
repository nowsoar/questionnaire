package github.nowsoar.questionnaire.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import github.nowsoar.questionnaire.service.CreateService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    private Gson gson = new Gson();

    //创建初始化问卷
    @GetMapping("/createQuestionnaire")
    public String createQuestionnaire(String username) {
        if (username == null || username.equals("")) {
            username = "admin";
        }
        return createService.createQuestionnaire(username);
    }

    //创建初始化问卷时获得问卷列表
    @GetMapping("/getQuestionList")
    public String getQuestionList(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionList(questionnaireId);
    }

    //创建初始化问卷时获得问卷内容,Outline在这里专指问卷摘要表
    @GetMapping("/getQuestionnaireOutline")
    public String getQuestionnaireOutline(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionnaireOutline(questionnaireId);
    }

    //保存单个问题
    @PostMapping("/saveOneQuestion")
    public String saveOneQuestion(@RequestBody String oneQuestion, @Param("questionnaireId") Integer questionnaireId) {
        return createService.saveOneQuestion(gson.fromJson(oneQuestion, JsonObject.class).get("question").toString(),
                questionnaireId);
    }

    //保存问卷
    @PostMapping("/saveQuestionnaire")
    public String saveQuestionnaire(@RequestBody String body) {
        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);
        String questionList = jsonObject.get("questionList").toString();
        String questionnaire = jsonObject.get("questionnaire").toString();
        return createService.saveQuestionnaire(questionnaire, questionList);
    }
}
