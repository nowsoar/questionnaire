package github.nowsoar.questionnaire.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/23
 */
@Mapper
@Repository
public interface QuestionnaireIpMapper {

    Integer findByQuestionnaireIdAndIp(Integer questionnaireId, String ip);
}
