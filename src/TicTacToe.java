import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TicTacToe extends Application {
    private static final int BOARD_SIZE = 5;
    private static final int CELL_SIZE = 100;
    private static final int WINDOW_WIDTH = BOARD_SIZE * CELL_SIZE;
    private static final int WINDOW_HEIGHT = (BOARD_SIZE + 1) * CELL_SIZE;

    private Button[][] board;
    private boolean playerOneTurn = true;
    private Label winnerLabel = new Label("");

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        board = new Button[BOARD_SIZE][BOARD_SIZE];


        // Add winnerLabel to the grid
        root.add(winnerLabel, 0, BOARD_SIZE + 1, BOARD_SIZE, 1);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button cell = new Button();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);
                cell.setOnAction(e -> {
                    if (cell.getText().isEmpty()) {
                        cell.setText(playerOneTurn ? "X" : "O");
                        checkForWinner();
                        playerOneTurn = !playerOneTurn;
                    }
                });
                cell.setStyle("-fx-font-size: 3em; -fx-font-weight: bold;");

                board[row][col] = cell;
                root.add(cell, col, row);
            }
        }

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void checkForWinner() {
        // Check rows
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (!board[row][0].getText().isEmpty() &&
                board[row][0].getText().equals(board[row][1].getText()) &&
                board[row][0].getText().equals(board[row][2].getText()) &&
                board[row][0].getText().equals(board[row][3].getText()) &&
                board[row][0].getText().equals(board[row][4].getText())) {
                announceWinner();
                return;
            }
        }

        // Check columns
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (!board[0][col].getText().isEmpty() &&
                board[0][col].getText().equals(board[1][col].getText()) &&
                board[0][col].getText().equals(board[2][col].getText()) &&
                board[0][col].getText().equals(board[3][col].getText()) &&
                board[0][col].getText().equals(board[4][col].getText())) {
                announceWinner();
                return;
            }
        }

        // Check diagonal
        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[0][0].getText().equals(board[2][2].getText()) &&
            board[0][0].getText().equals(board[3][3].getText()) &&
            board[0][0].getText().equals(board[4][4].getText())) {
            announceWinner();
            return;
            }
                // Check reverse diagonal
    if (!board[0][4].getText().isEmpty() &&
            board[0][4].getText().equals(board[1][3].getText()) &&
            board[0][4].getText().equals(board[2][2].getText()) &&
            board[0][4].getText().equals(board[3][1].getText()) &&
            board[0][4].getText().equals(board[4][0].getText())) {
        announceWinner();
        return;
    }

    // Check for tie
    boolean isTie = true;
    for (int row = 0; row < BOARD_SIZE; row++) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col].getText().isEmpty()) {
                isTie = false;
                break;
            }
        }
        if (!isTie) {
            break;
        }
    }

    if (isTie) {
        winnerLabel.setText("Tie Game");
        disableBoard();
    }
}

private void announceWinner() {
    String winner = playerOneTurn ? "Player 1" : "Player 2";
    winnerLabel.setText(winner + " wins!");
    winnerLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center;");
    winnerLabel.setMinWidth(winnerLabel.getParent().getBoundsInLocal().getWidth());
    disableBoard();

    // Delay for 10 seconds before closing the window
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
        Stage stage = (Stage) board[0][0].getScene().getWindow();
        stage.close();
    }));
    timeline.play();
}


private void disableBoard() {
    for (Button[] row : board) {
        for (Button cell : row) {
            cell.setDisable(true);
        }
    }
}

public static void main(String[] args) {
    launch(args);
}
}