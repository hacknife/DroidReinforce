package com.iwdael.reinforce

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

    def getMs() {
        getString(project, 'ms', extension.ms)
    }

    def getApkSigner() {
        getString(project, 'apkSigner', extension.apkSigner)
    }

    def getJks() {
        String jks = getString(project, 'jks', extension.jks)
        File file = new File(jks)
        if (file.exists()) return jks
        else "${project.parent.projectDir.path}${File.separator}${jks}"
    }

    def getAlias() {
        getString(project, 'alias', extension.alias)
    }

    def getPassword() {
        getString(project, 'password', extension.password)
    }

    def getZipalign() {
        getString(project, 'zipalign', extension.zipalign)
    }

    def getApkDirectory() {
        String[] apkDirs = getString(project, 'apkDir', extension.apkDir)
                .replaceAll("\\[", '')
                .replaceAll("]", '')
                .replaceAll(" ", '')
                .split(",")
        List<File> files = new ArrayList<>()
        for (String dir in apkDirs) {
            files.add(new File("${project.projectDir.path}${File.separator}build${File.separator}outputs${File.separator}apk${File.separator}${dir}"))
        }
        return files
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
        if (ms == null) {
            throw new GradleException("Missing attributes: reinforceConfig.ms")
        }
        if (apkSigner == null) {
            throw new GradleException("Missing attributes: reinforceConfig.apkSigner")
        }
        if (jks == null) {
            throw new GradleException("Missing attributes: reinforceConfig.jks")
        }
        if (alias == null) {
            throw new GradleException("Missing attributes: reinforceConfig.alias")
        }
        if (password == null) {
            throw new GradleException("Missing attributes: reinforceConfig.password")
        }
    }
}
