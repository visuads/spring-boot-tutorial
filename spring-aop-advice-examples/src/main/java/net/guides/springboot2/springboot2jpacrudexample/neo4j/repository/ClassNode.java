package net.guides.springboot2.springboot2jpacrudexample.neo4j.repository;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Class")
public class ClassNode {

    @Id
    @GeneratedValue
    private Long nodeId;

    private String className;

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    @Relationship(type = "NEXT_CLASS", direction = Relationship.Direction.OUTGOING)
    private ClassNode nextClassNode;

    public ClassNode(String className) {
        this.className = className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setNextClassNode(ClassNode nextClassNode) {
        this.nextClassNode = nextClassNode;
    }
}
