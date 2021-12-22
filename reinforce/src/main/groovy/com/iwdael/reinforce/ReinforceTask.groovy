package com.iwdael.reinforce

import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction

class ReinforceTask extends BaseTask {
    private static final MS_URL = "https://leguimg.qcloud.com/ms-client/java-tool/1.0.3/ms-shield.jar"
    private static final APK_SIGNER_URL = "http://file.hacknife.com/apksigner_30.0.2.jar"
    private static final ZIP_ALIGN_URL = "http://file.hacknife.com/zipalign_30.0.2.exe"

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

        List<File> dirs = finder.apkDirectory
        for (dir in dirs) {
            File[] apps = listApk(dir)
            for (apk in apps) {
                File outputDir = makeOutApkDir(outputDir(), rootApkDir(), apk)
                String ms_filed = "java -Dfile.encoding=UTF-8 -jar ${finder.ms} -sid ${finder.secretId} -skey ${finder.secretKey} -uploadPath ${apk.path} -downloadPath ${outputDir.path}"
                StreamReader.exec(project, ms_filed)
                String zipalign = "${finder.zipalign} -v -p 4 ${outputDir}${File.separator}${prefixName(apk)}_legu.apk ${outputDir}${File.separator}${prefixName(apk)}_legu_zipalign.apk"
                StreamReader.exec(project, zipalign)
                String apkSinger = "java -jar ${finder.apkSigner} sign --ks ${finder.jks} --ks-key-alias ${finder.alias} --ks-pass pass:${finder.password} --key-pass pass:${finder.password} --out ${outputDir}${File.separator}${prefixName(apk)}_legu_zipalign_signed.apk ${outputDir}${File.separator}${prefixName(apk)}_legu_zipalign.apk"
                StreamReader.exec(project, apkSinger)
                new File("${outputDir}${File.separator}${prefixName(apk)}_legu_zipalign_signed.apk.idsig").delete()
            }
        }

    }
}
