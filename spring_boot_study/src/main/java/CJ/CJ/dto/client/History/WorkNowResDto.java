package CJ.CJ.dto.client.History;

import CJ.CJ.dto.client.user.UserinfoNoPasswordLevelDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WorkNowResDto extends UserinfoNoPasswordLevelDto {

    private short lastHeartRate;
}
