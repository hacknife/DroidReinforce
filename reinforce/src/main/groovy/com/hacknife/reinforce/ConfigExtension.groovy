package com.hacknife.reinforce

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

    ConfigExtension(Project project) {
        ms = "${new File(project.getProjectDir().parentFile, ".reinforce").path}${File.separator}.tools${File.separator}ms-shield.jar"
//        project.
    }
}
