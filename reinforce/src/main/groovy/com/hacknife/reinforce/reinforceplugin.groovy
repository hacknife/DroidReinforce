package com.hacknife.reinforce

import org.gradle.api.Plugin
import org.gradle.api.Project

public class ReinforcePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.task("droidReinforce") << {
            println("==================droidReinforce============================>>")
        }
    }
}