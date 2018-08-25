package mta.is.maiph.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author MaiPH
 */
@Setter
@Getter
@ToString
public class SkipTakeRequest {

    @NotNull
    Integer skip;
    @NotNull
    Integer take;
}
