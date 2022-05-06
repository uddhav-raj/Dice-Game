
public class Player {
    String playerId;
    int playerState;
    int score;
    int rank;
    /*
    playerId: The ID assigned at the start Of The Game.
    playerState: Four states:
                 0 -> Can't Play this turn. (Consecutive 1s in previous turns)
                 1 -> Ready to Play.
                 2 -> Got 1 in the last turn.
                 3 -> Player has won.
    score: Total score obtained uptil now.        
    */
    Player(String playerId){
        this.playerId = playerId;
        this.playerState = 1;
        this.score = 0;
        this.rank = 0;
    }

    public void increaseScore(int diceRoll){
        this.score = this.score + diceRoll;
    }

    public void playerHasWon(){
        this.playerState = 3;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public void playerGotOneThisTurn(){
        this.playerState = 2;
    }

    public void playerCanPlayNextTurn(){
        this.playerState = 1;
    }

    public void playerGotConsecutiveOnes(){
        this.playerState = 0;
    }

    
}
