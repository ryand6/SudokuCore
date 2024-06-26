package com.github.ryand6.sudokuGenerator;

public interface SudokuValidation {

    /*
    Check that the value in the cell doesn't violate Sudoku rules
     */
    default boolean validateCell(int[][]grid, int val, int row, int col) {

        // Get cell position of upper left corner of block value is in
        int blockRow = (int) Math.floor(row / 3) * 3;
        int blockCol = (int) Math.floor(col / 3) * 3;

        // Check column
        for (int x = 0; x < 9; x++) {
            if (grid[row][x] == val) {
                return false;
            }
        }

        // Check row
        for (int y = 0; y < 9; y++) {
            if (grid[y][col] == val) {
                return false;
            }
        }

        // Check block
        for (int p = 0; p < 3; p++) {
            for (int q = 0; q < 3; q++) {
                if (grid[blockRow + p][blockCol + q] == val) {
                    return false;
                }
            }
        }

        return true;
    }

    default boolean validateGrid(int[][] grid) {
        // Confirm rows and columns
        for (int i = 0; i < 9; i++) {
            if (!isValidArray(grid[i]) || !isValidArray(getColumn(grid, i))) {
                return false;
            }
        }

        // Confirm blocks
        for (int bRow = 0; bRow < 3; bRow++) {
            for (int bCol = 0; bCol < 3; bCol++) {
                if (!validateBlock(grid, bRow * 3, bCol * 3)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validateBlock(int[][] grid, int row, int col) {
        // Store presence of cell values in array
        boolean[] blockNums = new boolean[10];

        for (int p = 0; p < 3; p++) {
            for (int q = 0; q < 3; q++) {
                int num = grid[row + p][col + q];
                // Completed board should not contain 0 as this represents empty cell
                if (num == 0) {
                    return false;
                }
                // Check if number is repeated in block
                if (blockNums[num]) {
                    return false;
                }
                blockNums[num] = true;
                }
            }
        return true;
    }

    private boolean isValidArray(int[] array) {
        boolean[] presence = new boolean[10];

        for (int num : array) {
            // Completed board should not contain 0 as this represents empty cell
            if (num == 0) {
                 return false;
            }
            if (presence[num]) {
                return false;
            }
            presence[num] = true;
        }
        return true;
    }

    private int[] getColumn(int[][] grid, int colIndex) {
        int[] column = new int[9];
        for (int i = 0; i < 9; i++) {
            column[i] = grid[i][colIndex];
        }
        return column;
    }

}
