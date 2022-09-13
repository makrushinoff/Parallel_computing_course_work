package ua.kpi.iasa.parallel.dto.texts;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.kpi.iasa.parallel.dto.ResponseDto;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetTextsResponseDto extends ResponseDto implements Serializable {

    private static final long serialVersionID = 789789789L;

    private List<String> fileNames;

}
