package CJ.CJ.controller.client;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import CJ.CJ.config.jwt.JwtUtil;
import CJ.CJ.config.message.MessageComponent;
import CJ.CJ.dto.client.*;
import CJ.CJ.dto.client.auth.*;
import CJ.CJ.dto.client.user.UserInfoDto;
import CJ.CJ.service.client.AuthService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @Autowired
    private MessageComponent messageComponent;

    @Autowired
    private MessageCodeAndResDto response;

    @Autowired
    private final JwtUtil jwtUtil;


    @PostMapping("/login")
    public MessageCodeAndResDto logIn(@Valid @RequestBody LoginReqDto loginReqDto)
    {
        authService.checkPassword(loginReqDto);
        LoginResDto loginResDto = new LoginResDto();
        loginResDto.setLevel(authService.getLevel(loginReqDto.getUserId()));
        loginResDto.setAccessToken(this.authService.getAccessToken(loginReqDto.getUserId()));

        response.setMessage(messageComponent.getSUCCESS());
        response.setData(loginResDto);
        return response;
    }


    @PostMapping("/register")
    public MessageCodeAndResDto register(@Valid @RequestBody RegisterDto registerDto)
    {
        System.out.println(registerDto.toString());
        authService.register(registerDto);
        response.setMessage(messageComponent.getSUCCESS());
        response.setData(null);
        return response;
    }

    @GetMapping("/register/duplication")
    public MessageCodeAndResDto checkIdDuplication(@RequestParam String userId)
    {
        CheckIdDuplicationReqDto checkIdDuplicationReqDto = new CheckIdDuplicationReqDto();
        checkIdDuplicationReqDto.setUserId(userId);
        authService.checkIdDuplication(checkIdDuplicationReqDto);
        response.setMessage(messageComponent.getSUCCESS());
        response.setData(null);
        checkIdDuplicationReqDto = null;
        return response;
    }

}
