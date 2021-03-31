package com.hacknife.reinforce

import org.gradle.api.Project;

class PropertyFinder {

    private final Project project
    private final ReinforceExtension extension

    PropertyFinder(Project project, ReinforceExtension extension) {
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

    def getKeystore() {
        getString(project, 'keystorePath', extension.keystorePath)
    }

    def getAlias() {
        getString(project, 'alias', extension.alias)
    }

    def getPassword() {
        getString(project, 'password', extension.password)
    }


    private String getString(Project project, String propertyName, String defaultValue) {
        project.hasProperty(propertyName) ? project.getProperty(propertyName) : defaultValue
    }

    private boolean getBoolean(Project project, String propertyName, boolean defaultValue) {
        project.hasProperty(propertyName) ? Boolean.parseBoolean(project.getProperty(propertyName)) : defaultValue
    }

}
