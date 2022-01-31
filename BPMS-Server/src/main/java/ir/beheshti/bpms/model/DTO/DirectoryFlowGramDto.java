package ir.beheshti.bpms.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//*** lombok ***//
@Getter
@Setter
//**************//

public class DirectoryFlowGramDto {
    private List<NodeDataArrayDto> nodeDataArray;
    private List<LinkDataArrayDto> linkDataArray;
}
