package github.nowsoar.questionnaire.mapper;

import github.nowsoar.questionnaire.entity.Questionnaire;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@Mapper
@Repository
public interface QuestionnaireMapper {

    List<Questionnaire> findAllByUsername(String username);

    void insert(Questionnaire questionnaire);

    Questionnaire findByQuestionnaireId(Integer questionnaireId);

    void update(Questionnaire questionnaire);
}
