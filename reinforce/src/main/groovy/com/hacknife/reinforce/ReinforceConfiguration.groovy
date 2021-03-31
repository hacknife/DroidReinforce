package com.hacknife.reinforce

import org.gradle.api.Project

class ReinforceConfiguration {

    ReinforceExtension extension

    ReinforceConfiguration(ReinforceExtension extension) {
        this.extension = extension
    }

    void configure(Project project) {
        initDefaults()
        deriveDefaultsFromProject(project)

        PropertyFinder propertyFinder = new PropertyFinder(project, extension)

     }

}
