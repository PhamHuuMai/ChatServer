package mta.is.maiph.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author MaiPH
 */
@AllArgsConstructor
@Getter
@Setter

public class ApplicationException extends Exception{
    int code;            
}
