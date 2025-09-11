package com.gmail.mphag;

public class ActionManager {

    private final PlayerManager playerManager;
    private final BoardManager boardManager;
    private final QuestionManager questionManager;

    private boolean executingAction = false;
    private boolean isAwaitingInput = false;

    public ActionManager(PlayerManager playerManager, BoardManager boardManager, QuestionManager questionManager) {
        this.playerManager = playerManager;
        this.boardManager = boardManager;
        this.questionManager = questionManager;
    }

    public void update() {
        if(!executingAction) {
            return;
        }
        switch(playerManager.getCurrentPlayer().getCurrentSpinType()) {
            case DEMON:
                //Await input
                break;
            case ADD_ANGEL:
                //Await input
                break;

            case REMOVE_ANGEL:
                //Await input
                break;
            case SHOOT_WITH_ANGEL:
                //Await input
                break;
            default:
                break;
        }
    }

    public void input() {
        if(!isAwaitingInput) {
            return;
        }

        System.out.println("through input");

    }

    public void draw() {
        if(!executingAction) {
            return;
        }
        boardManager.resetHighlighting();
        switch(playerManager.getCurrentPlayer().getCurrentSpinType()) {
            case DEMON:
                boardManager.highlightDemonTiles(playerManager.getCurrentPlayer());
                break;
            case ADD_ANGEL:
                boardManager.highlightAngelTiles(playerManager.getCurrentPlayer());
                break;
            case REMOVE_ANGEL:
                boardManager.highlightOpponentAngels(playerManager.getCurrentPlayer());
                break;
            case SHOOT_WITH_ANGEL:
                boardManager.highlightAngels(playerManager.getCurrentPlayer());
                break;
            default:
                break;
        }
    }

    public void executeAction() {
        this.executingAction = true;
        Player currentPlayer = playerManager.getCurrentPlayer();
        SpinType currentSpinType = currentPlayer.getCurrentSpinType();

        switch(currentSpinType) {
            case NOTHING:
                playerManager.switchTurn();
                break;
            case QUESTION:
                questionManager.askQuestion();
                break;
            //
            case MOVE_DEMONS:
                boardManager.moveAllDemons();
                finishAction();
                break;

            //Disse skal alle sammen vente på player input, som bliver støttet i draw og update ift hvilken en af dem det er:
            case DEMON:
            case SHOOT_WITH_ANGEL:
            case ADD_ANGEL:
            case REMOVE_ANGEL:
                isAwaitingInput = true;
                break;

            default:
                break;
        }


    }

    private void finishAction() {
        this.executingAction = false;
        playerManager.switchTurn();
        Core.GAME_STATE = GameState.WAITING_FOR_ROLL;
    }


}
