package com.someoctets.timclock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

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













        String  defautNombrePalettesString = outils.loadString("defautHeureEntree","");
        defautHeureEntree = findViewById(R.id.defautHeureEntree);
        defautHeureEntree.setText(defautNombrePalettesString);
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
        String defautTarePaletteString = outils.loadString("defautHeureSortie","");
        defautHeureSortie = findViewById(R.id.defautHeureSortie);
        defautHeureSortie.setText(defautTarePaletteString);
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

        String defautTareColisString = outils.loadString("defautPause","");
        defautPause = findViewById(R.id.defautPause);
        defautPause.setText(defautTareColisString);
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

