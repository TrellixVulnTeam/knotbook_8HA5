package knotbook.application

import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import knotbook.core.fx.*
import knotbook.core.snap.SnapScene
import knotbook.core.splash.GCSplash
import knotbook.core.splash.Splash
import knotbook.core.table.Knotable
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.materialdesign.MaterialDesign
import java.io.File
import kotlin.system.exitProcess


class Main : Application() {

    private val bar = menuBar {
        modify {
            menu {
                name("File")
                modify {
                    item {
                        name("New Table")
                    }
                    item { name("New Table") }
                    item { name("Delete Table") }
                    item {
                        name("Synchronize")
                        icon(FontAwesomeSolid.SYNC, 16)
                    }
                    separator()
                    item {
                        name("New Repository")
                    }
                    item {
                        name("Mark Repository as Read-Only")
                    }
                    item {
                        name("Open Repository")
                        icon(FontAwesomeSolid.FOLDER_OPEN, 16)
                        shortcut(KeyCode.O, control = true)
                    }
                    item {
                        name("Reveal Context in Source")
                        shortcut(KeyCode.Q, control = true)
                    }
                    separator()
                    item {
                        name("Exit")
                        action { exitProcess(0) }
                    }
                }
            }
            menu {
                name("Edit")
                modify {
                    item {
                        name("Find")
                        icon(FontAwesomeSolid.SEARCH, 16)
                    }
                    item {
                        name("Replace")
                    }
                    item {
                        name("Copy")
                        icon(FontAwesomeSolid.COPY, 16)
                        shortcut(KeyCode.C, control = true)
                    }
                    item {
                        name("Copy Special")
                        shortcut(KeyCode.C, control = true, shift = true)
                    }
                }
            }
            menu {
                name("View")
                modify {

                }
            }
            menu {
                name("Help")
                modify {
                    item { name("Process Manager") }
                    item { name("Plugin Manager") }
                    item { name("Show Log File") }
                    item {
                        name("Application Registry")
                        icon(FontAwesomeSolid.ADDRESS_BOOK, 16)
                    }
                    item {
                        name("Start GC Cycle")
                        action { GCSplash.splash() }
                        shortcut(KeyCode.B, control = true)
                    }
                    separator()
                    item {
                        name("About")
                        action { Splash.splash() }
                        shortcut(KeyCode.F1)
                    }
                }
            }
        }
    }
//            Menu("View").apply {
//                items.addAll(
//                        MenuItem("Expand to Source").apply {
//                            accelerator = KeyCodeCombination(KeyCode.F9)
//                        },
//                        MenuItem("Toggle Sidebar").apply {
//                            accelerator = KeyCodeCombination(KeyCode.F9)
//                        },
//                        MenuItem("Toggle Fullscreen").apply {
//                            accelerator = KeyCodeCombination(KeyCode.F11)
//                        },
//                        MenuItem("Toggle Theme", FontIcon(FontAwesomeSolid.ADJUST)).apply {
//                            accelerator = KeyCodeCombination(KeyCode.F2)
//                        }
//                )
//            }

    override fun start(stage: Stage) {
        stage.title = "Knotbook"
        stage.icons.add(Image(Main::class.java.getResourceAsStream("/knotbook/application/knot-tb.png")))
        val knotable = Knotable()
        val min = Button("", FontIcon.of(MaterialDesign.MDI_WINDOW_MINIMIZE, 17)).apply {
            styleClass.add("minmax-button")
        }
        val max = Button("", FontIcon.of(MaterialDesign.MDI_WINDOW_MAXIMIZE, 17)).apply {
            styleClass.add("minmax-button")
        }
        val mover = HBox().apply {
            prefWidth = 400.0
            prefHeight = 28.0
            minHeight = 28.0
            maxHeight = 28.0
            alignment = Pos.CENTER_LEFT
            background = Background(BackgroundFill(Color.valueOf("#eee"), null, null))

            children.apply {
                add(HBox().apply {
                    alignment = Pos.CENTER
                    prefWidth = 36.0
                    children.add(ImageView(Image(Main::class.java.getResourceAsStream("/knotbook/application/knot-tb.png"))).apply {
                        isPreserveRatio = true
                        fitHeight = 18.0
                    })
                })
                add(bar)
                add(Label("Test Repo").apply {
                    style = "-fx-font-weight:bold"
                })
                add(Label(" - []"))
                add(HBox().apply { HBox.setHgrow(this, Priority.ALWAYS) })
                add(min)
                add(max)
                add(Button("", FontIcon.of(MaterialDesign.MDI_WINDOW_CLOSE, 17)).apply {
                    styleClass.add("close-button")
                    onAction = EventHandler {
                        stage.close()
                    }
                })
            }
        }
        stage.scene = SnapScene(stage, VBox().apply {
            stylesheets.add("/knotbook.css")
            prefWidth = 800.0
            prefHeight = 600.0
            children.add(mover)
            children.add(HBox().apply {
                children.add(VBox().apply {
                    background = Background(BackgroundFill(Color.WHITE, null, null))
                    prefWidth = 300.0
                    minWidth = 300.0
                    alignment = Pos.TOP_CENTER
                })
                children.add(knotable)
            })
        }).apply {
            setMoveControl(mover)
            min.onAction = EventHandler {
                minimise()
            }

            max.onAction = EventHandler {
                maximise()
            }
            Platform.runLater {
                maximise()
            }
        }

        knotable.requestFocus()
        stage.show()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}