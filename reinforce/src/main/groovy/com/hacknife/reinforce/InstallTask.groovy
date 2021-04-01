package com.hacknife.reinforce

import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction

class InstallTask extends BaseTask {


    @TaskAction
    void run() throws Exception {
        String[] paths = identityPath.name.split(" ")
        StringBuilder cmd = new StringBuilder()
        for (int i = 0; i < paths.size(); i++) {
            if (i == 0) {
                cmd.append("adb install -r ${outputDir().path}${File.separator}")
            } else if (i == paths.size() - 1) {
                cmd.append(paths[i]).append("_legu_zipalign_signed.apk")
            } else {
                cmd.append(paths[i]).append(File.separator)
            }
        }
        project.logger.log(LogLevel.WARN, cmd.toString())
        StreamReader.exec(project,cmd.toString())
    }


}
