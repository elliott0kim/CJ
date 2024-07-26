package CJ.CJ.dto.client.auth;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    public ErrorResponseDto (int httpStatus , String errorMessage , LocalDateTime localDateTime)
    {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.localDateTime = localDateTime;
    }
    private int httpStatus;
    private String errorMessage;
    private LocalDateTime localDateTime;
}


