package ua.kpi.iasa.parallel.dto.index;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.kpi.iasa.parallel.dto.RequestDto;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IndexFilesRequestDto extends RequestDto implements Serializable {

    private static final long serialVersionID = 123123123L;

    private int threadsNumber;

}
