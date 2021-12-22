package com.iwdael.reinforce

import org.gradle.api.Project


class ConfigExtension {
    String secretId;
    String secretKey;
    String ms;
    String apkSigner;
    String jks;
    String alias;
    String password;
    String zipalign;
    String apkDir = ""

    ConfigExtension(Project project) {
        ms = "${new File(project.getProjectDir().parentFile, ".reinforce").path}${File.separator}.tools${File.separator}ms-shield.jar"
        apkSigner = "${new File(project.getProjectDir().parentFile, ".reinforce").path}${File.separator}.tools${File.separator}apkSigner_30.0.2.jar"
        zipalign = "${new File(project.getProjectDir().parentFile, ".reinforce").path}${File.separator}.tools${File.separator}zipalign_30.0.2.exe"
    }
}
