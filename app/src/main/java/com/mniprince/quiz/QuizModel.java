package com.mniprince.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
Cette classe est une classe modèle (ou POJO) pour stocker les données d'une question de quiz. Elle utilise l'annotation Gson @SerializedName pour faire correspondre les noms des propriétés JSON aux noms des propriétés de la classe.

Les propriétés de la classe sont :

category: la catégorie du quiz
id: l'identifiant unique du quiz
correctAnswer: la réponse correcte du quiz
incorrectAnswers: une liste des réponses incorrectes du quiz
question: la question du quiz
tags: une liste de tags associés au quiz
type: le type de quiz (par exemple, vrai/faux ou choix multiple)
difficulty: la difficulté du quiz (par exemple, facile, moyen ou difficile)
regions: une liste de régions associées au quiz (par exemple, Amérique du Nord, Europe, etc.)
 */

public class QuizModel {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("correctAnswer")
    @Expose
    private String correctAnswer;
    @SerializedName("incorrectAnswers")
    @Expose
    private List<String> incorrectAnswers = null;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("regions")
    @Expose
    private List<Object> regions = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Object> getRegions() {
        return regions;
    }

    public void setRegions(List<Object> regions) {
        this.regions = regions;
    }

}