package mta.is.maiph.exception;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestControllerAdvice()
public class ExceptionHandle {
    
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity app(ApplicationException ex){ 
        log.info(""+ex.getMessage());
        Response response = new Response(ex.getCode());
        return new ResponseEntity(response,HttpStatus.OK);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity common(Exception ex) { 
        log.info(ex.getMessage());
        Response response = new Response(ErrorCode.UNKNOW_ERROR);
        return new ResponseEntity(response,HttpStatus.OK);
    }
}
