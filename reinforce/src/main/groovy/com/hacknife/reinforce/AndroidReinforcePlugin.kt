package com.hacknife.reinforce

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class AndroidReinforcePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.task("androidReinforce") {
            println("====================================androidReinforce===========================================")
        }
    }
}