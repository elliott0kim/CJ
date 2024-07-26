package CJ.CJ.dto.client.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoDto {
    private String userId;
    private String password;
    private String name;
    private String phoneNum;
    private short gender;
    private String birth;
    private short height;
    private short weight;
    private short threshold;
    private short level;
    private short reportCountTotal;
    private short reportCountToday;
}