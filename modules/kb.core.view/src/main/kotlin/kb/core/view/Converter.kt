package kb.core.view

import kb.core.fx.observable
import kb.service.api.array.TableArray
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCellType
import java.util.*

fun TableArray.toGrid(): GridBase {
    val rows = rows
    val cols = cols
    val grid = GridBase(rows, cols)
    grid.setRowHeightCallback { 20.0 }
    grid.setResizableRows(BitSet())
    grid.rows.addAll((0 until rows).map { row ->
        (0 until cols).map { col ->
            SpreadsheetCellType.STRING.createCell(row, col, 1, 1, this.get(row, col))
        }.observable()
    })
    grid.rows.first().forEachIndexed { _, c ->
        c.style = "-fx-alignment: CENTER; -fx-background-color: rgba(240,240,240)"
    }
    return grid
}