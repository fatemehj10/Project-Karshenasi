package ir.beheshti.bpms.model.event_log;

import ir.beheshti.bpms.model.diagrams.DirectoryFlowGram;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.List;

//*** lombok ***//
@Getter
@Setter
//**************//

public class EventLogFile {
    private String path;
    private List<String> headersName;
    private List<EventLogElement> csvRecordsList;
    private DirectoryFlowGram directoryFlowGram;

    public EventLogFile(String path) {
        this.path = path;
    }
}
