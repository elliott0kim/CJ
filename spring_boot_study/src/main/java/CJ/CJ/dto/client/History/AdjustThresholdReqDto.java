package CJ.CJ.dto.client.History;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdjustThresholdReqDto {
    private String userId;
    private String threshold;
}
