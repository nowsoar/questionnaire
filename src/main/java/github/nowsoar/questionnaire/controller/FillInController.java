package github.nowsoar.questionnaire.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import github.nowsoar.questionnaire.service.CreateService;
import github.nowsoar.questionnaire.service.FillInService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/23
 */
@RestController
@RequestMapping("/api/fillin")
public class FillInController {

    @Autowired
    private CreateService createService;

    @Autowired
    private FillInService fillInService;

    private Gson gson = new Gson();

    @GetMapping("/getQuestionnaireOutline")
    public String getQuestionnaireOutline(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionnaireOutline(questionnaireId);
    }

    @GetMapping("/getQuestionList")
    public String getQuestionList(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionList(questionnaireId);
    }

    @GetMapping("/checkAlreadySubmit")
    public Boolean checkAlreadySubmit(@Param("questionnaireId") Integer questionnaireId, @Param("ip") String ip) {
        return fillInService.checkAlreadySubmit(questionnaireId, ip);
    }

    @PostMapping("/submitAnswer")
    public String submitAnswer(@Param("questionnaireId") Integer questionnaireId, @RequestBody String answer) {
        String answerListJson = gson.fromJson(answer, JsonObject.class).get("answerList").toString();
        String ip = gson.fromJson(answer, JsonObject.class).get("ip").getAsString();
        if (!fillInService.checkAlreadySubmit(questionnaireId, ip)) {
            fillInService.submitAnswer(questionnaireId, answerListJson, ip);
        }
        return "";
    }
}
