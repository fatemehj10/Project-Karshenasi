package ir.beheshti.bpms.model.diagrams;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

//*** lombok ***//
@Getter
@Setter
//**************//

public class DirectoryFlowGramNode {
    private String name;
    private int index;

    public DirectoryFlowGramNode(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        DirectoryFlowGramNode node = (DirectoryFlowGramNode)o;
        return this.name.equals(node.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return '(' + name  + ')';
    }
}
