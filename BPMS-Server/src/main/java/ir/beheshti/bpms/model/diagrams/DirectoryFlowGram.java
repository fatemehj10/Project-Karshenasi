package ir.beheshti.bpms.model.diagrams;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//*** lombok ***//
@Getter
@Setter
//**************//

@Component
public class DirectoryFlowGram {
    private List<DirectoryFlowGramEdge> flowchart;

    public DirectoryFlowGram() {
        this.flowchart = new ArrayList<>();
    }

    public void addActivityEdge(DirectoryFlowGramEdge directoryFlowGramEdge) {
        flowchart.add(directoryFlowGramEdge);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < flowchart.size(); i++) {
            result += (("Edge " + i + " : ") + flowchart.get(i) + "\n");
        }
        return result;
    }
}
