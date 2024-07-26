package CJ.CJ.service.client;

import CJ.CJ.dto.client.History.*;
import CJ.CJ.dto.client.user.UserinfoNoPasswordLevelDto;
import CJ.CJ.mapper.client.HeartRateHistoryMapper;
import CJ.CJ.mapper.client.UserInfoMapper;
import CJ.CJ.service.StringToByteArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

//import static CJ.CJ.service.StringToByteArray.convertStringToByteArray;

@Service
@Slf4j
public class MemberHistoryInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    HeartRateHistoryMapper heartRateHistoryMapper;

    public byte[] base64ToByteArray(String base64EncodedData)
    {
        if (base64EncodedData != null) {
            return Base64.getDecoder().decode(base64EncodedData);
        } else {
            return null;
        }
    }

    @Transactional
    public List<ReportHistoryResDto> reportHistory(ReportHistoryReqDto reportHistoryReqDto)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        if (reportHistoryReqDto.getUserId() != null)
        {
            paramMap.put("userId",reportHistoryReqDto.getUserId());
        }
        paramMap.put("startDate",reportHistoryReqDto.getStartDate()); //이거 신고 날짜조회하는데 신고 들어간 시작 일자 맞지?
        paramMap.put("endDate",reportHistoryReqDto.getEndDate());
        if (reportHistoryReqDto.getAction() != null)
        {
            int intAction = Integer.parseInt(reportHistoryReqDto.getAction());
            paramMap.put("action",intAction);
        }

        List<ReportHistoryResDto> reportHistoryResDtoList = userInfoMapper.findUserInfoForReportHistory(paramMap);
        if (reportHistoryResDtoList == null)
        {
            throw new EmptyResultDataAccessException(0);
        }
        return reportHistoryResDtoList;
    }

    @Transactional
    public List<WorkNowResDto> workNow()
    {
        List<WorkNowResDto> workNowList = userInfoMapper.findUserInfoByWorkNow();
        if (workNowList == null)
        {
            throw new EmptyResultDataAccessException(0);
        }

        List<WorkNowResDto> workNowResDtoList = new ArrayList<>();
        for (int idx = 0 ; idx < workNowList.size() ; idx++)
        {
            WorkNowResDto workNowResDto = (WorkNowResDto) workNowList.get(idx);

            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("userId",workNowResDto.getUserId());
            try
            {
                HashMap<String, Object> heartRateHashMap = heartRateHistoryMapper.getByteArray(paramMap);
                int[] heartRateByteArray = StringToByteArray.blotToShortArray(heartRateHashMap);
                if (heartRateByteArray.length < 1)
                {
                    throw new EmptyResultDataAccessException(0);
                }
                short lastHeartRate = (short) heartRateByteArray[heartRateByteArray.length - 1];
                workNowResDto.setLastHeartRate((short) lastHeartRate);
                workNowResDtoList.add(workNowResDto);
            }
            catch (Exception e)
            {
                throw new RuntimeException(); // 전체 스택 트레이스를 출력하여 원인 예외를 확인
            }
        }
        return workNowResDtoList;
    }

    @Transactional
    public List<UserinfoNoPasswordLevelDto> member(TotalMemberReqDto totalMemberReqDto)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        if (totalMemberReqDto.getUserId() != null)
        {
            paramMap.put("userId",totalMemberReqDto.getUserId());
        }
        paramMap.put("startWorkDate",totalMemberReqDto.getStartWorkDate());
        paramMap.put("endWorkDate",totalMemberReqDto.getEndWorkDate()); //이거 신고 날짜조회하는데 신고 들어간 시작 일자 맞지?
        List<UserinfoNoPasswordLevelDto> userinfoNoPasswordLevelDtoList = userInfoMapper.findUserInfoByWorkDate(paramMap);
        if (userinfoNoPasswordLevelDtoList == null)
        {
            throw new EmptyResultDataAccessException(0);
        }
        return userinfoNoPasswordLevelDtoList;
    }

    @Transactional
    public void adjustThreshold(AdjustThresholdReqDto adjustThresholdReqDto)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",adjustThresholdReqDto.getUserId());
        paramMap.put("threshold",adjustThresholdReqDto.getThreshold()); //이거 신고 날짜조회하는데 신고 들어간 시작 일자 맞지?
        userInfoMapper.updateUserInfoThreshold(paramMap);
    }
}

//        enum CountMember
//        {
//            OnlyOneMember(1),
//            Others(2);
//            private final int value;
//            CountMember(int value) {
//                this.value = value;
//            }
//
//            public int getValue() {
//                return value;
//            }
//        }
//        int memberFlag;
//
//        if (reportHistoryReqDto.getUserId() == null)
//        {
//            memberFlag = CountMember.OnlyOneMember.getValue();
//        }
//        else
//        {
//            memberFlag = CountMember.Others.getValue();
//        }

//        else if (memberFlag == CountMember.OnlyOneMember.getValue())
//        {
//            if (reportHistoryResDtoList.size() == CountMember.OnlyOneMember.getValue())
//            {
//                throw new TooManyResultsException();
//            }
//        }
