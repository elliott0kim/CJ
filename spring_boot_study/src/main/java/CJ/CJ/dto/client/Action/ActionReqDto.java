package CJ.CJ.dto.client.Action;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionReqDto {
    private String userId;
    private String reportDateTime;
    private String action;
}
