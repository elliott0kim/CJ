//package CJ.CJ.config.handler;
//
//import CJ.CJ.config.message.MessageComponent;
//import CJ.CJ.dto.client.MessageCodeAndResDto;
//import org.apache.ibatis.exceptions.TooManyResultsException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.sql.SQLException;
//
//@ControllerAdvice
//@RestControllerAdvice
//public class CJExceptionHandler {
//    @Autowired
//    private MessageComponent messageComponent;
//    @Autowired
//    private MessageCodeAndResDto response;
//
//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<MessageCodeAndResDto> UnknownError()
//    {
//        response.setMessage(messageComponent.getUNKNOWN_ERROR());
//        response.setData(null);
//        return new ResponseEntity<MessageCodeAndResDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(value = SQLException.class)
//    public ResponseEntity<MessageCodeAndResDto> sqlError()
//    {
//        response.setMessage(messageComponent.getNO_DATA());
//        response.setData(null);
//        return new ResponseEntity<MessageCodeAndResDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    @ExceptionHandler(value = EmptyResultDataAccessException.class)
//    public ResponseEntity<MessageCodeAndResDto> noData()
//    {
//        response.setMessage(messageComponent.getNO_DATA());
//        response.setData(null);
//        return new ResponseEntity<MessageCodeAndResDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(value = BadCredentialsException.class)
//    public ResponseEntity<MessageCodeAndResDto> failToLogin()
//    {
//        response.setMessage(messageComponent.getINVALID_PASSWORD());
//        response.setData(null);
//        return new ResponseEntity<MessageCodeAndResDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(value = TooManyResultsException.class)
//    public ResponseEntity<MessageCodeAndResDto> notOneResult(TooManyResultsException exception)
//    {
//            response.setMessage(messageComponent.getDUPLICATED_NAME());
//            response.setData(null);
//            return new ResponseEntity<MessageCodeAndResDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//}
