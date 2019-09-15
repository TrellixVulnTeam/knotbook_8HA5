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
    implementation(group = "org.apache.commons", name = "commons-lang3", version = "3.9")
    implementation(project(":kb.service.api"))
    implementation(project(":kb.core.view"))
    runtimeOnly(project(":kb.tool.path.planner"))
    runtimeOnly(project(":kb.core.code"))
}

val appJVMArgs = listOf(
        "-XX:+UseG1GC",
        "-Xms64m",
        "-Xmx1024m"
)

application {
    applicationDefaultJvmArgs = appJVMArgs
    mainClassName = "kb.application/kb.application.Main"
}

jlink {
    launcher {
        name = "run"
        jvmArgs = appJVMArgs
    }

    addOptions(
            "--no-header-files",
            "--no-man-pages",
            "--strip-debug",
            "--compress=1"
    )
}