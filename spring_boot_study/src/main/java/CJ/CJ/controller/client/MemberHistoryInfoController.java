package CJ.CJ.controller.client;

import CJ.CJ.config.message.MessageComponent;
import CJ.CJ.dto.client.History.AdjustThresholdReqDto;
import CJ.CJ.dto.client.History.ReportHistoryReqDto;
import CJ.CJ.dto.client.History.TotalMemberReqDto;
import CJ.CJ.dto.client.History.WorkNowResDto;
import CJ.CJ.dto.client.MessageCodeAndResDto;
import CJ.CJ.service.client.MemberHistoryInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class MemberHistoryInfoController {
    @Autowired
    private MessageComponent messageComponent;

    @Autowired
    private MessageCodeAndResDto response;

    @Autowired
    MemberHistoryInfoService memberHistoryInfoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    @Value("${boost.beast.ip}")
    private String boostBeastIp;

    @Value("${boost.beast.port}")
    private String boostBeastPort;

    @Value("${boost.beast.endpoint.lastHeartRate}")
    private String boostBeastEndPoint;


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
    public MessageCodeAndResDto workNow() throws JsonProcessingException {
        List<WorkNowResDto> workNowMember = memberHistoryInfoService.workNow();

        String baseUrl = "http://" + boostBeastIp + ":" + boostBeastPort + boostBeastEndPoint;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);

        if (workNowMember != null)
        {
            List<String> userIdList = new ArrayList<>();
            for (int idx = 0 ; idx < workNowMember.size() ; idx++)
            {
                userIdList.add(workNowMember.get(idx).getUserId());
            }
            builder.queryParam("userId", userIdList.toArray(new String[0]));
        }

        String url = builder.toUriString();
        String jsonResponse = restTemplate.getForObject(url, String.class);
        Map<String, Object> jsonResponseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>(){});

        // "data" 필드 추출
        Map<String, Integer> userIdHeartRateMap = (Map<String, Integer>) jsonResponseMap.get("data");

        for (int idx = 0 ; idx < workNowMember.size() ; idx++)
        {
            workNowMember.get(idx).setLastHeartRate(
                    userIdHeartRateMap.get(
                            workNowMember.get(idx).getUserId()
                    ).shortValue());
        }

        response.setMessage(messageComponent.getSUCCESS());
        response.setData(workNowMember);
        return response;
    }

    @GetMapping("/member")
    public MessageCodeAndResDto member(@RequestParam String userId, String startWorkDate, String endWorkDate)
    {
        TotalMemberReqDto totalMemberReqDto = new TotalMemberReqDto();
        if (!userId.equals("NULL") && !userId.equals("null") && userId != null)
        {
            totalMemberReqDto.setUserId(userId);
        }
        totalMemberReqDto.setStartWorkDate(startWorkDate);
        totalMemberReqDto.setEndWorkDate(endWorkDate);

        memberHistoryInfoService.member(totalMemberReqDto);

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
