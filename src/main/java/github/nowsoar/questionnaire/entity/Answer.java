package github.nowsoar.questionnaire.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/24
 */
@Data
public class Answer {

    private Integer answerId;

    private Integer questionId;

    private String questionTitle;

    private String questionType;

    private String writeValue;

    private String ip;

    private Date fillTime;
}
