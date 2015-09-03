package com.example.kushagra.tictactoelab3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

    // Game State
    private boolean playerTurn = false; // Player Turn as Boolean false = X true = O
    private char board[][] = new char[3][3]; // 3 x 3 Array of Characters
    public int counter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupOnClickListeners();
        clearButton();
    }

    /**
     * Called when you press new game.
     */

    public void newGame(View view) {

        playerTurn = false;
        board = new char[3][3];
        counter = 0;
        clearButton();
    }

    /**
     * Clear each button in the game. Start New game!
     */

    private void clearButton() {
        // Using Table layout for each button to get position of buttons

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        for (int y = 0; y < tableLayout.getChildCount(); y++) {
            if (tableLayout.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) tableLayout.getChildAt(y);

                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setText("");
                        B.setEnabled(true);
                    }
                }
            }
        }
        TextView t = (TextView) findViewById(R.id.titleText);
        t.setText(R.string.title);
    }

    /**
     * Display the winner as 'X' or 'O'.
     */

    private boolean checkWin() {
        // Check if char winner is NONE
        char winner = '\0';
        if (checkWinner(board, 'X')) {
            winner = 'X';
        } else if (checkWinner(board, 'O')) {
            winner = 'O';
        }
        // If winner is NONE
        if (winner == '\0') {
            // And if counter is 8 i.e all buttons are disabled it means game is tied

            if(counter==8){
                TextView titleText = (TextView) findViewById(R.id.titleText);
                // Game tied
                titleText.setText("No body wins!");
                return true;
            }
            return false;
        }
        else {
            // Winner is displayed here
            TextView titleText = (TextView) findViewById(R.id.titleText);
            titleText.setText("'"+ winner + "'"+ " wins");
            return true;
        }
    }


    /**
     * To check if a player has won the tic tac toe game
     * @param board  the board of size 3 x 3
     * @param player the player, 'X' or 'O'
     * checkWinner returns true if 'X' or 'O' wins
     */

    private boolean checkWinner(char[][] board, char player) {

        // check for each column
        // size = 3
        for (int x = 0; x < 3; x++) {
            // Set total as zero
            int total = 0;

            for (int y = 0; y < 3; y++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= 3) {
                return true;
            }
        }

        // check for each row
        for (int y = 0; y < 3; y++) {
            int total = 0;
            for (int x = 0; x < 3; x++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= 3) {
                return true; // they win
            }
        }

        // forward diagonal
        int total = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (x == y && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= 3) {
            return true; // they win
        }

        // backward diagonal
        total = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (x + y == 3 - 1 && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= 3) {
            return true;
        }
        return false;
    }

    /**
     * Disables the buttons in the grid.
     */

    private void disableButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);

        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);

                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setEnabled(false);
                    }
                }
            }
        }
    }

    /**
     * Setting up listeners for all buttons in the table grid
     */
    private void setupOnClickListeners() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);

        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    View V = R.getChildAt(x);
                    V.setOnClickListener(new PlayOnClick(x, y));
                }
            }
        }
    }
    /**
     * OnClickListener for 'X' and 'O' after the button has been clicked or touched
     */

    private class PlayOnClick implements View.OnClickListener {

        private int x = 0;
        private int y = 0;

        public PlayOnClick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                Button B = (Button) view;
                if(playerTurn){
                    board[x][y] =  'O';
                }
                else {
                    board[x][y] = 'X';
                }
                if(playerTurn){
                    B.setText("O");
                }
                else
                    B.setText("X");

                B.setEnabled(false);
                playerTurn = !playerTurn;

                // check if anyone has won
                if (checkWin()) {
                    disableButtons();
                }

                else{
                    counter = counter + 1;
                }
            }
        }
    }
}