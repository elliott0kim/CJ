package CJ.CJ.dto.client.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDto {
    private String userId;
    private String password;
    private String name;
    private String phoneNum;
    private short gender;
    private String birth;
    private short height;
    private short weight;

    @Override
    public String toString() {
        return "RegisterDto{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", gender=" + gender +
                ", birth='" + birth + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
