package github.nowsoar.questionnaire.controller;

import github.nowsoar.questionnaire.service.AnalysisService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@RestController
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/api/getQuestionnaires")
    public String getQuestionnaires(@Param("username") String username) {
        if (username == null || username.equals("")) {
            username = "admin";
        }
        return analysisService.getQuestionnairesByUsername(username);
    }
}
