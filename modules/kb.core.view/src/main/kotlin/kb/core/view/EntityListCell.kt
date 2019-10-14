package kb.core.view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ListCell
import javafx.scene.paint.Color
import kb.core.fx.add
import kb.core.fx.hbox
import kb.core.fx.label
import kb.core.icon.centered

@Suppress("DuplicatedCode")
class EntityListCell : ListCell<Entity>() {

    override fun updateItem(item: Entity?, empty: Boolean) {
        super.updateItem(item, empty)

        prefHeight = 24.0
        if (item == null || empty) {
            graphic = null
            setOnMouseClicked {
            }
        } else {
            alignment = Pos.CENTER_LEFT
            graphic = hbox {
                padding = Insets(0.0, 8.0, 0.0, 8.0)
                alignment = Pos.CENTER_LEFT
                spacing = 4.0
                if (item.icon != null) {
                    add(item.icon.centered(20))
                } else {
                    add(hbox {
                        prefWidth = 20.0
                    })
                }
                if (item.cat != null) {
                    add(label {
                        text = item.cat
                        alignment = Pos.CENTER_LEFT
                        textFill = Color.valueOf("#3c5c94")
//                    style = "-fx-font-weight:700"
                    })
                }
                if (item.name != null) {
                    add(label {
                        text = item.name
                    })
                }
            }
        }
    }
}