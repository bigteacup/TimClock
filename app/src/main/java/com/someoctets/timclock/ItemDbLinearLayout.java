package com.someoctets.timclock;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class ItemDbLinearLayout extends LinearLayout  {
private TextView modifierEntree;
private TextView modifierSortie;
private TextView modifierPause;
private Context context;
MainActivity main;
public Enregistrement enregistrement;




    public ItemDbLinearLayout(Context context) {
        super(context);

    }

    public ItemDbLinearLayout(Context context, MainActivity main, Enregistrement enregistrement) {
        super(context);
        this.context = context;
        this.main = main;
        this.enregistrement = enregistrement;
        initializeUILayout();
    }

    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.itemdb, this);
        modifierEntree = view.findViewById(R.id.modifierEntree);
        modifierSortie = view.findViewById(R.id.modifierSortie);
        modifierPause = view.findViewById(R.id.modifierPause);
        //LinearLayout itemdb =  view.findViewById(R.id.itemdb);

        modifierEntree.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }



            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (main.check(s.toString())) {
                        main.entree.setText("0");
                        main.entree.selectAll();
                    }
                } catch (Exception e) {
                }


            }
        });
        modifierEntree.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    textInputSelected();
                } else {
                }
            }
            //  main.modifier(dbId);
        });
        modifierSortie.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }



            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (main.check(s.toString())) {
                        main.entree.setText("0");
                        main.entree.selectAll();
                    }
                } catch (Exception e) {
                }


            }
        });
        modifierSortie.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    textInputSelected();
                } else {
                }
            }
        });
        modifierPause.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }



            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (main.check(s.toString())) {
                        main.entree.setText("0");
                        main.entree.selectAll();
                    }
                } catch (Exception e) {
                }


            }
        });
        modifierPause.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    textInputSelected();
                } else {
                }
            }
        });

    }
    public void textInputSelected(){
        main.editMode = true;
        main.fabSaveButtonIsLocked=true;
        main.affichageFabButtons();
        main.itemSelected = this;
    }

    public void setMain(MainActivity main) {
        this.main = main;
    }
    public void setModifierEntree(long i){
        this.modifierEntree.setText(String.valueOf(i));
    }
    public void setModifierSortie(long i){
        this.modifierSortie.setText(String.valueOf(i));
    }
    public void setModifierPause(long i){
        this.modifierPause.setText(String.valueOf(i));
    }



    public String getModifierEntree(){
        return  this.modifierEntree.getText().toString();
    }
    public String getModifierSortie(){
        return this.modifierSortie.getText().toString();
    }
    public String getModifierPause(){
        return this.modifierPause.getText().toString();
    }


}
