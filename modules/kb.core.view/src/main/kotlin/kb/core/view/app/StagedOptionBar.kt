package kb.core.view.app

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.stage.Popup
import javafx.stage.Stage
import kb.core.fx.*
import kb.core.icon.fontIcon
import kb.core.view.util.PrettyListView
import org.kordamp.ikonli.materialdesign.MaterialDesign

@Suppress("MemberVisibilityCanBePrivate")
class StagedOptionBar {

    companion object {
        const val kOBWidth = 560.0
        const val kOBHeight = 400.0
    }

    private val hintProperty = SimpleStringProperty("Search a Command")

    fun getHint(): String {
        return hintProperty.get()
    }

    fun setHint(hint: String?) {
        hintProperty.set(hint)
    }

    val popup = Popup()

    val lv = PrettyListView<Entity>().apply {
        vgrow()
        isFocusTraversable = false
        items = getList().observable()
        selectionModel.select(0)
        setCellFactory {
            EntityListCell()
        }
    }

    val tf = textField {
        promptTextProperty().bind(hintProperty)
        this.setOnKeyPressed {
            when {
                it.code == KeyCode.UP -> {
                    val i = lv.selectionModel.selectedIndex
                    if (i != 0) {
                        lv.selectionModel.select(i - 1)
                        lv.scrollTo(i - 8)
                    }
                    it.consume()
                }
                it.code == KeyCode.DOWN -> {
                    val i = lv.selectionModel.selectedIndex
                    if (i != lv.items.size - 1) {
                        lv.selectionModel.select(i + 1)
                        lv.scrollTo(i - 6)
                    }
                    it.consume()
                }
                it.code == KeyCode.ESCAPE -> popup.hide()
                it.code == KeyCode.ENTER -> popup.hide()
            }

        }
        styleClass("formula-field")
    }

    val container = vbox {
        effect = DropShadow().apply {
            color = Color.GRAY
            blurType = BlurType.GAUSSIAN
            height = 10.0
            width = 10.0
            radius = 10.0
            offsetY = 5.0
        }
        styleClass("option-bar")
        prefWidth = kOBWidth
        maxHeight = kOBHeight

        add(hbox {
            align(Pos.CENTER)
            padding = Insets(6.0)
            spacing = 6.0
            add(tf.hgrow())
            add(button {
                styleClass("ok-button")
                graphic = fontIcon(MaterialDesign.MDI_CHECK, 14)
                isFocusTraversable = false
                setOnAction {
                    popup.hide()
                }
            })
        })
        add(lv)
    }

    fun setTheme(styles: List<String>) {
        container.stylesheets.setAll(styles)
    }

    fun show(stage: Stage, menuBarHeight: Double) {
        if (popup.isShowing) {
            popup.hide()
        }
        popup.content.setAll(container)
        popup.isAutoHide = true
        popup.x = stage.x + stage.width / 2.0 - kOBWidth / 2.0 - 10.0 // fix centering
        popup.y = stage.y + stage.scene.y + menuBarHeight - 5.0
        popup.show(stage)
    }
}