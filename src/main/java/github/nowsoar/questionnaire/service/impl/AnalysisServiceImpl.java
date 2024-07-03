package github.nowsoar.questionnaire.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import github.nowsoar.questionnaire.entity.Answer;
import github.nowsoar.questionnaire.entity.Question;
import github.nowsoar.questionnaire.entity.Questionnaire;
import github.nowsoar.questionnaire.mapper.AnswerMapper;
import github.nowsoar.questionnaire.mapper.QuestionMapper;
import github.nowsoar.questionnaire.mapper.QuestionnaireMapper;
import github.nowsoar.questionnaire.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private QuestionMapper questionMapper;

    private Gson gson = new Gson();

    @Override
    public String getQuestionnairesByUsername(String username) {
        JsonObject res = new JsonObject();
        List<Questionnaire> questionnaires =  questionnaireMapper.findAllByUsername(username);
        res.add("questionnaires", gson.fromJson(gson.toJson(questionnaires), JsonArray.class));
        return gson.toJson(res);
    }

    //使用事务注解
    @Transactional
    @Override
    public String getQuestionValueList(Integer questionId) {
        List<Answer> answerList = answerMapper.findByQuestionId(questionId);
        Question question = questionMapper.findByQuestionId(questionId);
        String questionType = question.getQuestionType();
        if ("single_check".equals(questionType)) {
            Map<String, Integer> resValueMap = new HashMap<>();
            JsonObject temp = gson.fromJson(question.getDetails(), JsonObject.class);
            JsonArray questionOptions = temp.get("questionOptions").getAsJsonArray();
            for (JsonElement questionOptionJson : questionOptions) {
                String questionOption = questionOptionJson.getAsString();
                resValueMap.put(questionOption, 0);
            }
            for (Answer answer : answerList) {
                String writeValue = answer.getWriteValue();
                resValueMap.put(writeValue, resValueMap.get(writeValue) + 1);//对频率进行计数
            }
            return gson.toJson(resValueMap);
        } else if (questionType.equals("multi_check")) {
            Map<String, Integer> resValueMap = new HashMap<>();
            JsonObject temp = gson.fromJson(question.getDetails(), JsonObject.class);
            JsonArray questionOptions = temp.get("questionOptions").getAsJsonArray();
            for (JsonElement questionOptionJson : questionOptions) {
                String questionOption = questionOptionJson.getAsString();
                resValueMap.put(questionOption, 0);
            }
            for (Answer answer : answerList) {
                JsonArray valueList = gson.fromJson(answer.getWriteValue(), JsonArray.class);
                for (JsonElement value : valueList) {
                    String valueAsString = value.getAsString();
                    resValueMap.put(valueAsString, resValueMap.get(valueAsString) + 1);
                }
            }
            return gson.toJson(resValueMap);
        } else if (questionType.equals("number") || questionType.equals("grade")) {
            Map<String, Double> resValueMap = new HashMap<>();
            List<Double> valueList = new ArrayList<>();
            Double sum = 0d;
            for (Answer answer : answerList) {
                Double value = gson.fromJson(answer.getWriteValue(), Double.class);
                valueList.add(value);
                sum += value;
                Collections.sort(valueList);
            }
            if (valueList.size() == 0) {
                resValueMap.put("最大值", 0d);
                resValueMap.put("最小值", 0d);
                resValueMap.put("平均值", 0d);
                resValueMap.put("中位数", 0d);
            } else {
                resValueMap.put("最大值", valueList.get(valueList.size() - 1));
                resValueMap.put("最小值", valueList.get(0));
                resValueMap.put("平均值", sum / valueList.size());
                resValueMap.put("中位数", valueList.get(valueList.size() / 2));
            }
            return gson.toJson(resValueMap);
        }
        return null;
    }

    @Override
    public String getWriteValue(Integer questionId) {
        return gson.toJson(answerMapper.findByQuestionId(questionId));
    }
}
