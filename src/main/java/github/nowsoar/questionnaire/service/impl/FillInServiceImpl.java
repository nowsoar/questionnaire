package github.nowsoar.questionnaire.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import github.nowsoar.questionnaire.entity.Answer;
import github.nowsoar.questionnaire.entity.Questionnaire;
import github.nowsoar.questionnaire.entity.QuestionnaireIp;
import github.nowsoar.questionnaire.mapper.AnswerMapper;
import github.nowsoar.questionnaire.mapper.QuestionnaireIpMapper;
import github.nowsoar.questionnaire.mapper.QuestionnaireMapper;
import github.nowsoar.questionnaire.service.FillInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/23
 */
@Service
public class FillInServiceImpl implements FillInService {

    @Autowired
    private QuestionnaireIpMapper questionnaireIpMapper;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private AnswerMapper answerMapper;

    private Gson gson = new Gson();

    @Override
    public Boolean checkAlreadySubmit(Integer questionnaireId, String ip) {
        int count = questionnaireIpMapper.findByQuestionnaireIdAndIp(questionnaireId, ip);
        if (count > 0) {
            return true;//已经提交过
        } else {
            return false;
        }
    }

    @Override
    public void submitAnswer(Integer questionnaireId, String answerListJson, String ip) {
        //如果问卷关闭，直接返回
        Questionnaire questionnaire = questionnaireMapper.findByQuestionnaireId(questionnaireId);
        if ("closed".equals(questionnaire.getStatus())) {
            return ;
        }
        //记录ip
        QuestionnaireIp questionnaireIp = new QuestionnaireIp();
        questionnaireIp.setIp(ip);
        questionnaireIp.setQuestionnaireId(questionnaireId);
        questionnaireIpMapper.insert(questionnaireIp);
        //问卷填写人数+1
        if (questionnaire.getFillCount() == null) {
            questionnaire.setFillCount(0);
        }
        questionnaire.setFillCount(questionnaire.getFillCount() + 1);
        questionnaireMapper.save(questionnaire);
        JsonArray answerListArray = gson.fromJson(answerListJson, JsonArray.class);
        for (JsonElement oneAnswer : answerListArray) {
            Answer answer = new Answer();
            answer.setIp(ip);
            answer.setFillTime(new Date());
            JsonObject oneAnswerObj = oneAnswer.getAsJsonObject();
            answer.setQuestionId(oneAnswerObj.get("questionId").getAsInt());
            answer.setQuestionTitle(oneAnswerObj.get("questionTitle").getAsString());
            String questionType = oneAnswerObj.get("questionType").getAsString();
            answer.setQuestionType(questionType);
            //根据不同问题类型获得并设置参数
            if ("single_check".equals(questionType)) {
                answer.setWriteValue(oneAnswerObj.get("answerSingleCheck").getAsString());
            } else if ("multi_check".equals(questionType)) {
                answer.setWriteValue(oneAnswerObj.get("answerMultiCheck").getAsString());
            } else if ("single_line_text".equals(questionType)) {
                answer.setWriteValue(oneAnswerObj.get("answerText").getAsString());
            } else if ("multi_line_text".equals(questionType)) {
                answer.setWriteValue(oneAnswerObj.get("answerText").getAsString());
            } else if ("number".equals(questionType)) {
                answer.setWriteValue(oneAnswerObj.get("answerNumber").getAsString());
            } else if ("grade".equals(questionType)) {
                answer.setWriteValue(oneAnswerObj.get("answerGrade").getAsString());
            } else if ("date".equals(questionType)) {
                answer.setWriteValue(oneAnswerObj.get("answerDate").getAsString());
            }
            answerMapper.insert(answer);
        }
    }
}
