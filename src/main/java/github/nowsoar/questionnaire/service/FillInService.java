package github.nowsoar.questionnaire.service;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/23
 */
public interface FillInService {

    public Boolean checkAlreadySubmit(Integer questionnaireId, String ip);

    public void submitAnswer(Integer questionnaireId, String answerListJson, String ip);
}
