package CJ.CJ.dto.client.History;

import CJ.CJ.dto.client.user.UserinfoNoPasswordLevelDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportHistoryResDto extends UserinfoNoPasswordLevelDto {
    private short reportHeartRate;
    private String reportDateTime;
    private String action;
    private double locationXPos;
    private double locationYPos;
}
