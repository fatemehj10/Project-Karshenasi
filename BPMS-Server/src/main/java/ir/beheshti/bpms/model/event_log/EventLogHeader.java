package ir.beheshti.bpms.model.event_log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//*** lombok ***//
@Getter
@Setter
@NoArgsConstructor
//**************//

@Component
public class EventLogHeader {
    private String action;
    private String caseID;
    private String time;
    private List<String> all = new ArrayList<>();

    public void addHeader(String header) {
        all.add(header);
    }
}
