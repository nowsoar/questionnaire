package github.nowsoar.questionnaire.mapper;

import github.nowsoar.questionnaire.entity.Question;
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
public interface QuestionMapper {

    List<Question> findAllByQuestionnaireId(Integer questionnaireId);

    void insert(Question question);

    void deleteByQuestionnaireId(Integer questionnaireId);
}
