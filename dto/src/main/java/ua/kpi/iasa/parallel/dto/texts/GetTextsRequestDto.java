package ua.kpi.iasa.parallel.dto.texts;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.kpi.iasa.parallel.dto.RequestDto;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetTextsRequestDto extends RequestDto implements Serializable {

    private static final long serialVersionID = 987987987L;

    private String keyword;

}
