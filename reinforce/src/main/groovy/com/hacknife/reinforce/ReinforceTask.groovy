package com.hacknife.reinforce

import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction

class ReinforceTask extends BaseTask {
    private static final MS_URL = "https://leguimg.qcloud.com/ms-client/java-tool/1.0.3/ms-shield.jar"

    @TaskAction
    void run() throws Exception {
        ConfigExtension extension = project.getExtensions().findByType(ConfigExtension.class);
        PropertyFinder finder = new PropertyFinder(project, extension)
        finder.invalid()
        generateDirectory()
        project.logger.log(LogLevel.WARN, "reinforce config:")
        project.logger.log(LogLevel.WARN, "secretId: ${finder.secretId}")
        project.logger.log(LogLevel.WARN, "secretKey: ${finder.secretKey}")
        project.logger.log(LogLevel.WARN, "ms-shield: ${finder.msPath}")
        project.logger.log(LogLevel.WARN, "apkSigner: ${finder.apkSignerPath}")
        project.logger.log(LogLevel.WARN, "zipalign: ${finder.zipalign}")
        project.logger.log(LogLevel.WARN, "jks: ${finder.jksPath}")
        project.logger.log(LogLevel.WARN, "alias: ${finder.alias}")
        project.logger.log(LogLevel.WARN, "password: ${finder.password}")
        download(MS_URL, finder.msPath) { progress -> project.logger.log(LogLevel.WARN, progress) }

        File[] apps = listApk(finder.apkDirectory)
        for (apk in apps) {
            File outputDir = makeOutApkDir(outputDir(), finder.apkDirectory, apk)
            String ms_filed = "java -Dfile.encoding=UTF-8 -jar ${finder.msPath} -sid ${finder.secretId} -skey ${finder.secretKey} -uploadPath ${apk.path} -downloadPath ${outputDir.path}"
            StreamReader.exec(project, ms_filed)
            String zipalign = "${finder.zipalign} -v -p 4 ${outputDir}${File.separator}${prefixName(apk)}_legu.apk ${outputDir}${File.separator}${prefixName(apk)}_legu_zipalign.apk"
            StreamReader.exec(project, zipalign)
            String apkSinger = "java -jar ${finder.apkSignerPath} sign --ks ${finder.jksPath} --ks-key-alias ${finder.alias} --ks-pass pass:${finder.password} --key-pass pass:${finder.password} --out ${outputDir}${File.separator}${prefixName(apk)}_legu_zipalign_signed.apk ${outputDir}${File.separator}${prefixName(apk)}_legu_zipalign.apk"
            StreamReader.exec(project, apkSinger)
            new File("${outputDir}${File.separator}${prefixName(apk)}_legu_zipalign_signed.apk.idsig").delete()
        }
    }
}
