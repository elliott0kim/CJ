package CJ.CJ.mapper.client;

import CJ.CJ.dto.client.History.ReportHistoryResDto;
import CJ.CJ.dto.client.History.WorkNowResDto;
import CJ.CJ.dto.client.user.UserinfoNoPasswordLevelDto;
import org.apache.ibatis.annotations.Mapper;
import CJ.CJ.dto.client.user.UserInfoDto;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserInfoMapper {

    List<ReportHistoryResDto> findUserInfoForReportHistory(HashMap<String, Object> paramMap);
    List<WorkNowResDto> findUserInfoByWorkNow();
    short findLevelByUserId(HashMap<String, Object> paramMap);
    int checkFistMember();
    List<UserinfoNoPasswordLevelDto> findUserInfoByWorkDate(HashMap<String, Object> paramMap);

    void updateUserInfoThreshold(HashMap<String, Object> paramMap);
    void updateActionByUserId(HashMap<String, Object> paramMap);
    void updateWorkNowByUserId(HashMap<String, Object> paramMap);
    UserInfoDto findUserInfoById(HashMap<String, Object> paramMap);
    void registerUserInfo(HashMap<String, Object> paramMap);

}
