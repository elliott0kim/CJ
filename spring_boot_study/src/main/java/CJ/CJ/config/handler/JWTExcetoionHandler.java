package CJ.CJ.config.handler;

import CJ.CJ.config.message.MessageComponent;
import CJ.CJ.dto.client.MessageCodeAndResDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class JWTExcetoionHandler {
    @Autowired
    private MessageComponent messageComponent;
    @Autowired
    private MessageCodeAndResDto response;

    @ExceptionHandler(value = io.jsonwebtoken.security.SecurityException.class)
    public MessageCodeAndResDto invalidToken()
    {
        response.setMessage(messageComponent.getINVALID_ACCESS());
        response.setData(null);
        return response;
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    public MessageCodeAndResDto invalidToken2()
    {
        response.setMessage(messageComponent.getINVALID_ACCESS());
        response.setData(null);
        return response;
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public MessageCodeAndResDto expiredToken()
    {
        response.setMessage(messageComponent.getEXPIRED_TOKEN());
        response.setData(null);
        return response;
    }

    @ExceptionHandler(value = UnsupportedJwtException.class)
    public MessageCodeAndResDto unsupportToken()
    {
        response.setMessage(messageComponent.getINVALID_ACCESS());
        response.setData(null);
        return response;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public MessageCodeAndResDto claimEmpty()
    {
        response.setMessage(messageComponent.getINVALID_ACCESS());
        response.setData(null);
        return response;
    }
}
