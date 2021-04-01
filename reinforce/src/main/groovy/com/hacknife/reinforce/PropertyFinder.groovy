package com.hacknife.reinforce

import org.gradle.api.GradleException
import org.gradle.api.Project;

class PropertyFinder {

    private final Project project
    private final ConfigExtension extension


    PropertyFinder(Project project, ConfigExtension extension) {
        this.extension = extension
        this.project = project
    }

    def getSecretId() {
        getString(project, 'secretId', extension.secretId)
    }

    def getSecretKey() {
        getString(project, 'secretKey', extension.secretKey)
    }

    def getMsPath() {
        getString(project, 'msPath', extension.msPath)
    }

    def getApkSignerPath() {
        getString(project, 'apkSignerPath', extension.apkSignerPath)
    }

    def getJksPath() {
        getString(project, 'jksPath', extension.jksPath)
    }

    def getAlias() {
        getString(project, 'alias', extension.alias)
    }

    def getPassword() {
        getString(project, 'password', extension.password)
    }
    def getZipalign() {
        getString(project, 'zipalignPath', extension.zipalignPath)
    }

    def getApkDirectory() {
        new File(getString(project, 'apkDir', "${project.projectDir.path}${File.separator}build${File.separator}outputs${File.separator}apk${File.separator}"))
    }

    private String getString(Project project, String propertyName, String defaultValue) {
        project.hasProperty(propertyName) ? project.getProperty(propertyName) : defaultValue
    }

    private boolean getBoolean(Project project, String propertyName, boolean defaultValue) {
        project.hasProperty(propertyName) ? Boolean.parseBoolean(project.getProperty(propertyName)) : defaultValue
    }

    void invalid() {
        if (secretId == null) {
            throw new GradleException("Missing attributes: reinforceConfig.secretId")
        }
        if (secretKey == null) {
            throw new GradleException("Missing attributes: reinforceConfig.secretKey")
        }
        if (msPath == null) {
            throw new GradleException("Missing attributes: reinforceConfig.msPath")
        }
        if (apkSignerPath == null) {
            throw new GradleException("Missing attributes: reinforceConfig.apkSignerPath")
        }
        if (jksPath == null) {
            throw new GradleException("Missing attributes: reinforceConfig.jksPath")
        }
        if (alias == null) {
            throw new GradleException("Missing attributes: reinforceConfig.alias")
        }
        if (password == null) {
            throw new GradleException("Missing attributes: reinforceConfig.password")
        }
    }
}
