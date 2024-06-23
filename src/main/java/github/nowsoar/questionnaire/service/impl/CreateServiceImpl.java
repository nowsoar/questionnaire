package github.nowsoar.questionnaire.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import github.nowsoar.questionnaire.entity.Question;
import github.nowsoar.questionnaire.entity.Questionnaire;
import github.nowsoar.questionnaire.mapper.QuestionMapper;
import github.nowsoar.questionnaire.mapper.QuestionnaireMapper;
import github.nowsoar.questionnaire.service.CreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@Service
public class CreateServiceImpl implements CreateService {


    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private QuestionMapper questionMapper;

    private Gson gson = new Gson();

    private final Integer questionIdDigit = 1000;

    @Override
    public String createQuestionnaire(String username) {
        Questionnaire newQuestionnaire = new Questionnaire();
        newQuestionnaire.setUsername(username);
        newQuestionnaire.setCreateTime(new Date());
        newQuestionnaire.setStatus("editing");
        newQuestionnaire.setTitle("请输入标题");
        newQuestionnaire.setDescription("请输入描述");
        questionnaireMapper.insert(newQuestionnaire);//insert之后id在对象已设置值
        JsonObject res = new JsonObject();
        res.addProperty("id", newQuestionnaire.getQuestionnaireId());
        return gson.toJson(res);
    }

    @Override
    public String getQuestionList(Integer questionnaireId) {
        JsonArray resList = new JsonArray();
        List<Question> questionList = questionMapper.findAllByQuestionnaireId(questionnaireId);
        for (Question question : questionList) {
            JsonObject oneRes = gson.fromJson(gson.toJson(question), JsonObject.class);
            oneRes.addProperty("isBoxSelected", false);
            oneRes.addProperty("questionTitle", question.getQuestionTitle());
            oneRes.addProperty("questionDescription", question.getQuestionDescription());
            oneRes.addProperty("questionIndex", question.getQuestionId());
            oneRes.addProperty("questionNullable", question.getQuestionNullable());
            oneRes.addProperty("questionType", question.getQuestionType());
            JsonObject temp = gson.fromJson(question.getDetails(), JsonObject.class);
            processDetails(oneRes, temp);
            resList.add(oneRes);
        }
        JsonObject res = new JsonObject();
        res.add("questionList", resList);
        return gson.toJson(res);
    }

    @Override
    public String getQuestionnaireOutline(Integer questionnaireId) {
        JsonObject res = new JsonObject();
        Questionnaire questionnaire = questionnaireMapper.findByQuestionnaireId(questionnaireId);
        res.add("questionnaire", gson.fromJson(gson.toJson(questionnaire), JsonObject.class));
        return gson.toJson(res);
    }

    @Override
    public String saveOneQuestion(String question, Integer questionnaireId) {
        JsonObject temp = gson.fromJson(question, JsonObject.class);
        saveOneQuestion(temp, questionnaireId);
        return null;
    }

    @Override
    public String saveQuestionnaire(String questionnaire, String questionList) {
        JsonObject questionnaireObj = gson.fromJson(questionnaire, JsonObject.class);
        JsonArray questionListArray = gson.fromJson(questionList, JsonArray.class);
        int questionnaireId = questionnaireObj.get("questionnaireId").getAsInt();
        saveQuestionnaireOutline(questionnaire);
        for (JsonElement questionJson : questionListArray) {
            JsonObject temp = questionJson.getAsJsonObject();
            saveOneQuestion(temp, questionnaireId);
        }
        return null;
    }

    @Override
    public String saveQuestionnaireOutline(String questionnaire) {
        JsonObject questionnaireObj = gson.fromJson(questionnaire, JsonObject.class);
        Questionnaire questionnaireDB = questionnaireMapper.findByQuestionnaireId(questionnaireObj.get("questionnaireId").getAsInt());
        questionnaireDB.setDescription(questionnaireObj.get("questionnaireDescription").getAsString());
        questionnaireDB.setTitle(questionnaireObj.get("questionnaireTitle").getAsString());
        questionnaireMapper.update(questionnaireDB);
        return null;
    }

    private void processDetails(JsonObject oneRes, JsonObject temp) {
        if (temp != null) {
            oneRes.add("questionOptions", temp.get("questionOptions").getAsJsonArray());//add可以添加JsonObject，JsonArray等；addProperty用来添加普通属性
            oneRes.add("frontOptions", temp.get("frontOptions").getAsJsonArray());//add可以添加JsonObject，JsonArray等；addProperty用来添加普通属性
            oneRes.addProperty("frontChoose", temp.get("frontChoose").getAsBoolean());
            oneRes.addProperty("numberType", temp.get("numberType").getAsString());
            oneRes.addProperty("defaultNumber", temp.get("defaultNumber").getAsInt());
            oneRes.addProperty("gradeMax", temp.get("gradeMax").getAsInt());
            oneRes.addProperty("date", temp.get("date").getAsString());
            oneRes.addProperty("textDescription", temp.get("textDescription").getAsString());
        }
    }

    private void saveOneQuestion(JsonObject temp, Integer questionnaireId) {
        Question question = new Question();
        question.setQuestionnaireId(questionnaireId);
        question.setQuestionDescription(temp.get("questionDescription").getAsString());
        question.setQuestionTitle(temp.get("questionTitle").getAsString());
        question.setQuestionId(questionnaireId*questionIdDigit + temp.get("questionIndex").getAsInt());
        question.setQuestionType(temp.get("questionType").getAsString());
        question.setQuestionNullable(temp.get("questionNullable").getAsBoolean());
        JsonObject otherJson = new JsonObject();
        processDetails(otherJson, temp);
        question.setDetails(otherJson.toString());
        questionMapper.insert(question);
    }
}
