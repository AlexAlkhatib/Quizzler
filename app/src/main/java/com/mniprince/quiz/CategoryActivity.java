package com.mniprince.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity {
    BroadcastReceiver br;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button start;
    Spinner spinner, catspinner;
    String[] limi = {"5", "10", "15", "20"};
    String[] category = {"Arts & Literature", "Film & TV", "Food & Drink", "General Knowledge", "Geography", "History", "Music", "Science", "Society & Culture", "Sport & Leisure"};
    String cat, limit = "", difficulty;
    private long backpresstime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        checkInternetConnection();
    }
    /*
    La méthode onCreateOptionsMenu(Menu menu) est responsable de la création du menu d'options dans l'activité principale.
    Elle fait appel à la méthode getMenuInflater() pour créer une instance du MenuInflater, qui permet d'ajouter des éléments au menu en utilisant un fichier XML.
    Ensuite, elle utilise la méthode inflate() pour ajouter les éléments définis dans le fichier menu_main.xml au menu.
    Enfin, elle retourne true pour indiquer que la création du menu est terminée avec succès.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    La méthode onOptionsItemSelected est responsable de la gestion des événements du menu de l'application.
    Lorsque l'utilisateur sélectionne un élément de menu, la méthode est appelée avec l'objet MenuItem correspondant en tant que paramètre.
    Dans cette méthode, la méthode getItemId() est utilisée pour récupérer l'identifiant de l'élément de menu sélectionné.
    Si l'élément de menu sélectionné est "about", un Toast affichant un message d'information sur l'application est créé et affiché. Si l'élément de menu sélectionné est "exit", la méthode finishAffinity() est appelée pour fermer toutes les activités associées à l'application et quitter l'application. La valeur de retour true indique que l'événement de menu a été géré avec succès.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, R.string.about_toast, Toast.LENGTH_LONG).show();
                return (true);
            case R.id.exit:
                finishAffinity();
                return (true); }
        return true;
    }

    //Pareil que dans la classe MainActivity
    private void checkInternetConnection() {
        if (br == null) {

            br = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");
                    NetworkInfo.State state = info.getState();
                    if (state == NetworkInfo.State.CONNECTED) {
                        setContentView(R.layout.activity_category);
                        Toolbar tb = findViewById(R.id.ToolBar);
                        setSupportActionBar(tb);
                        try {
                            initcat();

                        } catch (Exception e) {

                        }


                    } else {

                        setContentView(R.layout.internet_screen);
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        Toast.makeText(getApplicationContext(), "Internet connection is Off", Toast.LENGTH_LONG).show();
                    }

                }


            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver((BroadcastReceiver) br, intentFilter);
        }
    }
    /*
    La méthode initcat() est responsable de l'initialisation des éléments de l'interface utilisateur de la CategoryActivity.
    Elle définit les adaptateurs pour les spinners de catégorie et de limite, et ajoute des écouteurs d'événements pour sélectionner les valeurs de ces spinners.
    Elle initialise également le bouton de démarrage et définit son écouteur de clic. Lorsque le bouton est cliqué, la méthode vérifie si une difficulté a été sélectionnée.
    Si c'est le cas, elle crée un Intent pour aller à l'activité MainActivity et y transmet les valeurs sélectionnées pour la catégorie, la limite et la difficulté.
    Enfin, elle démarre l'activité MainActivity et ferme la CategoryActivity actuelle. Si aucune difficulté n'a été sélectionnée, elle affiche un Toast indiquant à l'utilisateur de sélectionner un niveau de difficulté.
     */
    private void initcat() {
        radioGroup = findViewById(R.id.radioGroup);
        start = findViewById(R.id.start);
        spinner = findViewById(R.id.spinner);
        catspinner = findViewById(R.id.catspinner);


        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(CategoryActivity.this, R.layout.spinner_item, category);
        catAdapter.setDropDownViewResource(R.layout.spinner_item);
        catspinner.setAdapter(catAdapter);
        catspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> limitAdapter = new ArrayAdapter<String>(CategoryActivity.this, R.layout.spinner_item, limi);
        limitAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(limitAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                limit = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (difficulty != null) {
                    Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                    intent.putExtra("cat", cat);
                    intent.putExtra("limit", limit);
                    intent.putExtra("difficulty", difficulty);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CategoryActivity.this, "Select Difficulty Level", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    /*
    La méthode checkButton() est appelée lorsqu'un bouton radio est sélectionné dans le groupe radioGroup.
    Elle récupère l'identifiant de l'élément de bouton radio sélectionné à l'aide de la méthode getCheckedRadioButtonId() et l'assigne à la variable radioid.
    Ensuite, elle trouve la vue RadioButton correspondante à partir de l'ID radioid à l'aide de la méthode findViewById() et l'assigne à la variable radioButton.
    Enfin, elle récupère le texte du bouton radio sélectionné à l'aide de la méthode getText() et l'assigne à la variable difficulty.
     */
    public void checkButtion(View v) {
        int radioid = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioid);
        difficulty = radioButton.getText().toString();
    }


    /*
    La méthode onBackPressed() est utilisée pour détecter quand l'utilisateur appuie sur le bouton "retour" de son appareil.
    Dans cette méthode, il y a une condition qui vérifie si l'utilisateur a appuyé sur le bouton deux fois dans un délai de 2 secondes pour confirmer s'il souhaite quitter l'application.
    Si l'utilisateur confirme, l'activité CategoryActivity est fermée et l'application est ramenée à l'écran d'accueil du système d'exploitation.
    Sinon, un message "Appuyez à nouveau pour quitter" est affiché à l'utilisateur.
     */
    @Override
    public void onBackPressed() {
        if (backpresstime + 2000 > System.currentTimeMillis()) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
            builder.setMessage("Are you sure want to exit?");
            builder.setTitle("Confirmation Notice!");
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CategoryActivity.this.finish();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else {
            Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backpresstime = System.currentTimeMillis();

    }
}