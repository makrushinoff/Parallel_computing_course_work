package ua.kpi.iasa.parallel.dto.index;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.kpi.iasa.parallel.dto.ResponseDto;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IndexFilesResponseDto extends ResponseDto implements Serializable {

    private static final long serialVersionID = 321321321L;

    private Long indexingTime;

}
