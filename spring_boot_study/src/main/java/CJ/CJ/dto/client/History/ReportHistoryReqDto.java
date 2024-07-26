package CJ.CJ.dto.client.History;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportHistoryReqDto {
    private String userId;
    private String startDate;
    private String endDate;
    private String action;
}