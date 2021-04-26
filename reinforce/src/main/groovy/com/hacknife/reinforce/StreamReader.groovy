package com.hacknife.reinforce

import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class StreamReader {
    InputStream is
    Closure closure
    String charset

    StreamReader(InputStream is, Closure closure) {
        this(is, Charset.defaultCharset().name(), closure)
    }

    StreamReader(InputStream is, String charset, Closure closure) {
        this.is = is
        this.closure = closure
        this.charset = charset
    }

    void read() {
        try {
            is.eachLine(charset) { line ->
                if (line?.trim()) {
                    if (closure) {
                        closure(line)
                    }
                }
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is) {
                    is.close()
                }
            } catch (Exception ex) {
                ex.printStackTrace()
            }
        }
    }

    static exec(Project project, String cmd) {
        Process process = Runtime.runtime.exec(cmd2Translate(cmd), null, null)
        StreamReader reader = new StreamReader(process.inputStream, { text -> project.logger.log(LogLevel.WARN, changCharSet(text)) })
        reader.read()
        process.waitFor()
    }

    static String[] cmd2Translate(String cmd) {
        String[] cmdArr = ["/bin/sh", "-c", cmd]
        if (isWindowSystem()) {
            cmdArr[0] = "cmd"
            cmdArr[1] = "/C"
        }
        return cmdArr
    }

    static boolean isWindowSystem() {
        return System.getProperty("file.separator") == "\\"
    }


    static String changCharSet(String content) {
        new String(content.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)
    }

}
