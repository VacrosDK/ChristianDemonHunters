package com.gmail.mphag;

import java.util.ArrayList;
import java.util.List;

public class QuestionManager {

    private List<Question> list  = new ArrayList<>();

    public void loadQuestions() {
        list.add(new Question("Hvad betyder ordet 'konfirmation'?", "At bekræfte sin dåb", "At blive voksen", "At blive medlem af kirken for første gang", "At bekræfte sin dåb"));
        list.add(new Question("Hvem var Jesu tolv nærmeste venner?", "Profeterne", "Disciplene", "Farisæerne", "Disciplene"));
        list.add(new Question("Hvad fejrer vi til påske?", "Jesu fødsel", "Jesu død og opstandelse", "Jesu himmelfart", "Jesu død og opstandelse"));
        list.add(new Question("Hvilket symbol bruges ofte for Helligånden?", "En due", "En fisk", "En stjerne", "En due"));
        list.add(new Question("Hvad betyder 'Fadervor'?", "Vor Gud", "Vor Far", "Vor konge", "Vor Far"));
        list.add(new Question("Hvor blev Jesus født ifølge Bibelen?", "Jerusalem", "Nazaret", "Betlehem", "Betlehem"));
        list.add(new Question("Hvad kaldes den sidste aften Jesus spiste sammen med disciplene?", "Påskemåltidet", "Den sidste nadver", "Den gyldne fest", "Den sidste nadver"));
        list.add(new Question("Hvilket sprog blev Det Nye Testamente oprindeligt skrevet på?", "Hebraisk", "Græsk", "Latin", "Græsk"));
        list.add(new Question("Hvad betyder 'Amen'?", "Så skal det ske", "Tak for mad", "Halleluja", "Så skal det ske"));
        list.add(new Question("Hvad kaldes de fire første bøger i Det Nye Testamente?", "Breve", "Salmer", "Evangelier", "Evangelier"));
    }

    public void draw() {

    }

    public void logic() {

    }

    public void input() {

    }


    public void askQuestion() {

    }
}
