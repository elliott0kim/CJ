package CJ.CJ.dto.client.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResDto {
    private String accessToken;
    private short level;
}
