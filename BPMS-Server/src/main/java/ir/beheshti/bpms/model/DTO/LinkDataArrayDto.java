package ir.beheshti.bpms.model.DTO;

import lombok.Getter;
import lombok.Setter;

//*** lombok ***//
@Getter
@Setter
//**************//

public class LinkDataArrayDto {
    private String from;
    private String to;
    private String green = "";
    private String red = "";
    private String text;
}
