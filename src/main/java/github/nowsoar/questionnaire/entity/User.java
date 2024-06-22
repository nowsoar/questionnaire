package github.nowsoar.questionnaire.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@Data
public class User {

    private Integer userId;

    private String username;

    private String password;

    private String email;

    private String phoneNum;

    private String status;

    private String randomCode;

    private Date createTime;

    private Date lastLoginTime;
}
