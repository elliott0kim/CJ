package CJ.CJ.mapper.server;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;

@Mapper
@Qualifier("secondarySqlSessionTemplate")
public interface FindUserIdByWorkDateMapper {
    List<String> findUserInfoByWorkDate(HashMap<String, Object> paramMap);
}
