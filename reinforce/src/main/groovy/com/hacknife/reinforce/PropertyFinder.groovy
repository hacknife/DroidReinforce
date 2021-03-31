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
        getString(project, 'secretId', extension.bintrayUser)
    }

    def getSecretKey() {
        getString(project, 'secretKey', extension.bintrayKey)
    }

    def getMsPath() {
        getBoolean(project, 'msPath', extension.dryRun)
    }

    def getOverride() {
        getBoolean(project, 'override', extension.override)
    }

    def getPublishVersion() {
        getString(project, 'publishVersion', extension.publishVersion)
    }

    private String getString(Project project, String propertyName, String defaultValue) {
        project.hasProperty(propertyName) ? project.getProperty(propertyName) : defaultValue
    }

    private boolean getBoolean(Project project, String propertyName, boolean defaultValue) {
        project.hasProperty(propertyName) ? Boolean.parseBoolean(project.getProperty(propertyName)) : defaultValue
    }

}
