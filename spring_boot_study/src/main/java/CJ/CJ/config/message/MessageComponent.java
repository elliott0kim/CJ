package CJ.CJ.config.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Getter
@Setter
public class MessageComponent
{
    private String SUCCESS;
    private String UNKNOWN_ERROR;
    private String DUPLICATED_NAME;
    private String INVALID_PASSWORD;
    private String NO_DATA;
    private String INVALID_ACCESS;
    private String EXPIRED_TOKEN;
}
