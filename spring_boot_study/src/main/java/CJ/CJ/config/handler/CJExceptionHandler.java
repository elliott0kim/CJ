//package CJ.CJ.config.handler;
//
//import CJ.CJ.config.message.MessageComponent;
//import CJ.CJ.dto.client.MessageCodeAndResDto;
//import org.apache.ibatis.exceptions.TooManyResultsException;
//import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
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
////    @ExceptionHandler(value = RuntimeException.class)
////    public MessageCodeAndResDto UnknownError()
////    {
////        response.setMessage(messageComponent.getUNKNOWN_ERROR());
////        response.setData(null);
////        return response;
////    }
//
//    @ExceptionHandler(value = SQLException.class)
//    public MessageCodeAndResDto sqlError()
//    {
//        response.setMessage(messageComponent.getNO_DATA());
//        response.setData(null);
//        return response;
//    }
//    @ExceptionHandler(value = EmptyResultDataAccessException.class)
//    public MessageCodeAndResDto noData()
//    {
//        response.setMessage(messageComponent.getNO_DATA());
//        response.setData(null);
//        return response;
//    }
//
//    @ExceptionHandler(value = BadCredentialsException.class)
//    public MessageCodeAndResDto failToLogin()
//    {
//        response.setMessage(messageComponent.getINVALID_PASSWORD());
//        response.setData(null);
//        return response;
//    }
//
//    @ExceptionHandler(value = TooManyResultsException.class)
//    public MessageCodeAndResDto notOneResult(TooManyResultsException exception)
//    {
////        if (exception.getMessage().equals(messageComponent.getDUPLICATED_NAME()))
////        {
//            response.setMessage(messageComponent.getDUPLICATED_NAME());
//            response.setData(null);
//            return response;
////        }
////        else
////        {
////            response.setMessage(messageComponent.getUNKNOWN_ERROR());
////            response.setData(null);
////            return response;
////        }
//    }
//
//}
