package CJ.CJ.mapper.client;

import java.sql.Blob;
import java.util.HashMap;

public interface HeartRateHistoryMapper {
    HashMap<String, Object> getByteArray(HashMap<String, Object> paramMap);
    void insertReportHistoryByUserId(HashMap<String, Object> paramMap);
}
