package CJ.CJ.dto.client.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginReqDto {
    private String userId;
    private String password;
}
