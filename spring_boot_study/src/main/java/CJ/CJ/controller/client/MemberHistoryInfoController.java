package CJ.CJ.controller.client;

import CJ.CJ.config.message.MessageComponent;
import CJ.CJ.dto.client.History.AdjustThresholdReqDto;
import CJ.CJ.dto.client.History.ReportHistoryReqDto;
import CJ.CJ.dto.client.History.TotalMemberReqDto;
import CJ.CJ.dto.client.MessageCodeAndResDto;
import CJ.CJ.service.client.MemberHistoryInfoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MemberHistoryInfoController {
    @Autowired
    private MessageComponent messageComponent;

    @Autowired
    private MessageCodeAndResDto response;

    @Autowired
    MemberHistoryInfoService memberHistoryInfoService;

    @GetMapping("/reportHistory")
    public MessageCodeAndResDto reportHistory
            (@RequestParam String userId, String startDate, String endDate, String action)
    {
        ReportHistoryReqDto reportHistoryReqDto = new ReportHistoryReqDto();
        if (!userId.equals("NULL"))
        {
            reportHistoryReqDto.setUserId(userId);
        }

        if (!action.equals("NULL"))
        {
            reportHistoryReqDto.setAction(action);
        }

        reportHistoryReqDto.setStartDate(startDate);
        reportHistoryReqDto.setEndDate(endDate);

        response.setMessage(messageComponent.getSUCCESS());
        response.setData(memberHistoryInfoService.reportHistory(reportHistoryReqDto));
        return response;
    }

    @GetMapping("/workNow")
    public MessageCodeAndResDto workNow()
    {
        response.setMessage(messageComponent.getSUCCESS());
        response.setData(memberHistoryInfoService.workNow());
        return response;
    }

    @GetMapping("/member")
    public MessageCodeAndResDto member(@RequestParam String userId, String startWorkDate, String endWorkDate)
    {
        TotalMemberReqDto totalMemberReqDto = new TotalMemberReqDto();
        if (!userId.equals("NULL"))
        {
            totalMemberReqDto.setUserId(userId);
        }
        totalMemberReqDto.setStartWorkDate(startWorkDate);
        totalMemberReqDto.setEndWorkDate(endWorkDate);
        response.setMessage(messageComponent.getSUCCESS());
        response.setData(memberHistoryInfoService.member(totalMemberReqDto));
        return response;
    }


    @PostMapping("/adjustThreshold")
    public MessageCodeAndResDto adjustThreshold(@Valid @RequestBody AdjustThresholdReqDto adjustThresholdReqDto)
    {
        memberHistoryInfoService.adjustThreshold(adjustThresholdReqDto);
        response.setMessage(messageComponent.getSUCCESS());
        response.setData(null);
        return response;
    }
}
