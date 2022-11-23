package project.turn;

public class Turn {
    private boolean isTurnOfPlayer1 = true;
    private boolean isTurnOfPlayer2 = false;
    private int countTurn = 0;

    public Turn() {
    }

    public Turn(Turn turn) {
        this.isTurnOfPlayer1 = turn.isTurnOfPlayer1;
        this.isTurnOfPlayer2 = turn.isTurnOfPlayer2;
    }

    public int getCountTurn() {
        return countTurn;
    }

    public void nextTurn() {
        if (isTurnOfPlayer1) {
            isTurnOfPlayer1 = false;
            isTurnOfPlayer2 = true;
        } else {
            isTurnOfPlayer1 = true;
            isTurnOfPlayer2 = false;
        }
        countTurn++;
    }

    public byte whoNext() {
        if (isTurnOfPlayer1)
            return 1;
        else
            return 2;
    }
}
