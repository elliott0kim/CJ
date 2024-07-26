package CJ.CJ.dto.client.History;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TotalMemberReqDto {
    private String userId;
    private String startWorkDate;
    private String endWorkDate;
}
