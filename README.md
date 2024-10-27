# Minesweeper Game

## Overview

This is a simple implementation of the classic Minesweeper game built with Kotlin and Jetpack Compose. The game allows players to uncover tiles on a grid while avoiding mines. The game features a user-friendly interface and provides a fun way to test your logic and deduction skills.

## Features

- **Dynamic Board Generation**: The game generates a grid with a specified number of mines.
- **Neighboring Mines Count**: Each tile displays the number of neighboring mines when revealed.
- **Flagging System**: Players can flag tiles they suspect contain mines.
- **Visual Indicators**: Different colors and symbols indicate safe areas, mines, and flagged tiles.
- **Responsive Design**: The game interface is designed to be responsive and centered on the screen.

## Getting Started

### Prerequisites

- Android Studio
- Kotlin
- Jetpack Compose

### Installation

1. **Clone the repository**:

2. **Open the project in Android Studio**.

3. **Sync the project** to download the required dependencies.

4. **Run the application** on an emulator or physical device.

## Usage

1. Click on any tile to reveal it.
2. If the tile contains a mine, the game is over.
3. If the tile is safe, it will display the number of neighboring mines.
4. You can flag a tile by clicking on it to indicate that you suspect it contains a mine.
5. The game continues until all safe tiles are revealed or a mine is triggered.

## Code Structure

The project consists of the following key components:

- **`Cell.kt`**: Data class representing a cell in the Minesweeper grid, including properties for mine status, neighboring mines count, and revealed state.
- **`MinesweeperGrid.kt`**: Contains the logic for generating the game board and calculating neighboring mines.
- **`MinesweeperViewModel.kt`**: Composable functions for rendering the game interface and handling user interactions.
- **Resources**: Includes image resources used for the game interface.

## Contributing

Contributions are welcome! If you have suggestions for improvements or new features, feel free to submit a pull request or open an issue.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Inspired by the classic Minesweeper game.
- Built with the help of Jetpack Compose for a modern UI experience.

