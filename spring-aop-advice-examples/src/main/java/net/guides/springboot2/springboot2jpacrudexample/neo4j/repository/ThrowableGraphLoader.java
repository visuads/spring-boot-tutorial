package net.guides.springboot2.springboot2jpacrudexample.neo4j.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThrowableGraphLoader {

    @Autowired
    private MethodNodeRepository methodNodeRepository;

    @Autowired
    private ClassNodeRepository classNodeRepository;

    public void loadThrowableData(StackTraceElement[] stackTrace) {
        MethodNode previousMethodNode = null;

        for (StackTraceElement element : stackTrace) {
            // Extract method name and class name from stack trace element
            String methodName = element.getMethodName();
            String className = element.getClassName();

            // Create or retrieve method node
            MethodNode methodNode = methodNodeRepository.findByMethodNameAndClassName(methodName, className)
                    .orElseGet(() -> methodNodeRepository.save(new MethodNode(methodName, className)));

            // Create relationship between method nodes
            if (previousMethodNode != null) {
                previousMethodNode.getCalledMethods().add(methodNode);
                methodNodeRepository.save(previousMethodNode);
            }
            previousMethodNode = methodNode;
        }
    }
}

