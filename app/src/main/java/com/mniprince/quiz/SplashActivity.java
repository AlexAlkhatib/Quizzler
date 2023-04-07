package com.mniprince.quiz;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

/*
Ce code définit la classe SplashActivity qui étend la classe Activity et affiche un écran de démarrage avec une animation de barre de progression pendant que l'application se charge.
La barre de progression est animée à l'aide d'une classe ProgressBarAnimation personnalisée.
 */
public class SplashActivity extends Activity {
    ProgressBar progressBar;
    TextView textView;

    /*
    Dans la méthode onCreate, le fichier de mise en page activity_splash.xml est gonflé et la barre de progression et le texte sont initialisés.
    La valeur maximale de la barre de progression est définie sur 100 et l'échelle de la barre de progression est définie sur 3f pour les appareils exécutant Android Honeycomb ou une version ultérieure.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = findViewById(R.id.progressb);
        textView = findViewById(R.id.textView);
        progressBar.setMax(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            progressBar.setScaleY(3f);
        }

        progressBarAnimation();
    }

    /*
    La méthode progressBarAnimation crée une nouvelle instance de la classe ProgressBarAnimation et définit sa durée à 1800 millisecondes.
    La barre de progression est ensuite définie pour utiliser cette animation.
     */
    public void progressBarAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        anim.setDuration(1800);
        progressBar.setAnimation(anim);


    }

    /*
    La méthode onStop termine l'activité lorsqu'elle est arrêtée pour empêcher l'utilisateur de revenir à l'écran de démarrage une fois que l'application est chargée.
     */
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
