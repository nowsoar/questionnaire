package github.nowsoar.questionnaire.mapper;

import github.nowsoar.questionnaire.entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/24
 */
@Mapper
@Repository
public interface AnswerMapper {

    void insert(Answer answer);

    List<Answer> findByQuestionId(Integer questionId);
}
