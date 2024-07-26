package CJ.CJ.dto.client.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserinfoNoPasswordLevelDto {
    protected String userId;
    protected String name;
    protected String phoneNum;
    protected short gender;
    protected String birth;
    protected short height;
    protected short weight;
    protected short threshold;
    protected short reportCountTotal;
    protected short reportCountToday;
}
