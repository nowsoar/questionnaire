package github.nowsoar.questionnaire.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@Data
public class Questionnaire {

    private Integer questionnaireId;

    private String username;

    private Date createTime;

    private Date startTime;

    private Date endTime;

    private String status;

    private String title;

    private Integer fillCount;

    private String description;
}
