package ir.beheshti.bpms.service;

import ir.beheshti.bpms.model.diagrams.DirectoryFlowGram;
import ir.beheshti.bpms.model.diagrams.DirectoryFlowGramEdge;
import ir.beheshti.bpms.model.diagrams.DirectoryFlowGramNode;
import ir.beheshti.bpms.model.event_log.EventLogElement;
import ir.beheshti.bpms.model.event_log.EventLogHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

//*** lombok ***//
@Getter
@Setter
@NoArgsConstructor
//**************//

@Component
public class EventLogFileService {
    private String path;
    private EventLogHeader headersName;
    private Map<DirectoryFlowGramNode, Integer> nodesFreq;
    private DirectoryFlowGram directoryFlowGram;
    private List<EventLogElement> csvRecordsList;

    public void read(String[] headers) {
        //read CSV file with given header names
        Iterable<CSVRecord> records = null;
        try (Reader in = new FileReader(path)) {
            records = CSVFormat.newFormat(';')
                    .withHeader(headers)
                    .withFirstRecordAsHeader()
                    .parse(in);
            //read CSV file with given header names
            csvRecordsList = new ArrayList<>();
            for (CSVRecord record : records) {
                EventLogElement element = new EventLogElement(record.get(headers[0]),
                        record.get(headers[1]),
                        record.get(headers[2]));
                csvRecordsList.add(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DirectoryFlowGram getDirectoryFlowGram() {
        return directoryFlowGram;
    }

    public void solveDirectoryFlowGram() {
        int caseIdCount = 0;
        Collections.sort(csvRecordsList);
        int length = csvRecordsList.size();
        directoryFlowGram = new DirectoryFlowGram();
        nodesFreq = new HashMap<>();
        DirectoryFlowGramEdge newEdge;
        for (int i = 0; i < length - 1; i++) {
            EventLogElement element = csvRecordsList.get(i);
            EventLogElement nextElement = csvRecordsList.get(i + 1);
            if (element.getCaseId().equals(nextElement.getCaseId())) {
                newEdge = new DirectoryFlowGramEdge(
                        new DirectoryFlowGramNode(element.getActivityName()),
                        new DirectoryFlowGramNode(nextElement.getActivityName()));
                if (!directoryFlowGram.getFlowchart().contains(newEdge)) {
                    directoryFlowGram.addActivityEdge(newEdge);
                }
                //add edge freq
                else {
                    int index = directoryFlowGram.getFlowchart().indexOf(newEdge);
                    directoryFlowGram.getFlowchart().get(index).addFreq();
                }
                //add node1 freq
                if (nodesFreq.containsKey(newEdge.getNode1())) {
                    Integer val = nodesFreq.get(newEdge.getNode1()) + 1;
                    nodesFreq.replace(newEdge.getNode1(), val);
                }
                else
                    nodesFreq.put(newEdge.getNode1(), 1);
            }
            else
                caseIdCount++;
        }
        //show results
        System.out.println(caseIdCount);
        System.out.println(directoryFlowGram.toString());
        System.out.println(nodesFreq);
    }

    public EventLogHeader getHeadersName(){
        if (headersName == null) {
            detectHeadersName();
        }
        return headersName;
    }

    private void detectHeadersName() {
        //read header names
        headersName = new EventLogHeader();
        List<String> head = new ArrayList<>();
        try (Reader in = new FileReader(path)){
            head = CSVFormat.newFormat(';')
                    .withFirstRecordAsHeader()
                    .parse(in)
                    .getHeaderNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String str: head) {
            if (str.contains("Activity"))
                headersName.setAction(str);
            else if (str.contains("Case ID"))
                headersName.setCaseID(str);
            else if (str.contains("Complete Timestamp"))
                headersName.setTime(str);
            else
                headersName.addHeader(str);
        }
    }
}
