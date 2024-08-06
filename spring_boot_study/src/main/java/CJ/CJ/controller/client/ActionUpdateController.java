package CJ.CJ.controller.client;

import CJ.CJ.config.jwt.JwtUtil;
import CJ.CJ.config.message.MessageComponent;
import CJ.CJ.dto.client.Action.ActionReqDto;
import CJ.CJ.dto.client.History.AdjustThresholdReqDto;
import CJ.CJ.dto.client.History.ReceiveReportReqDto;
import CJ.CJ.dto.client.MessageCodeAndResDto;
import CJ.CJ.dto.client.auth.CheckIdDuplicationReqDto;
import CJ.CJ.service.client.ActionUpdateService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Slf4j
public class ActionUpdateController {
    @Autowired
    private MessageComponent messageComponent;

    @Autowired
    private MessageCodeAndResDto response;

    @Autowired
    ActionUpdateService actionUpdateService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${boost.beast.ip}")
    private String boostBeastIp;

    @Value("${boost.beast.port}")
    private String boostBeastPort;

    @Value("${boost.beast.endpoint.workStatusOff}")
    private String boostBeastEndPoint;


    @PostMapping("/Action")
    public MessageCodeAndResDto action(@Valid @RequestBody ActionReqDto actionReqDto)
    {
        actionUpdateService.actionUpdateService(actionReqDto);
        response.setMessage(messageComponent.getSUCCESS());
        response.setData(null);
        return response;
    }

    @PostMapping("/updateWorkNow")
    public MessageCodeAndResDto workNowUpdateService(@Valid @RequestBody CheckIdDuplicationReqDto checkIdDuplicationReqDto)
    {
        // 지금 현재 근무중인지 리턴값으로 알려줌
        boolean isWorkNow = actionUpdateService.isWorkNowService(checkIdDuplicationReqDto.getUserId());
        // 지금 근무중이면 퇴근으로 바꿨으니까! 알려줘야지 부스트 비스트한테도!
        if (isWorkNow == true)
        {
            // 기본 URL 구성
            String baseUrl = "http://" + boostBeastIp + ":" + boostBeastPort + boostBeastEndPoint;

            // 요청 본문 데이터
            String requestBody = "{ \"userId\": \""+checkIdDuplicationReqDto.getUserId()+"\" }";

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HttpEntity 생성 (헤더와 본문 포함)
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // POST 요청 보내기
            String response = restTemplate.postForObject(baseUrl, entity, String.class);

            // do something for boost beast response... 근데 시간없다ㅠ
        }

        actionUpdateService.workNowUpdateService(checkIdDuplicationReqDto.getUserId());
        response.setMessage(messageComponent.getSUCCESS());
        response.setData(null);
        return response;
    }

    @PostMapping("/receiveReport")
    public MessageCodeAndResDto receiveReport(@RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ReceiveReportReqDto receiveReportReqDto)
    {
        String token = "";
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setMessage(messageComponent.getUNKNOWN_ERROR());
            response.setData(null);
            return response;
        }

        token =  authHeader.substring(7); // 'Bearer ' 부분을 제거한 후 토큰 반환

        String userId = JwtUtil.parseUserId(token);

        actionUpdateService.receiveReportService(userId, receiveReportReqDto);
        response.setMessage(messageComponent.getSUCCESS());
        response.setData(null);
        return response;
    }
}
