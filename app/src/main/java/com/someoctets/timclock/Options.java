package com.someoctets.timclock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.type.DateTime;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Options extends Activity {


    public SharedPreferences sharedPreferences;
    public Outils outils = Outils.getInstanceOutils();


    boolean utiliserValeurParDefaut = false;
    boolean selectionnerToutParDefaut = false;

    TextInputEditText defautHeureEntree;
    TextInputEditText defautHeureSortie;
    TextInputEditText defautPause;



    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.options);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       outils.setSharedPreferences(sharedPreferences);

        utiliserValeurParDefaut = outils.loadBoolean("utiliserValeurParDefaut", false);
        selectionnerToutParDefaut = outils.loadBoolean("selectionnerToutParDefaut", true);


        Switch utiliserValeurParDefautSwitch = findViewById(R.id.utiliserValeurParDefaut);
        utiliserValeurParDefautSwitch.setChecked(utiliserValeurParDefaut);
        utiliserValeurParDefautSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                switcherUtiliserValeurParDefaut();

            }
        });

        Switch selectionnerToutParDefautSwitch = findViewById(R.id.selectionnerToutParDefaut);
        selectionnerToutParDefautSwitch.setChecked(selectionnerToutParDefaut);
        selectionnerToutParDefautSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                switcherSelectionnerToutParDefaut();

            }
        });



        String  defautHeureEntreeString = outils.loadString("defautHeureEntree","");
        defautHeureEntree = findViewById(R.id.defautHeureEntree);
        defautHeureEntree.setText(defautHeureEntreeString);
        defautHeureEntree.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c)
            {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                outils.saveString("defautHeureEntree", defautHeureEntree.getText().toString().trim());
            }
        });

        String defautHeureSortieString = outils.loadString("defautHeureSortie","");
        defautHeureSortie = findViewById(R.id.defautHeureSortie);
        defautHeureSortie.setText(defautHeureSortieString);
        defautHeureSortie.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c)
            {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                outils.saveString("defautHeureSortie", defautHeureSortie.getText().toString().trim());
            }
        });

        String defautPauseString = outils.loadString("defautPause","");
        defautPause = findViewById(R.id.defautPause);
        defautPause.setText(defautPauseString);
        defautPause.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c)
            {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                outils.saveString("defautPause", defautPause.getText().toString().trim());
            }
        });



        Button button = (Button) findViewById(R.id.buttonExporter);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "db export");
                //Ici sauvegarde

                EnregistrementDataSource enregistrementDataSource = new EnregistrementDataSource(getApplicationContext());
                ArrayList save = enregistrementDataSource.getAllEnregistrements();

                DBOpenHelper dbOpenHelper = new DBOpenHelper(getApplicationContext());
                //mettre heure ici
              // String nomDexportDB = ;
                String cible = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/TimClock_DB"+ +".db";
                try {
                    dbOpenHelper.exportDatabase( enregistrementDataSource.getDatabase().getPath().toString(), cible ) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        }







    public void Back(View v) {
        finish() ;

    }




    public void switcherUtiliserValeurParDefaut(){
        if(utiliserValeurParDefaut == true){
            utiliserValeurParDefaut = false;
            outils.saveBoolean("utiliserValeurParDefaut", false);
        }else {
            utiliserValeurParDefaut = true;
            outils.saveBoolean("utiliserValeurParDefaut", true);
        }
    }



    public void switcherSelectionnerToutParDefaut(){
        if(selectionnerToutParDefaut == true){
            selectionnerToutParDefaut = false;
            outils.saveBoolean("selectionnerToutParDefaut", false);

            }else {
            selectionnerToutParDefaut = true;
            outils.saveBoolean("selectionnerToutParDefaut", true);

            }
    }






}

