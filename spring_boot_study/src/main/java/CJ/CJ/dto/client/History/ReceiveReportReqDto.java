package CJ.CJ.dto.client.History;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReceiveReportReqDto
{
    private short reportHeartRate;
    private double locationXPos;
    private double locationYPos;
}
