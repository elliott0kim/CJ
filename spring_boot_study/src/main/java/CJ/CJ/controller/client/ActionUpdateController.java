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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ActionUpdateController {
    @Autowired
    private MessageComponent messageComponent;

    @Autowired
    private MessageCodeAndResDto response;

    @Autowired
    ActionUpdateService actionUpdateService;

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
