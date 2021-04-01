package com.hacknife.reinforce

import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction

class VersionTask extends BaseTask {


    @TaskAction
    void run() throws Exception {
        ConfigExtension extension = project.getExtensions().findByType(ConfigExtension.class);
        PropertyFinder finder = new PropertyFinder(project, extension)
        finder.invalid()
        String cmd = "java -jar ${finder.ms} -v"
        StreamReader.exec(project, cmd)
    }


}
