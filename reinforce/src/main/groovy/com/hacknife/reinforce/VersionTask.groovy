package com.hacknife.reinforce

import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction

class VersionTask extends BaseTask {

    private static final MS_URL = "https://leguimg.qcloud.com/ms-client/java-tool/1.0.3/ms-shield.jar"
    private static final APK_SIGNER_URL = "https://leguimg.qcloud.com/ms-client/java-tool/1.0.3/ms-shield.jar"
    private static final ZIP_ALIGN_URL = "https://leguimg.qcloud.com/ms-client/java-tool/1.0.3/ms-shield.jar"

    @TaskAction
    void run() throws Exception {
        ConfigExtension extension = project.getExtensions().findByType(ConfigExtension.class);
        PropertyFinder finder = new PropertyFinder(project, extension)
        finder.invalid()
        generateDirectory()
        project.logger.log(LogLevel.WARN, "reinforce config: {")
        project.logger.log(LogLevel.WARN, "     secretId: ${finder.secretId}")
        project.logger.log(LogLevel.WARN, "     secretKey: ${finder.secretKey}")
        project.logger.log(LogLevel.WARN, "     ms-shield: ${finder.ms}")
        project.logger.log(LogLevel.WARN, "     apkSigner: ${finder.apkSigner}")
        project.logger.log(LogLevel.WARN, "     zipalign: ${finder.zipalign}")
        project.logger.log(LogLevel.WARN, "     apkDir: ${finder.apkDirectory}")
        project.logger.log(LogLevel.WARN, "     jks: ${finder.jks}")
        project.logger.log(LogLevel.WARN, "     alias: ${finder.alias}")
        project.logger.log(LogLevel.WARN, "     password: ${finder.password}")
        project.logger.log(LogLevel.WARN, "}")
        download(MS_URL, finder.ms) { progress -> project.logger.log(LogLevel.WARN, "${progress}") }
        download(ZIP_ALIGN_URL, finder.zipalign) { progress -> project.logger.log(LogLevel.WARN, "${progress}") }
        download(APK_SIGNER_URL, finder.apkSigner) { progress -> project.logger.log(LogLevel.WARN, "${progress}") }

        String cmd = "java -jar ${finder.ms} -v"
        project.logger.log(LogLevel.WARN, "\n\n\ntencent legu version :")
        StreamReader.exec(project, cmd)
        project.logger.log(LogLevel.WARN, "\n")

    }


}
