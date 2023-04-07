package com.mniprince.quiz;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

/*
La classe ProgressBarAnimation étend la classe Animation et est utilisée pour créer une animation de la barre de progression et de son texte correspondant.
Cette classe a un constructeur qui prend en paramètres le contexte de l'application, la barre de progression, le texte de la barre de progression, la valeur de départ et la valeur finale de la barre de progression.
 */
public class ProgressBarAnimation extends Animation {
    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    private Float from;
    private Float to;

    public ProgressBarAnimation(Context context, ProgressBar progressBar, TextView textView, Float from, Float to) {
        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
    }

    /*
    La classe ProgressBarAnimation hérite de la classe Animation et remplace la méthode applyTransformation pour effectuer une animation de la barre de progression.
    La méthode prend en paramètres le temps d'interpolation et l'objet Transformation, qui est utilisé pour appliquer les transformations à la vue.
    Elle calcule la valeur de la barre de progression et la met à jour à chaque étape de l'animation en utilisant la formule linéaire.
    La valeur de la barre de progression est définie en pourcentage et est également affichée dans un TextView.
    Si la valeur de la barre de progression atteint sa valeur maximale (correspondant à la valeur "to" définie dans le constructeur),
    la méthode démarre une nouvelle activité CategoryActivity à l'aide d'un objet Intent.
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
        textView.setText((int) value + " %");
        if (value == to) {
            context.startActivity(new Intent(context, CategoryActivity.class));

        }

    }
}
