import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class PuzzlerX {
    private static final int GRID_SIZE = 4;
    private static final int EMPTY_CELL_VALUE = 0;
    private static final int WINNING_SCORE = 15;

    private int[][] puzzleGrid;
    private int score;

    public PuzzlerX() {
        puzzleGrid = new int[GRID_SIZE][GRID_SIZE];
        score = 0;
    }

    public void startGame() {
        initializeGrid();
        printGrid();

        Scanner scanner = new Scanner(System.in);
        while (!isComplete()) {
            System.out.print("Enter number to move: ");
            int number = scanner.nextInt();

            moveNumber(number);
            printGrid();
        }

        System.out.println("Congratulations! You completed the puzzle with a score of " + score);
    }

    private void initializeGrid() {
        int[] numbers = new int[GRID_SIZE * GRID_SIZE - 1];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        shuffleArray(numbers);

        int count = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (count < numbers.length) {
                    puzzleGrid[row][col] = numbers[count];
                    count++;
                } else {
                    puzzleGrid[row][col] = EMPTY_CELL_VALUE;
                }
            }
        }
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private void printGrid() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                System.out.print(puzzleGrid[row][col] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isComplete() {
        int count = 1;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (puzzleGrid[row][col] != count) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    public void moveNumber(int number) {
        int[] emptyCell = findEmptyCell();

        if (emptyCell != null) {
            int row = emptyCell[0];
            int col = emptyCell[1];

            if (isValidMove(row, col, number)) {
                int[] numberCell = findNumberCell(number);
                if (numberCell != null) {
                    int numberRow = numberCell[0];
                    int numberCol = numberCell[1];

                    puzzleGrid[row][col] = number;
                    puzzleGrid[numberRow][numberCol] = EMPTY_CELL_VALUE;
                    score++;
                } else {
                    System.out.println("Number not found in the grid!");
                }
            } else {
                System.out.println("Invalid move!");
            }
        } else {
            System.out.println("Empty cell not found!");
        }
    }

    private int[] findEmptyCell() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (puzzleGrid[row][col] == EMPTY_CELL_VALUE) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private boolean isValidMove(int row, int col, int number) {
        return ((row > 0 && puzzleGrid[row - 1][col] == number) ||
                (row < GRID_SIZE - 1 && puzzleGrid[row + 1][col] == number) ||
                (col > 0 && puzzleGrid[row][col - 1] == number) ||
                (col < GRID_SIZE - 1 && puzzleGrid[row][col + 1] == number));
    }

    private int[] findNumberCell(int number) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (puzzleGrid[row][col] == number) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        PuzzlerX puzzlerX = new PuzzlerX();
        puzzlerX.startGame();
    }
}
