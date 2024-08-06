package CJ.CJ.service.client;

import CJ.CJ.dto.client.History.*;
import CJ.CJ.dto.client.user.UserinfoNoPasswordLevelDto;
import CJ.CJ.mapper.client.HeartRateHistoryMapper;
import CJ.CJ.mapper.client.UserInfoMapper;
import CJ.CJ.mapper.server.FindUserIdByWorkDateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class MemberHistoryInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    FindUserIdByWorkDateMapper findUserIdByWorkDateMapper;


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
        return workNowList;
    }

    @Transactional
    public List<UserinfoNoPasswordLevelDto> member(TotalMemberReqDto totalMemberReqDto)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        List<UserinfoNoPasswordLevelDto> userinfoNoPasswordLevelDtoList = new ArrayList<>();
        if (totalMemberReqDto.getUserId() != null && !totalMemberReqDto.getUserId().equals("NULL") && !totalMemberReqDto.getUserId().equals("null"))
        {
            paramMap.put("userId",totalMemberReqDto.getUserId());
            UserinfoNoPasswordLevelDto userinfoNoPasswordLevelDto = userInfoMapper.findUserInfoByUserIdNoPassword(paramMap);
            userinfoNoPasswordLevelDtoList.add(userinfoNoPasswordLevelDto);
        }
        else
        {
            paramMap.put("startWorkDate",totalMemberReqDto.getStartWorkDate());
            paramMap.put("endWorkDate",totalMemberReqDto.getEndWorkDate()); //이거 신고 날짜조회하는데 신고 들어간 시작 일자 맞지?

            List<String> userIdList = findUserIdByWorkDateMapper.findUserInfoByWorkDate(paramMap);

            for (int idx = 0 ; idx < userIdList.size(); idx++)
            {
                paramMap.clear();
                paramMap.put("userId", userIdList.get(idx));
                UserinfoNoPasswordLevelDto userinfoNoPasswordLevelDto = userInfoMapper.findUserInfoByUserIdNoPassword(paramMap);
                userinfoNoPasswordLevelDtoList.add(userinfoNoPasswordLevelDto);
            }
        }

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
