package github.nowsoar.questionnaire.entity;

import lombok.Data;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@Data
public class Question {

    private Integer questionId;

    private Integer questionnaireId;

    private String questionTitle;

    private String questionDescription;

    private Boolean questionNullable;

    private String questionType;

    private String details;
}
