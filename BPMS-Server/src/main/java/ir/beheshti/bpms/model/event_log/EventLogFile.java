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
    private final String path;
    private List<EventLogElement> csvRecordsList;
    private DirectoryFlowGram directoryFlowgram;

    public EventLogFile(String path) throws IOException {
        this.path = path;
    }
}
