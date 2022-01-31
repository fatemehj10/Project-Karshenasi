package ir.beheshti.bpms.model.diagrams;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

//*** lombok ***//
@Getter
@Setter
//**************//

public class DirectoryFlowGramEdge {
    private DirectoryFlowGramNode node1;
    private DirectoryFlowGramNode node2;
    private int frequency;

    public DirectoryFlowGramEdge(DirectoryFlowGramNode node1, DirectoryFlowGramNode node2) {
        this.node1 = node1;
        this.node2 = node2;
        frequency = 1;
    }

    public void addFreq() {
        frequency++;
    }

    @Override
    public boolean equals(Object o) {
        DirectoryFlowGramEdge other = (DirectoryFlowGramEdge) o;
        return (this.node1.equals(other.getNode1()) &&
                this.node2.equals(other.getNode2()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(node1, node2);
    }

    @Override
    public String toString() {
        return "[" + node1 + ", " + node2 + ", " + frequency + "]";
    }
}
