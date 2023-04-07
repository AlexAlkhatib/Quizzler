package com.mniprince.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver br;
    ArrayList<QuizModel> quizModels = new ArrayList<>();
    TextView questiontv, status, totaltv, scoretv;
    Button submit, restart;
    String cat, limit, difficulty;
    LinearLayout mainlayout, scorelayout;

    private static int[] idArray = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4};
    private Button[] button = new Button[idArray.length];

    int i;
    int isClick = 0;
    int score;
    int q = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInternetConnection();


    }

    /* La fonction checkInternetConnection() permet de vérifier si l'appareil a une connexion Internet active.
     Elle utilise le ConnectivityManager pour obtenir l'état de la connexion réseau de l'appareil.
     Si une connexion Internet est disponible, la fonction renvoie true, sinon elle renvoie false.
      Cette fonction est utilisée dans la méthode onCreate() pour vérifier si l'appareil a une connexion Internet active avant de charger la page Web dans le WebView.
     */
    private void checkInternetConnection() {
        if (br == null) {

            br = new BroadcastReceiver() {

                /*
                La méthode onReceive(Context context, Intent intent) est une méthode d'écouteur d'intent de diffusion.
                 Elle est appelée chaque fois qu'un nouvel intent de diffusion est reçu par l'application.
                 la méthode est utilisée pour détecter les changements d'état de la connexion Internet.
                 Elle vérifie si l'état de la connexion a changé depuis la dernière fois que l'application a vérifié l'état de la connexion,
                 et elle met à jour l'état de la connexion dans l'interface utilisateur de l'application.
                 */
                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");
                    NetworkInfo.State state = info.getState();
                    if (state == NetworkInfo.State.CONNECTED) {
                        setContentView(R.layout.activity_main);


                        try {
                            init();

                        } catch (Exception e) {

                        }


                    } else {

                        setContentView(R.layout.internet_screen);
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        Toast.makeText(getApplicationContext(), "Internet connection is Off", Toast.LENGTH_LONG).show();
                    }

                }


            };

            /*
            IntentFilter est utilisé pour filtrer les intents à recevoir, dans ce cas, l'intent de CONNECTIVITY_ACTION qui est diffusé lorsque la connexion réseau change.
            registerReceiver() est utilisé pour enregistrer le BroadcastReceiver avec le système pour qu'il puisse recevoir des diffusions d'intent correspondant à l'IntentFilter spécifié.
             */
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver((BroadcastReceiver) br, intentFilter);
        }
    }

    /*
    La méthode init() sert à initialiser les variables et les éléments d'interface utilisateur nécessaires pour démarrer le quiz.
    Elle commence par récupérer les informations de l'intent qui a lancé l'activité, telles que la catégorie, la difficulté et la limite de questions.
    elle appelle getResponse() pour récupérer les questions du serveur en fonction des paramètres passés.
    la méthode initialise les différents éléments d'interface utilisateur tels que les TextViews pour afficher la question, le score et l'état du quiz.
    Elle récupère également les références des boutons pour soumettre et redémarrer le quiz, ainsi que les dispositions pour afficher la vue de score et la vue principale.
    Enfin, la méthode initialise la variable de score à zéro, prête à être incrémentée au fur et à mesure que l'utilisateur répond aux questions.
     */
    public void init() {
        try {
            Intent intent = getIntent();
            cat = intent.getStringExtra("cat").toLowerCase(Locale.ROOT);
            limit = intent.getStringExtra("limit");
            difficulty = intent.getStringExtra("difficulty").toLowerCase(Locale.ROOT);
            getResponse(cat, limit, difficulty);
        } catch (Exception ignored) {
        }
        score = 0;
        questiontv = findViewById(R.id.questionTV);
        status = findViewById(R.id.status);
        submit = findViewById(R.id.submit);
        restart = findViewById(R.id.restart);
        totaltv = findViewById(R.id.totaltv);
        scoretv = findViewById(R.id.score);
        mainlayout = findViewById(R.id.mainlayout);
        scorelayout = findViewById(R.id.scorelayout);
    }

    /*
    La méthode getResponse est responsable de faire un appel API en utilisant Retrofit pour obtenir les questions et réponses du quiz en fonction de la catégorie,
    de la difficulté et du nombre de question spécifié.
    Elle définitle texte de la question de quiz et de l'état, et randomise l'ordre des options de réponse.
    Enfin, elle définit les écouteurs de clic pour chaque bouton de réponse et vérifie la réponse sélectionnée. Si la réponse sélectionnée est correcte, elle ajoute 5 points au score.
    S'il y a encore des questions à répondre, la question suivante est chargée.
    Si toutes les questions ont été répondues, le bouton de soumission est affiché et la méthode submitbtn() est appelée.
     */
    private void getResponse(String key, String key1, String key2) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://the-trivia-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInteface requestInteface = retrofit.create(RequestInteface.class);
        Call<List<QuizModel>> call = requestInteface.getJson(key, key1, key2);

        call.enqueue(new Callback<List<QuizModel>>() {
            private Context[] strArray;

            @Override
            public void onResponse(Call<List<QuizModel>> call, Response<List<QuizModel>> response) {

                if (response.body() != null) {
                    quizModels = new ArrayList<>(response.body());
                }


                try {

                    String[] strArray = new String[4];
                    strArray[0] = quizModels.get(q).getIncorrectAnswers().get(0);
                    strArray[1] = quizModels.get(q).getIncorrectAnswers().get(1);
                    strArray[2] = quizModels.get(q).getIncorrectAnswers().get(2);
                    strArray[3] = quizModels.get(q).getCorrectAnswer();
                    Collections.shuffle(Arrays.asList(strArray));

                    questiontv.setText(quizModels.get(q).getQuestion());
                    status.setText(q + 1 + "/" + quizModels.size());
                    for (i = 0; i < idArray.length; i++) {

                        button[i] = findViewById(idArray[i]);
                        button[i].setText(strArray[i]);
                        button[i].setBackground(getDrawable(R.drawable.bgbtn));

                        button[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (isClick == 0) {
                                    isClick = 1;
                                    switch (view.getId()) {
                                        case R.id.btn1:
                                            button[0].setBackground(getDrawable(R.drawable.selectbtn));
                                            try {
                                                if (Objects.equals(strArray[0], quizModels.get(q).getCorrectAnswer())) {
                                                    score = score + 5;

                                                } else {
                                                    score = score;
                                                }
                                            } catch (Exception w) {

                                            }
                                            break;
                                        case R.id.btn2:
                                            button[1].setBackground(getDrawable(R.drawable.selectbtn));
                                            try {
                                                if (Objects.equals(strArray[1], quizModels.get(q).getCorrectAnswer())) {
                                                    score = score + 5;
                                                } else {
                                                    score = score;
                                                }
                                            } catch (Exception w) {

                                            }
                                            break;
                                        case R.id.btn3:
                                            button[2].setBackground(getDrawable(R.drawable.selectbtn));
                                            try {
                                                if (Objects.equals(strArray[2], quizModels.get(q).getCorrectAnswer())) {
                                                    score = score + 5;
                                                } else {
                                                    score = score;
                                                }
                                            } catch (Exception w) {

                                            }
                                            break;
                                        case R.id.btn4:
                                            button[3].setBackground(getDrawable(R.drawable.selectbtn));
                                            try {
                                                if (Objects.equals(strArray[3], quizModels.get(q).getCorrectAnswer())) {
                                                    score = score + 5;
                                                } else {
                                                    score = score;
                                                }
                                            } catch (Exception w) {

                                            }
                                            break;
                                    }
                                    if (q < quizModels.size() - 1) {
                                        q = q + 1;
                                        isClick = 0;
                                        getResponse(cat, limit, difficulty);
                                    } else {
                                        submit.setVisibility(View.VISIBLE);
                                        submitbtn();
                                    }


                                }
                            }
                        });

                    }


                    //Afficher la bonne réponse dans un toast, c'était prévu dans une base de données mais on était limité dans le temps
                    Toast.makeText(MainActivity.this, quizModels.get(q).getCorrectAnswer(), Toast.LENGTH_SHORT).show();

                } catch (Exception ignored) {
                }


            }

            @Override
            public void onFailure(Call<List<QuizModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    La méthode submitbtn() est appelée lorsque l'utilisateur a répondu à toutes les questions du quiz et a cliqué sur le bouton "Submit".
    Elle est responsable de masquer le layout principal où se trouvent les questions et les réponses, d'afficher le layout du score où l'utilisateur peut voir son score et le nombre total de questions,
    puis d'appeler la méthode restart() qui permet de redémarrer le quiz en réinitialisant le score et le numéro de question.
     */
    private void submitbtn() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainlayout.setVisibility(View.GONE);
                scorelayout.setVisibility(View.VISIBLE);
                totaltv.setText(String.valueOf(q + 1));
                scoretv.setText(String.valueOf(score) + "/" + (q + 1) * 5);
                restart();
            }
        });
    }

    /*
    La méthode restart() est responsable de définir l'écouteur de clic pour le bouton "Redémarrer".
    Lorsque ce bouton est cliqué, elle crée un Intent pour revenir à la CategoryActivity et lance cette activité.
    Elle appelle également la méthode finish() pour fermer l'activité MainActivity actuelle.
     */
    private void restart() {

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}