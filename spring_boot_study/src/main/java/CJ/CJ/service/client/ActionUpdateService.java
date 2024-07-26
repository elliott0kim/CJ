package CJ.CJ.service.client;


import CJ.CJ.dto.client.Action.ActionReqDto;
import CJ.CJ.dto.client.History.ReceiveReportReqDto;
import CJ.CJ.dto.client.user.UserInfoDto;
import CJ.CJ.mapper.client.HeartRateHistoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import CJ.CJ.mapper.client.UserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
@Slf4j
public class ActionUpdateService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    HeartRateHistoryMapper heartRateHistoryMapper;

    @Transactional
    public void actionUpdateService(ActionReqDto actionReqDto)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",actionReqDto.getUserId());
        paramMap.put("reportDateTime", actionReqDto.getReportDateTime());
        paramMap.put("action",actionReqDto.getAction());
        userInfoMapper.updateActionByUserId(paramMap);
    }

    @Transactional
    public void workNowUpdateService(String userId)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        userInfoMapper.updateWorkNowByUserId(paramMap);
    }

    @Transactional
    public void receiveReportService(String userId, ReceiveReportReqDto receiveReportReqDto)
    {
        // 현재 시각 얻기
        LocalDateTime now = LocalDateTime.now();

        // MySQL DATETIME 형식으로 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        UserInfoDto userInfoDto = new UserInfoDto();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        userInfoDto = userInfoMapper.findUserInfoById(paramMap);

        paramMap.put("threshold", userInfoDto.getThreshold());
        paramMap.put("reportHeartRate",receiveReportReqDto.getReportHeartRate());
        paramMap.put("reportDateTime",formattedDateTime);
        paramMap.put("action", 0);
        paramMap.put("locationXPos",receiveReportReqDto.getLocationXPos());
        paramMap.put("locationYPos",receiveReportReqDto.getLocationYPos());
        heartRateHistoryMapper.insertReportHistoryByUserId(paramMap);
    }
}
