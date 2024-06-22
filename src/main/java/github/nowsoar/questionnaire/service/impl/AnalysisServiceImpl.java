package github.nowsoar.questionnaire.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import github.nowsoar.questionnaire.entity.Questionnaire;
import github.nowsoar.questionnaire.mapper.QuestionnaireMapper;
import github.nowsoar.questionnaire.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    private Gson gson = new Gson();

    @Override
    public String getQuestionnairesByUsername(String username) {
        JsonObject res = new JsonObject();
        List<Questionnaire> questionnaires =  questionnaireMapper.findAllByUsername(username);
        res.add("questionnaires", gson.fromJson(gson.toJson(questionnaires), JsonArray.class));
        return gson.toJson(res);
    }
}
