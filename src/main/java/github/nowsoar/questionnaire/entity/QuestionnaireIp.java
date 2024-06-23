package github.nowsoar.questionnaire.entity;

import lombok.Data;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/23
 */
@Data
public class QuestionnaireIp {

    private Integer id;

    private Integer questionnaireId;

    private String ip;
}
