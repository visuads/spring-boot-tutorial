package net.guides.springboot2.springboot2jpacrudexample.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MethodNodeRepository extends Neo4jRepository<MethodNode, Long> {
    Optional<MethodNode> findByMethodNameAndClassName(String methodName, String className);
}
