package github.nowsoar.questionnaire.service;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
public interface AnalysisService {

    public String getQuestionnairesByUsername(String username);

    public String getQuestionValueList(Integer questionId);

    public String getWriteValue(Integer questionId);
}
