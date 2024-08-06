package CJ.CJ.mapper.client;

import CJ.CJ.dto.client.History.ReportHistoryResDto;
import CJ.CJ.dto.client.History.WorkNowResDto;
import CJ.CJ.dto.client.History.WorkStatusDto;
import CJ.CJ.dto.client.user.UserinfoNoPasswordLevelDto;
import org.apache.ibatis.annotations.Mapper;
import CJ.CJ.dto.client.user.UserInfoDto;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;

@Mapper
@Qualifier("primarySqlSessionTemplate")
public interface UserInfoMapper {

    List<ReportHistoryResDto> findUserInfoForReportHistory(HashMap<String, Object> paramMap);
    List<WorkNowResDto> findUserInfoByWorkNow();
    short findLevelByUserId(HashMap<String, Object> paramMap);
    int checkFistMember();
    UserinfoNoPasswordLevelDto findUserInfoByUserIdNoPassword(HashMap<String, Object> paramMap);

    void updateUserInfoThreshold(HashMap<String, Object> paramMap);
    void updateActionByUserId(HashMap<String, Object> paramMap);
    void updateWorkNowByUserId(HashMap<String, Object> paramMap);
    WorkStatusDto getWorkNowByUserId(HashMap<String, Object> paramMap);
    UserInfoDto findUserInfoByUserId(HashMap<String, Object> paramMap);
    void registerUserInfo(HashMap<String, Object> paramMap);

}
