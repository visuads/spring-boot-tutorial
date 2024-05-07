package net.guides.springboot2.springboot2jpacrudexample.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceUtils {
    public static String compactStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
