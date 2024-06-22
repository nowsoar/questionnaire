package github.nowsoar.questionnaire.mapper;

import github.nowsoar.questionnaire.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: ZKP
 * @time: 2024/6/22
 */
@Mapper
@Repository
public interface UserMapper {

    public User findByUsername(String username);

    public void update(User user);

    public void insert(User user);
}
