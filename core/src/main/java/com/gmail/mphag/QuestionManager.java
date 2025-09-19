package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionManager {

    private List<Question> list  = new ArrayList<>();
    private final Random random;

    private final BitmapFont font;

    private Question currentQuestion;
    private boolean questionIsUnderways;

    private boolean hasAnswered;
    private String answer;
    private long timeAnswered;
    private boolean questionDone;
    private boolean requestFreePick;


    public QuestionManager() {
        this.font = new BitmapFont(Gdx.files.internal("questionFont.fnt"));
        this.random = new Random();
        loadQuestions();
    }

    public void loadQuestions() {
        list.add(new Question("Hvad fejrer vi til jul?", "Jesu fødsel", "Jesu død", "Jesu dåb", "Jesu fødsel"));
        list.add(new Question("Hvor blev Jesus født?", "Betlehem", "Nazaret", "Jerusalem", "Betlehem"));
        list.add(new Question("Hvad fejrer vi til påske?", "Jesu opstandelse", "Jesu fødsel", "Jesu himmelfart", "Jesu opstandelse"));
        list.add(new Question("Hvad fejrer vi til pinse?", "At Helligånden kom til disciplene", "Jesu fødsel", "Skabelsen", "At Helligånden kom til disciplene"));
        list.add(new Question("Hvad symboliserer brødet ved nadveren?", "Jesu krop", "Jesu blod", "Guds ord", "Jesu krop"));
        list.add(new Question("Hvad symboliserer vinen ved nadveren?", "Jesu blod", "Jesu krop", "Helligånden", "Jesu blod"));
        list.add(new Question("Hvem døde på korset?", "Jesus", "Moses", "Abraham", "Jesus"));
        list.add(new Question("Hvem blev kastet i løvekulen?", "Daniel", "Josef", "Moses", "Daniel"));
        list.add(new Question("Hvem byggede arken?", "Noa", "Abraham", "David", "Noa"));
        list.add(new Question("Hvad betyder ordet 'Amen'?", "Sådan ske det", "Tak", "Farvel", "Sådan ske det"));
        list.add(new Question("Hvilken bøn lærte Jesus sine disciple?", "Fadervor", "Trosbekendelsen", "Nadverbønnen", "Fadervor"));
        list.add(new Question("Hvem gik på vandet?", "Jesus", "Moses", "Peter", "Jesus"));

    }

    public void drawImages(SpriteBatch spriteBatch) {
        if(!questionIsUnderways) {
            return;
        }

        spriteBatch.begin();

        Utils.drawStringCentered(
            font,
            spriteBatch,
            currentQuestion.getAnswer1(),
            (int) (Settings.GAME_WIDTH/2 - Settings.GAME_WIDTH / 4 - Settings.TILE_WIDTH + Settings.TILE_WIDTH),
            (int) (Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT + Settings.TILE_HEIGHT/2));

        Utils.drawStringCentered(
            font,
            spriteBatch,
            currentQuestion.getAnswer2(),
            (int) (Settings.GAME_WIDTH/2 - Settings.TILE_WIDTH + Settings.TILE_WIDTH),
            (int) (Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT + Settings.TILE_HEIGHT/2));

        Utils.drawStringCentered(
            font,
            spriteBatch,
            currentQuestion.getAnswer3(),
            (int) (Settings.GAME_WIDTH/2 + Settings.GAME_WIDTH / 4 - Settings.TILE_WIDTH + Settings.TILE_WIDTH),
            (int) (Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT + Settings.TILE_HEIGHT/2));

        Utils.drawStringCentered(
            font,
            spriteBatch,
            currentQuestion.getQuestion(),
            (int) (Settings.GAME_WIDTH/2 - (Settings.TILE_WIDTH * 4)/2 + (Settings.TILE_WIDTH * 4)/2),
            (int) (Settings.GAME_HEIGHT/2 + Settings.TILE_HEIGHT - Settings.TILE_HEIGHT/2 + Settings.TILE_HEIGHT/2));

        spriteBatch.end();

    }

    public void drawShapes(ShapeRenderer shapeRenderer) {
        if(!questionIsUnderways) {
            return;
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(!hasAnswered) {
            shapeRenderer.setColor(Color.WHITE);
        }

        if(hasAnswered) {
            if(currentQuestion.getAnswer1().equals(currentQuestion.getCorrectAnswer())) {
                shapeRenderer.setColor(Color.LIME);
            } else {
                shapeRenderer.setColor(Color.RED);
            }
        }
        //Boks 1
        shapeRenderer.rect(
            Settings.GAME_WIDTH/2 - Settings.GAME_WIDTH / 4 - Settings.TILE_WIDTH,
            Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT,
            Settings.TILE_WIDTH * 2,
            Settings.TILE_HEIGHT);

        if(hasAnswered) {
            if(currentQuestion.getAnswer2().equals(currentQuestion.getCorrectAnswer())) {
                shapeRenderer.setColor(Color.LIME);
            } else {
                shapeRenderer.setColor(Color.RED);
            }
        }
        //Boks 2
        shapeRenderer.rect(
            Settings.GAME_WIDTH/2 - Settings.TILE_WIDTH,
            Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT,
            Settings.TILE_WIDTH * 2,
            Settings.TILE_HEIGHT);


        if(hasAnswered) {
            if(currentQuestion.getAnswer3().equals(currentQuestion.getCorrectAnswer())) {
                shapeRenderer.setColor(Color.LIME);
            } else {
                shapeRenderer.setColor(Color.RED);
            }
        }
        //Boks 3
        shapeRenderer.rect(
            Settings.GAME_WIDTH/2 + Settings.GAME_WIDTH / 4 - Settings.TILE_WIDTH,
            Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT,
            Settings.TILE_WIDTH * 2,
            Settings.TILE_HEIGHT);


        shapeRenderer.setColor(Color.WHITE);

        shapeRenderer.rect(
            Settings.GAME_WIDTH/2 - (Settings.TILE_WIDTH * 4)/2,
            Settings.GAME_HEIGHT/2 + Settings.TILE_HEIGHT - Settings.TILE_HEIGHT/2,
            Settings.TILE_WIDTH * 4,
            Settings.TILE_HEIGHT);

        shapeRenderer.end();
    }

    public void logic() {
        if(!questionIsUnderways) {
            return;
        }

        if(!hasAnswered) {
            return;
        }

        if(answer.equals(currentQuestion.getCorrectAnswer())) {
            finishQuestion();
            requestFreePick = true;
            return;
        }

        if(TimeUtils.timeSinceMillis(timeAnswered) > 2000) {
            finishQuestion();
        }

    }

    private void finishQuestion() {
        System.out.println("Finish");
        questionDone = true;
        questionIsUnderways = false;
    }

    public void input() {
        if(!questionIsUnderways) {
            return;
        }

        if(!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        if(hasAnswered) {
            return;
        }

        int mouseX = Gdx.input.getX();
        int mouseY = Settings.GAME_HEIGHT - Gdx.input.getY();

        if(mouseIsInAnswerOne(mouseX, mouseY)) {
            hasAnswered = true;
            answer = currentQuestion.getAnswer1();
            timeAnswered = TimeUtils.millis();
        } else if(mouseIsInAnswerTwo(mouseX, mouseY)) {
            hasAnswered = true;
            answer = currentQuestion.getAnswer2();
            timeAnswered = TimeUtils.millis();
        } else if(mouseIsInAnswerThree(mouseX, mouseY)) {
            hasAnswered = true;
            answer = currentQuestion.getAnswer3();
            timeAnswered = TimeUtils.millis();
        }
    }

    private boolean mouseIsInAnswerThree(int mouseX, int mouseY) {
        return mouseX > Settings.GAME_WIDTH/2 + Settings.GAME_WIDTH / 4 - Settings.TILE_WIDTH &&
            mouseX < Settings.GAME_WIDTH/2 + Settings.GAME_WIDTH / 4 - Settings.TILE_WIDTH + Settings.TILE_WIDTH * 2 &&
            mouseY > Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT &&
            mouseY < Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT + Settings.TILE_HEIGHT;
    }

    private boolean mouseIsInAnswerTwo(int mouseX, int mouseY) {
        return mouseX > Settings.GAME_WIDTH/2 - Settings.TILE_WIDTH &&
                mouseX < Settings.GAME_WIDTH/2 - Settings.TILE_WIDTH + Settings.TILE_WIDTH * 2 &&
                mouseY > Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT &&
                mouseY < Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT + Settings.TILE_HEIGHT;
    }

    private boolean mouseIsInAnswerOne(int mouseX, int mouseY) {

        return mouseX > Settings.GAME_WIDTH/2 - Settings.GAME_WIDTH / 4 - Settings.TILE_WIDTH &&
                mouseX < Settings.GAME_WIDTH/2 - Settings.GAME_WIDTH / 4 - Settings.TILE_WIDTH + Settings.TILE_WIDTH * 2 &&
                mouseY > Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT &&
                mouseY < Settings.GAME_HEIGHT/2 - Settings.TILE_HEIGHT + Settings.TILE_HEIGHT;
    }

    public void askQuestion() {
        if(questionIsUnderways) {
            return;
        }

        int length = list.size();

        int i = random.nextInt(0, length);

        this.answer = "";
        this.hasAnswered = false;
        this.timeAnswered = 0;
        this.currentQuestion = list.get(i);
        this.questionIsUnderways = true;
        this.questionDone = false;
    }

    public boolean isQuestionDone() {
        return questionDone;
    }

    public boolean isWaitingForFreePick() {
        return requestFreePick;
    }
}
