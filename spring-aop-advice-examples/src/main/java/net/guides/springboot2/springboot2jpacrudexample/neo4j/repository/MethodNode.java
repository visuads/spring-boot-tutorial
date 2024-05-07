package net.guides.springboot2.springboot2jpacrudexample.neo4j.repository;

import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.ArrayList;
import java.util.List;

@Node("Method")
public class MethodNode {

    @Id
    @GeneratedValue
    private Long nodeId;

    private String methodName;

    private String className;

    @Relationship(type = "CALLS", direction = Relationship.Direction.OUTGOING)
    private List<MethodNode> calledMethods;


    // Constructor
    public MethodNode(String methodName, String className) {
        this.methodName = methodName;
        this.className = className;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setCalledMethods(List<MethodNode> calledMethods) {
        this.calledMethods = calledMethods;
    }

    public List<MethodNode> getCalledMethods() {
        if (calledMethods == null) {
            calledMethods = new ArrayList<>();
        }
        return calledMethods;
    }
}
