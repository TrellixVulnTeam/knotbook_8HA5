package kb.core.view

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import kb.core.fx.fontIcon
import kb.core.fx.runOnFxThread
import kb.core.icon.FontIcon
import org.kordamp.ikonli.materialdesign.MaterialDesign.*

@Suppress("unused")
class IndexTree {
    val tree = TreeView<Entity>()

//    private val memoryRepository = MemoryRepository()
//
//    private val repositories = listOf(
//            memoryRepository,
//            FSRepository()
//    )
//
//    private val propertyGroups = mutableListOf(view.identityPane)
//
//    private fun setModel(model: ViewModel) {
//        if (window.trySetModel(model)) {
//            propertyGroups.clear()
//            propertyGroups.add(view.identityPane)
//            propertyGroups.addAll(model.getPropertyGroups())
//            view.propertiesBox.panes.setAll(propertyGroups.map { it.pane })
//        }
//    }

    private fun selectAndFocus(item: TreeItem<Entity>) {
        tree.selectionModel.select(item)
        runOnFxThread {
            tree.requestFocus()
        }
    }

//    private fun selectAndSet(item: TreeItem<Index>) {
//        selectAndFocus(item)
//        item.value.model.get()?.let { setModel(it) }
//    }

    @Suppress("UNUSED_PARAMETER")
    private fun addAndSelect(index: Entity) {
        // Requires item to be added to repo AFTER, not BEFORE
//        val item = TreeItem(index)
//        val repoIndex = repositories.indexOf(index.repository)
//        if (repoIndex == -1) return
//        val topLevelContextSize = index.repository.tables[""]?.size ?: 0
//        if (index.context.isEmpty()) {
//            root.children[repoIndex].children.add(topLevelContextSize, item)
//        } else {
//            val tableIndex = index.repository.tables.keys.sorted().indexOf(index.context)
//            if (tableIndex == -1) return
//            root.children[repoIndex].children[topLevelContextSize + tableIndex - 1].children.add(item)
//        }
//        selectAndSet(item)
    }

    private val entityRoot = Entity(EntityText("Application"), FontIcon.of(MDI_APPLICATION), null, mutableListOf())
    private val root = TreeItem<Entity>(null)

//    init {
//        setContentView(view.splitPane)
//        view.openButton.onMouseClicked = EventHandler {
//            val chooser = FileChooser()
//            chooser.title = "Open"
//            chooser.initialDirectory = File(System.getProperty("user.home") + "/Desktop")
//            chooser.extensionFilters.addAll(
//                    FileChooser.ExtensionFilter("CSV", "*.csv")
//            )
//            val res: File? = chooser.showOpenDialog(view.openButton.scene.window)
//            if (res != null && res.extension.toLowerCase() == "csv") {
//                try {
//                    val data = DataFrame.readDelim(res.inputStream().reader().buffered(),
//                            CSVFormat.DEFAULT.withHeader().withNullString(""))
//                    val model = TableViewModel(data)
//                    val item = Index(res.name, fontIcon(TABLE, 18), memoryRepository, "", true, model)
//                    addAndSelect(item)
//                    memoryRepository.add(item)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }

    init {
        entityRoot.children?.addAll(listOf(
                Entity(EntityText("In-Memory Data"), fontIcon(MDI_MEMORY, 14), null, (0..15).flatMap {
                    listOf(
                        Entity("Empty View", fontIcon(MDI_BORDER_NONE, 14)),
                        Entity(EntityText("2018iri.csv"), fontIcon(MDI_FILE_DELIMITED, 14), null, mutableListOf(
                                Entity("Filesystem Link", fontIcon(MDI_LINK, 14)),
                                Entity("User Edit Mask", fontIcon(MDI_PENCIL, 14)),
                                Entity("Formulas", fontIcon(MDI_FUNCTION, 14)),
                                Entity(EntityText("Filter"), fontIcon(MDI_FILTER, 14), "Team={865}"),
                                Entity(EntityText("Sort", bold = true), fontIcon(MDI_SORT_ASCENDING, 14), "Scale"),
                                Entity(EntityText("Sort", bold = true), fontIcon(MDI_SORT_DESCENDING, 14), "Switch"),
                                Entity(EntityText("Colour Scale", bold = true), fontIcon(MDI_GRADIENT, 14), "Auto Switch")
                        )))
                }.toMutableList()
                ),
                Entity(EntityText("Local Folder", bold = true), fontIcon(MDI_MONITOR, 14), "~/KB/repo"),
                Entity("Android Scouting App", fontIcon(MDI_QRCODE, 14)),
                Entity("The Blue Alliance", fontIcon(MDI_LAMP, 14))
        ))
        tree.setCellFactory { EntityCell() }
        tree.root = root
        tree.isShowRoot = false
        regenerate()
    }

    private fun regenerate() {
        regenerateChildren(root, entityRoot)
    }

    private fun regenerateChildren(root: TreeItem<Entity>, entityRoot: Entity) {
        if (entityRoot.children != null && entityRoot.children.isNotEmpty()) {
            root.children.setAll(entityRoot.children.map { entity ->
                TreeItem(entity).also { item ->
                    regenerateChildren(item, entity)
                }
            })
            root.isExpanded = true
        }
    }
}