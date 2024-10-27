package hu.ait.minesweeper.ui.theme.MineSweeperScreen

import androidx.compose.foundation.Image
import hu.ait.minesweeper.R
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MineSweeperViewModel(grid: List<List<Tile>>, onTileClick: (Int, Int) -> Unit) {
    Column {
        for (rowIndex in grid.indices) {
            Row {
                for (colIndex in grid[rowIndex].indices) {
                    MinefieldCell(
                        tile = grid[rowIndex][colIndex],
                        onClick = { onTileClick(rowIndex, colIndex) }
                    )
                }
            }
        }
    }
}

@Composable
fun MinefieldCell(tile: Tile, onClick: () -> Unit) {
    val backgroundColor = when {
        tile.isRevealed && tile.isMine -> Color.Red // Color for mines
        tile.isRevealed && tile.neighboringMines == 0 -> Color.Green // Color for "safe" areas
        else -> Color.Black // Default color for unrevealed cells
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClick)
            .background(backgroundColor), // Set the background color based on conditions
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.individual_cell),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        if (tile.isRevealed) {
            if (tile.isMine) {
                Text(text = "X", color = Color.White, fontSize = 24.sp) // Mine indicator
            } else if (tile.neighboringMines == 0) {
                Text(text = "Safe", color = Color.White, fontSize = 18.sp) // Safe area indicator
            } else {
                Text(text = "${tile.neighboringMines}", color = Color.White, fontSize = 18.sp) // Show number of neighboring mines
            }
        }

        // Display the flag if placed
        if (tile.isFlagged) {
            Text(text = "F", color = Color.Yellow, fontSize = 24.sp) // Flag indicator
        }
    }
}



