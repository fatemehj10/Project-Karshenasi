package ir.beheshti.bpms.service;

import ir.beheshti.bpms.model.diagrams.DirectoryFlowGram;
import ir.beheshti.bpms.model.diagrams.DirectoryFlowGramEdge;
import ir.beheshti.bpms.model.diagrams.DirectoryFlowGramNode;
import ir.beheshti.bpms.model.event_log.EventLogElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//*** lombok ***//
@Getter
@Setter
@NoArgsConstructor
//**************//

@Component
public class EventLogFileService {
    private static final String[] HEADERS = {"Case ID", "Complete Timestamp", "Activity"};

    private String path;
    private List<EventLogElement> csvRecordsList;
    private DirectoryFlowGram directoryFlowGram;

    public void read(String path) throws IOException {
        this.path = path;
        Reader in = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.newFormat(';')
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);
        csvRecordsList = new ArrayList<>();
        for (CSVRecord record : records) {
            EventLogElement element = new EventLogElement(record.get("Case ID"),
                    record.get("Complete Timestamp"),
                    record.get("Activity"));
            csvRecordsList.add(element);
        }
        in.close();
    }

    public void solveDirectoryFlowGram() {
        Collections.sort(csvRecordsList);
        int length = csvRecordsList.size();
        directoryFlowGram = new DirectoryFlowGram();
        DirectoryFlowGramEdge newEdge;
        for (int i = 0; i < length - 1; i++) {
            EventLogElement element = csvRecordsList.get(i);
            EventLogElement nextElement = csvRecordsList.get(i + 1);
            if (element.getCaseId().equals(nextElement.getCaseId())) {
                newEdge = new DirectoryFlowGramEdge(
                        new DirectoryFlowGramNode(element.getActivityName()),
                        new DirectoryFlowGramNode(nextElement.getActivityName()));
                if (!directoryFlowGram.getFlowchart().contains(newEdge))
                    directoryFlowGram.addActivityEdge(newEdge);
            }
        }
        System.out.println(directoryFlowGram.toString());
    }
}
