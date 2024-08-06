package CJ.CJ.mapper.client;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Blob;
import java.util.HashMap;

@Mapper
@Qualifier("primarySqlSessionTemplate")
public interface HeartRateHistoryMapper {
    HashMap<String, Object> getByteArray(HashMap<String, Object> paramMap);
    void insertReportHistoryByUserId(HashMap<String, Object> paramMap);
}
