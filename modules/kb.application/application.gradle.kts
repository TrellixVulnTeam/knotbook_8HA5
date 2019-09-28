plugins {
    java
    application
    id("org.openjfx.javafxplugin")
    id("org.beryx.jlink")
    kotlin("jvm")
}

javafx {
    modules("javafx.controls")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":kb.service.api"))
    implementation(project(":kb.core.view"))
    runtimeOnly(project(":kb.tool.path.planner"))
    runtimeOnly(project(":kb.core.code"))
    runtimeOnly(project(":kb.tool.cng"))
}


application {
    mainClassName = "kb.application/kb.application.Main"
}

jlink {
    launcher {
        name = "run"
    }

    addOptions(
            "--no-header-files",
            "--no-man-pages",
            "--strip-debug"
    )
}