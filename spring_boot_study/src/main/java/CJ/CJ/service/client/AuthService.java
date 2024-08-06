package CJ.CJ.service.client;

import CJ.CJ.dto.client.auth.CheckIdDuplicationReqDto;
import CJ.CJ.dto.client.auth.LoginReqDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import CJ.CJ.config.jwt.JwtUtil;
import CJ.CJ.config.message.MessageComponent;
import CJ.CJ.dto.client.auth.RegisterDto;
import CJ.CJ.dto.client.user.UserInfoDto;
import CJ.CJ.mapper.client.UserInfoMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    //private final PasswordEncoder encoder;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Transactional
    public void checkPassword(LoginReqDto loginReqDto)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",loginReqDto.getUserId());
        UserInfoDto userInfoDto = userInfoMapper.findUserInfoByUserId(paramMap);
        if (userInfoDto == null)
        {
            throw new EmptyResultDataAccessException(0);
        }
        if (!userInfoDto.getPassword().equals(loginReqDto.getPassword()))
        {
            throw new BadCredentialsException("invalid password");
        }
    }

    @Transactional
    public void register(RegisterDto registerDto)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", registerDto.getUserId());
        paramMap.put("password", registerDto.getPassword());
        paramMap.put("name", registerDto.getName());
        paramMap.put("phoneNum", registerDto.getPhoneNum());
        paramMap.put("gender", registerDto.getGender());
        paramMap.put("birth", registerDto.getBirth());
        paramMap.put("height", registerDto.getHeight());
        paramMap.put("weight", registerDto.getWeight());
        if (checkFistMember() == true)
        {
            paramMap.put("level", 1);
        }
        else
        {
            paramMap.put("level", 0);
        }
        //paramMap.put("level", 1); // 0 is Admin , 1 is Member, so do something for Admin register
        userInfoMapper.registerUserInfo(paramMap);
        System.out.println(paramMap);
    }

    @Transactional
    public boolean checkFistMember()
    {
        if (userInfoMapper.checkFistMember() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Transactional
    public void checkIdDuplication(CheckIdDuplicationReqDto checkIdDuplicationReqDto)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", checkIdDuplicationReqDto.getUserId());
        UserInfoDto userInfoDto = userInfoMapper.findUserInfoByUserId(paramMap);
        if (userInfoDto != null)
        {
            MessageComponent messageComponent = new MessageComponent();
            throw new TooManyResultsException(messageComponent.getDUPLICATED_NAME());
        }
    }

    @Transactional
    public short getLevel(String userId)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        short level = userInfoMapper.findLevelByUserId(paramMap);
        return level;
    }

    // for JWT Filter
    @Transactional
    public void checkExistUserById(String userId)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        UserInfoDto userInfoDto = userInfoMapper.findUserInfoByUserId(paramMap);
        if (userInfoDto == null)
        {
            throw new IllegalArgumentException();
        }
    }

    public String getAccessToken(String userId)
    {
        return jwtUtil.createAccessToken(userId);
    }

}
