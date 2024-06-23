package github.nowsoar.questionnaire.service;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
public interface CreateService {

    public String createQuestionnaire(String username);

    public String getQuestionList(Integer questionnaireId);

    public String getQuestionnaireOutline(Integer questionnaireId);

    public String saveOneQuestion(String question, Integer questionnaireId);

    public String saveQuestionnaire(String questionnaire, String questionList);

    public String saveQuestionnaireOutline(String questionnaire);

    public String releaseQuestionnaire(Integer questionnaireId);

    public String deleteQuestionnaire(Integer questionnaireId);
}
