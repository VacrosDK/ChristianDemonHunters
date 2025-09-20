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
        list.add(new Question("Hvad fejrer vi jul?", "Jesu død", "Jesu fødsel", "Jesu dåb", "Jesu fødsel"));
        list.add(new Question("Hvor blev Jesus født?", "Nazaret", "Jerusalem", "Betlehem", "Betlehem"));
        list.add(new Question("Hvad fejrer vi påske?", "Jesu himmelfart", "Jesu opstandelse", "Jesu fødsel", "Jesu opstandelse"));
        list.add(new Question("Hvad fejrer vi pinse?", "Skabelsen", "Jesu fødsel", "Helligånden kom", "Helligånden kom"));
        list.add(new Question("Hvad symboliserer brødet?", "Jesu blod", "Jesu krop", "Guds ord", "Jesu krop"));
        list.add(new Question("Hvad symboliserer vinen?", "Helligånden", "Jesu blod", "Jesu krop", "Jesu blod"));
        list.add(new Question("Hvem døde på korset?", "Abraham", "Jesus", "Moses", "Jesus"));
        list.add(new Question("Hvem var i løvekulen?", "Josef", "Moses", "Daniel", "Daniel"));
        list.add(new Question("Hvem byggede arken?", "Abraham", "Noa", "David", "Noa"));
        list.add(new Question("Hvad betyder Amen?", "Tak", "Farvel", "Så ske det", "Så ske det"));
        list.add(new Question("Hvilken bøn lærte Jesus?", "Trosbekendelsen", "Fadervor", "Nadverbøn", "Fadervor"));
        list.add(new Question("Hvem gik på vandet?", "Peter", "Moses", "Jesus", "Jesus"));
        list.add(new Question("Hvem var Jesu mor?", "Maria", "Rebekka", "Sara", "Maria"));
        list.add(new Question("Hvem var Jesu far?", "Abraham", "Josef", "David", "Josef"));
        list.add(new Question("Hvad betyder Kristus?", "Hellig", "Den salvede", "Frelser", "Den salvede"));
        list.add(new Question("Hvem blev fristet i ørkenen?", "Jesus", "Elia", "Moses", "Jesus"));
        list.add(new Question("Hvem fornægtede Jesus tre gange?", "Peter", "Johannes", "Jakob", "Peter"));
        list.add(new Question("Hvornår døde Jesus?", "Påskedag", "Palmesøndag", "Langfredag", "Langfredag"));
        list.add(new Question("Hvad skete himmelfart?", "Jesus døbt", "Jesus til himlen", "Jesus født", "Jesus til himlen"));
        list.add(new Question("Hvad betyder dåben?", "At blive voksen", "At blive en del af Guds familie", "At få syndsforladelse", "At blive en del af Guds familie"));
        list.add(new Question("Hvilket tegn gav Gud Noa?", "Regnbuen", "Stjernen", "Duen", "Regnbuen"));
        list.add(new Question("Hvem fik de 10 bud?", "Abraham", "David", "Moses", "Moses"));
        list.add(new Question("Hvem kaldes troens fader?", "Jakob", "Abraham", "Noa", "Abraham"));
        list.add(new Question("Hvad er det største bud?", "Giv tiende", "Elsk Gud og din næste", "Bed dagligt", "Elsk Gud og din næste"));
        list.add(new Question("Hvad betyder disciple?", "Lærlinge", "Hyrder", "Profeter", "Lærlinge"));
        list.add(new Question("Hvad lovede Jesus?", "Et tempel", "Rigdom", "Helligånden", "Helligånden"));
        list.add(new Question("Hvor mange disciple havde Jesus?", "7", "12", "10", "12"));
        list.add(new Question("Hvem var den yngste discipel?", "Johannes", "Thomas", "Peter", "Johannes"));
        list.add(new Question("Hvem skrev flest breve?", "Paulus", "Moses", "David", "Paulus"));
        list.add(new Question("Hvilken bog fortæller om skabelsen?", "Salmerne", "1. Mosebog", "Joh.ev.", "1. Mosebog"));
        list.add(new Question("Hvad betyder konfirmation?", "At bekræfte sin dåb", "At blive voksen", "At blive gift", "At bekræfte sin dåb"));
        list.add(new Question("Hvad sagde Jesus om fremtiden?", "Saml guld", "Vær ikke bekymret", "Bed om hævn", "Vær ikke bekymret"));
        list.add(new Question("Hvem var den første mand?", "Adam", "Noa", "Moses", "Adam"));
        list.add(new Question("Hvem var den første kvinde?", "Sara", "Eva", "Maria", "Eva"));
        list.add(new Question("Hvem blev solgt af sine brødre?", "Moses", "Josef", "David", "Josef"));
        list.add(new Question("Hvem kæmpede mod Goliat?", "Saul", "Moses", "David", "David"));
        list.add(new Question("Hvem førte folket ud af Egypten?", "Moses", "Abraham", "Noa", "Moses"));
        list.add(new Question("Hvem fik stentavlerne?", "Jakob", "Moses", "Abraham", "Moses"));
        list.add(new Question("Hvem red ind i Jerusalem på et æsel?", "Jesus", "David", "Peter", "Jesus"));
        list.add(new Question("Hvem blev konge efter Saul?", "Samuel", "David", "Salomon", "David"));
        list.add(new Question("Hvad er den første bog i Bibelen?", "1. Mosebog", "Salmerne", "Matthæus", "1. Mosebog"));
        list.add(new Question("Hvad er den sidste bog i Bibelen?", "Johs. Åbenbaring", "Markus", "Ordsprogene", "Johs. Åbenbaring"));
        list.add(new Question("Hvad er den korteste bøn?", "Amen", "Kyrie", "Tak", "Amen"));
        list.add(new Question("Hvad er et andet navn for evangelierne?", "De gode nyheder", "Profeterne", "Lovene", "De gode nyheder"));
        list.add(new Question("Hvem tvivlede på Jesu opstandelse?", "Thomas", "Peter", "Johannes", "Thomas"));
        list.add(new Question("Hvem forrådte Jesus?", "Judas", "Paulus", "Peter", "Judas"));
        list.add(new Question("Hvem skrev mange salmer?", "Abraham", "David", "Moses", "David"));
        list.add(new Question("Hvad betyder Messias?", "Den salvede", "Hellig", "Frelser", "Den salvede"));
        list.add(new Question("Hvem fik løftet om mange børn?", "Moses", "Abraham", "Noa", "Abraham"));
        list.add(new Question("Hvem dræbte Abel?", "Josef", "Esau", "Kain", "Kain"));
        list.add(new Question("Hvem byggede templet?", "Moses", "Salomon", "David", "Salomon"));
        list.add(new Question("Hvad er Fadervor?", "En salme", "En bøn", "En profeti", "En bøn"));
        list.add(new Question("Hvor blev Jesus døbt?", "Jordanfloden", "Nilen", "Genesaret sø", "Jordanfloden"));
        list.add(new Question("Hvem døbte Jesus?", "Johannes Døber", "Paulus", "Peter", "Johannes Døber"));
        list.add(new Question("Hvem var de første disciple?", "Peter og Andreas", "Moses og Aron", "Jakob og Esau", "Peter og Andreas"));
        list.add(new Question("Hvor mange dage var Jesus død?", "3", "2", "7", "3"));
        list.add(new Question("Hvem skrev Apostlenes Gerninger?", "Paulus", "Lukas", "Matthæus", "Lukas"));
        list.add(new Question("Hvad betyder evangelium?", "Godt budskab", "Profeti", "Lov", "Godt budskab"));
        list.add(new Question("Hvad betyder kirke?", "Bygning", "Forsamling", "Folk", "Forsamling"));
        list.add(new Question("Hvilken dag hvilede Gud?", "Den 6.", "Den 1.", "Den 7.", "Den 7."));
        list.add(new Question("Hvem fik gaver af de vise mænd?", "Abraham", "Moses", "Jesus", "Jesus"));
        list.add(new Question("Hvilken konge ville dræbe Jesus?", "Herodes", "Pilatus", "Nebukadnesar", "Herodes"));
        list.add(new Question("Hvad er det største håb?", "Evigt liv", "Sejr", "Rigdom", "Evigt liv"));
        list.add(new Question("Hvad var Jesu første under?", "Helbrede blind", "Gå på vandet", "Vand til vin", "Vand til vin"));

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

    public void setFreePickFinished() {
        finishQuestion();
        this.requestFreePick = false;
    }

}
