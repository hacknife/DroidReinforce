package com.hacknife.reinforce

import org.gradle.api.Project


class ConfigExtension {
    String secretId;
    String secretKey;
    String msPath;
    String apkSignerPath;
    String jksPath;
    String alias;
    String password;
    String zipalignPath;

    ConfigExtension(Project project) {
        msPath = "${new File(project.getProjectDir().parentFile, ".reinforce").path}${File.separator}.tools${File.separator}ms-shield.jar"
    }
}
