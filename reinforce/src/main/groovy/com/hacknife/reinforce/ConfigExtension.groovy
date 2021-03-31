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

    ConfigExtension(Project project) {
    }
}
