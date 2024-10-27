package hu.ait.minesweeper.ui.theme.MineSweeperScreen

import hu.ait.minesweeper.countAdjacentMines
import kotlin.random.Random

data class Tile(
    val isMine: Boolean = false,
    val neighboringMines: Int = 0,
    val isRevealed: Boolean = false,
    val isFlagged: Boolean = false
)

fun createMinefield(size: Int, mineCount: Int): List<List<Tile>> {
    val grid = MutableList(size) { MutableList(size) { Tile() } }
    var minesCount = 0

    while (minesCount < mineCount) {
        val row = Random.nextInt(size)
        val col = Random.nextInt(size)
        if (!grid[row][col].isMine) {
            grid[row][col] = grid[row][col].copy(isMine = true)
            minesCount++
        }
    }

    for (row in 0 until size) {
        for (col in 0 until size) {
            if (!grid[row][col].isMine) {
                val neighboringMines = countAdjacentMines(grid, row, col)
                grid[row][col] = grid[row][col].copy(neighboringMines = neighboringMines)
            }
        }
    }

    return grid
}

