package hu.ait.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.ait.minesweeper.ui.theme.MineSweeperScreen.MineSweeperViewModel
import hu.ait.minesweeper.ui.theme.MineSweeperScreen.Tile
import hu.ait.minesweeper.ui.theme.MineSweeperScreen.createMinefield

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinefieldGame()
        }
    }
}

@Composable
fun MinefieldGame() {
    val gridDimension = 5
    val totalMines = 5
    var grid by remember { mutableStateOf(createMinefield(gridDimension, totalMines)) }
    var firstMove by remember { mutableStateOf(true) }
    var statusMessage by remember { mutableStateOf("Begin the game!") }
    var flaggingMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "MineSweeper Game",
            fontSize = 32.sp,
            color = Color(0xFFFFC0CB),
            modifier = Modifier.padding(16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Flagging Mode",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )

            Checkbox(
                checked = flaggingMode,
                onCheckedChange = { flaggingMode = it }
            )
        }

        MineSweeperViewModel(grid) { x, y ->
            if (flaggingMode) {
                grid = grid.mapIndexed { rowIndex, rowList ->
                    rowList.mapIndexed { colIndex, tile ->
                        if (rowIndex == x && colIndex == y) {
                            tile.copy(isFlagged = !tile.isFlagged)
                        } else {
                            tile
                        }
                    }
                }
            } else {
                if (firstMove) {
                    if (grid[x][y].isMine) {
                        statusMessage = "Oh no! You hit a mine!"
                        grid = exposeAllMines(grid)
                    } else {
                        grid = handleFirstMove(grid, x, y)
                        firstMove = false
                    }
                } else {
                    if (grid[x][y].isMine) {
                        statusMessage = "Oh no! You hit a mine!"
                        grid = exposeAllMines(grid)
                    } else {
                        grid = grid.mapIndexed { rowIndex, rowList ->
                            rowList.mapIndexed { colIndex, tile ->
                                if (rowIndex == x && colIndex == y) {
                                    tile.copy(isRevealed = true)
                                } else {
                                    tile
                                }
                            }
                        }
                    }
                }
            }
        }

        Text(
            text = statusMessage,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            grid = createMinefield(gridDimension, totalMines)
            firstMove = true
            statusMessage = "Begin the game!"
        }, colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray
        )) {
            Text(text = "Restart Game", color = Color.White)
        }
    }
}

fun exposeAllMines(grid: List<List<Tile>>): List<List<Tile>> {
    return grid.map { row ->
        row.map { tile ->
            if (tile.isMine) {
                tile.copy(isRevealed = true)
            } else {
                tile
            }
        }
    }
}

fun handleFirstMove(grid: List<List<Tile>>, x: Int, y: Int): List<List<Tile>> {
    val newGrid = grid.map { it.toMutableList() }.toMutableList()
    uncoverCells(newGrid, x, y)
    return newGrid
}

fun uncoverCells(grid: MutableList<MutableList<Tile>>, x: Int, y: Int) {
    if (x !in grid.indices || y !in grid[x].indices || grid[x][y].isRevealed) return
    grid[x][y] = grid[x][y].copy(isRevealed = true)

    val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1, 0 to 1,
        1 to -1, 1 to 0, 1 to 1
    )

    for ((dx, dy) in directions) {
        val newX = x + dx
        val newY = y + dy
        if (newX in grid.indices && newY in grid[newX].indices && !grid[newX][newY].isMine) {
            grid[newX][newY] = grid[newX][newY].copy(isRevealed = true)
        }
    }
}

fun countAdjacentMines(grid: List<List<Tile>>, x: Int, y: Int): Int {
    val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1, 0 to 1,
        1 to -1, 1 to 0, 1 to 1
    )
    return directions.count { (dx, dy) ->
        val newX = x + dx
        val newY = y + dy
        newX in grid.indices && newY in grid[newX].indices && grid[newX][newY].isMine
    }
}

