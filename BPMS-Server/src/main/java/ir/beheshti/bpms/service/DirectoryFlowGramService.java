package ir.beheshti.bpms.service;

import ir.beheshti.bpms.model.DTO.DirectoryFlowGramDto;
import ir.beheshti.bpms.model.DTO.LinkDataArrayDto;
import ir.beheshti.bpms.model.DTO.NodeDataArrayDto;
import ir.beheshti.bpms.model.diagrams.DirectoryFlowGram;
import ir.beheshti.bpms.model.diagrams.DirectoryFlowGramEdge;
import ir.beheshti.bpms.model.diagrams.DirectoryFlowGramNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//*** lombok ***//
@Getter
@Setter
@NoArgsConstructor
//**************//

@Component
public class DirectoryFlowGramService {
    DirectoryFlowGramDto directoryFlowGramDto;
    public void covertToDto(Map<DirectoryFlowGramNode, Integer> nodesFreq, DirectoryFlowGram directoryFlowGram) {
        DirectoryFlowGramDto flowGramDto = new DirectoryFlowGramDto();
        List<NodeDataArrayDto> nodeDataArray = new ArrayList<>();
        List<LinkDataArrayDto> linkDataArray = new ArrayList<>();
        Set<DirectoryFlowGramNode> setNodes = nodesFreq.keySet();
        //node data array set
        int index = 0;
        for (DirectoryFlowGramNode item:setNodes) {
            NodeDataArrayDto newNode = new NodeDataArrayDto();
            item.setIndex(index);
            newNode.setKey(String.valueOf(index));
            newNode.setText(item.getName());
            newNode.setBound(String.valueOf(nodesFreq.get(item)));
            nodeDataArray.add(newNode);
            index++;
        }
        flowGramDto.setNodeDataArray(nodeDataArray);
        /////////////////////
        //link data array set
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        int max = Integer.MIN_VALUE;
        int maxIndex = 0;
        index = 0;
        List<DirectoryFlowGramEdge> edges = directoryFlowGram.getFlowchart();
        for (DirectoryFlowGramEdge edge: edges) {
            if (edge.getFrequency() < min) {
                min = edge.getFrequency();
                minIndex = index;
            }
            if (edge.getFrequency() > max) {
                max = edge.getFrequency();
                maxIndex = index;
            }
            LinkDataArrayDto newEdge = new LinkDataArrayDto();
            for (DirectoryFlowGramNode item:setNodes) {
                if(item.equals(edge.getNode1()))
                    newEdge.setFrom(String.valueOf(item.getIndex()));
                if(item.equals(edge.getNode2()))
                    newEdge.setTo(String.valueOf(item.getIndex()));
            }
            newEdge.setText(String.valueOf(edge.getFrequency()));
            linkDataArray.add(newEdge);
            index++;
        }
        linkDataArray.get(minIndex).setRed("true");
        linkDataArray.get(maxIndex).setGreen("true");

        flowGramDto.setLinkDataArray(linkDataArray);
        /////////////////////
        directoryFlowGramDto = flowGramDto;
    }
}
