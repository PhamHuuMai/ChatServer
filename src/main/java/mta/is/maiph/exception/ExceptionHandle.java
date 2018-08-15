package mta.is.maiph.exception;

import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author MaiPH
 */
@RestControllerAdvice()
public class ExceptionHandle {
    
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity app(ApplicationException ex){ 
        Response response = new Response(ex.getCode());
        return new ResponseEntity(response,HttpStatus.OK);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity common(Exception ex) { 
        ex.printStackTrace();
        Response response = new Response(ErrorCode.UNKNOW_ERROR);
        return new ResponseEntity(response,HttpStatus.OK);
    }
}
