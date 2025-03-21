package com.someoctets.timclock;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    public Outils outils = Outils.getInstanceOutils();
    //public CalendarView cal;
    private EnregistrementDataSource datasource = new EnregistrementDataSource(this);
    public TextInputEditText entree;
    public TextInputEditText sortie;
    public TextInputEditText pause;
    private TextView strTotal2;
    public TextInputEditText datein;
    public TextInputEditText dateout;
    public TextInputEditText libelle;
    public CaseJour selectedCase;
    public ItemDbLinearLayout itemSelected;
    int selectedDay = 0;
    int selectedMonth = 0;
    int selectedYear = 0;
    GridAdapter ga;
    CalendarTim caltim;
    boolean fabSaveButtonIsLocked = true;
    boolean editMode = false;
    FloatingActionButton fabSaveButton;
    FloatingActionButton fabUnlockButton;
    boolean fabOrange = false;
    boolean selectAllParDefaut;
    boolean utiliserValeurParDefaut;

    ArrayList<Enregistrement> values = new ArrayList<Enregistrement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /////////////////////////////////////////////
        // Initialisation
        Date now = new Date();
        SimpleDateFormat simpleDateformatA = new SimpleDateFormat("d"); // the day of the week abbreviated
        simpleDateformatA.format(now);
        selectedDay = Integer.parseInt(simpleDateformatA.format(now));

        SimpleDateFormat simpleDateformatB = new SimpleDateFormat("M"); // the day of the week abbreviated
        simpleDateformatB.format(now);
        selectedMonth = Integer.parseInt(simpleDateformatB.format(now));

        SimpleDateFormat simpleDateformatC = new SimpleDateFormat("y"); // the day of the week abbreviated
        simpleDateformatC.format(now);
        selectedYear = Integer.parseInt(simpleDateformatC.format(now));
        //////////////////////////////////////////////////////////////////////////


        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        datasource.open();
        values = datasource.getAllEnregistrements();
        datasource.close();
        setContentView(R.layout.activity_main);
        final CalendarTim caltimF = findViewById(R.id.custom_cal2);
        caltim = caltimF;
        ga = caltimF.getmAdapter();
        ga.setMain(this);
        caltimF.setMain(this);
        setSupportActionBar(toolbar);
        fabSaveButton = (FloatingActionButton) findViewById(R.id.fabSaveButton);
        fabUnlockButton = (FloatingActionButton) findViewById(R.id.fabUnlockButton);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        outils.setSharedPreferences(sharedPreferences);
        Calendar firstDate = Calendar.getInstance();
        Calendar todate = Calendar.getInstance();


        for (CaseJour f : caltim.getmAdapter().monthlyCases) {
            todate.setTime(f.getDateCase());
            if (firstDate.get(Calendar.DAY_OF_MONTH) == f.jour && firstDate.get(Calendar.MONTH) == todate.get(Calendar.MONTH)) {
                selectedCase = f;
            }
        }


        final Button optionsButton = (Button) findViewById(R.id.boutonOption);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Options.class);
                startActivity(intent);
            }
        });

        fabSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabSaveButtonIsLocked == false && editMode == false) {
                    enregistrer(selectedYear, selectedMonth, selectedDay);
                }
                if (fabSaveButtonIsLocked == false && editMode == true) {
                    modifier(selectedYear, selectedMonth, selectedDay); //todo enregistrer sur l'idDb correspondant à eventlistenner du textinput selectionné (recuperer le code dans "majjour" et "enregistrer"

                }
            }
        });
        fabUnlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcherFabSaveButtonIsLocked();
            }
        });

        entree = findViewById(R.id.entree);
        entree.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }


            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (check(s.toString())) {
                        entree.setText("0");
                        entree.selectAll();
                    }
                } catch (Exception e) {
                }


            }
        });
        entree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editMode = false;
                    fabSaveButtonIsLocked = false;
                    affichageFabButtons();
                } else {
                }
            }
        });
        sortie = findViewById(R.id.sortie);
        sortie.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (check(s.toString())) {
                        sortie.setText("0");
                        sortie.selectAll();
                    }
                } catch (Exception e) {
                }
            }
        });
        sortie.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editMode = false;
                    fabSaveButtonIsLocked = false;
                    affichageFabButtons();
                } else {
                }
            }
        });


        pause = findViewById(R.id.pause);
        pause.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (check(s.toString())) {
                        pause.setText("0");
                        pause.selectAll();
                    }
                } catch (Exception e) {
                }
            }
        });
        pause.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editMode = false;
                    fabSaveButtonIsLocked = false;
                    affichageFabButtons();
                } else {
                }
            }
        });


        strTotal2 = findViewById(R.id.strTotal2);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(entree, InputMethodManager.SHOW_IMPLICIT);


        entree.requestFocus();
        entree.selectAll();
        majTotalMois();
        affichageFabButtons();


    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            CaseJour cs = null;
            Calendar pauseDate = Calendar.getInstance();
            Calendar pauseTodate = Calendar.getInstance();

            for (CaseJour f : caltim.getmAdapter().monthlyCases) {
                pauseTodate.setTime(f.getDateCase());
                if (pauseDate.get(Calendar.DAY_OF_MONTH) == f.jour && pauseDate.get(Calendar.MONTH) == pauseTodate.get(Calendar.MONTH)) {
                    cs = f;
                }
            }
            //caltim.setUpCalendarAdapter();
            try {
                caltim.majSelectedCase(cs);
            } catch (Exception e) {
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    public boolean check(String a) {
        Boolean c = false;
        int b = Integer.parseInt(a);
        if (b >= 2400) {
            c = true;
        } else {
            c = false;
        }
        return c;
    }


    public long[] calculer(long entreeD, long sortieD, long pauseD, boolean useDbData) {


        long ecart;
        long tempsAvecPause = 0;
        long resultatSansPause = 0;
        long resultatAvecPause = 0;
        long resultatDureePause = 0;

        entree = findViewById(R.id.entree);
        sortie = findViewById(R.id.sortie);
        pause = findViewById(R.id.pause);


        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        if (useDbData == false) {
            entreeD = 0;
            sortieD = 0;
            pauseD = 0;
            try {
                entreeD = Integer.parseInt(entree.getText().toString());
            } catch (Exception e) {
            }

            try {
                sortieD = Integer.parseInt(sortie.getText().toString());
            } catch (Exception e) {
            }
            try {
                pauseD = Integer.parseInt(pause.getText().toString());
            } catch (Exception e) {
            }
        } else {


        }

        try {
            Date entreDate = null;
            Date sortieDate = null;
            Date pauseDate = null;

            try {
                String strEntreD = Long.toString(entreeD);
                entreDate = sdf.parse(parseDate(strEntreD));
            } catch (Exception e) {
            }
            try {
                String strSortieD = Long.toString(sortieD);
                sortieDate = sdf.parse(parseDate(strSortieD));
            } catch (Exception e) {
            }

            try {
                String strPauseD = Long.toString(pauseD);
                pauseDate = sdf.parse(parseDate(strPauseD));
            } catch (Exception e) {

            }


            ecart = (sortieDate.getTime() - entreDate.getTime());

            if (sortieDate.before(entreDate)) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(sortieDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1);

                ecart = (calendar.getTimeInMillis() - entreDate.getTime());
            }
            /////////// //correction fuseau horaire//////////
            SimpleDateFormat srf = new SimpleDateFormat("HHmm");

            Date refDate = srf.parse("0000"); // TODO inutile à suprimer après verif
            long pauseL = pauseDate.getTime() - refDate.getTime();

///////////////////////////////////////////////////

            tempsAvecPause = ecart - pauseL;

            resultatDureePause = pauseL / 1000 / 60;
            resultatSansPause = ecart / 1000 / 60;
            resultatAvecPause = tempsAvecPause / 1000 / 60;

            if (resultatAvecPause < 0) {
                resultatAvecPause = 0;
            }


        } catch (Exception e) {

        }
        long[] t = {resultatSansPause, resultatAvecPause, resultatDureePause};
        return t;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public String parseDate(String date) {
        if (date.length() == 1) {
            date = "000" + date;
        }
        if (date.length() == 2) {
            date = "00" + date;
        }
        if (date.length() == 3) {
            date = "0" + date;
        }
        if (date.length() == 4) {
            date = date;
        }
        return date;
    }


    public ArrayList<Enregistrement> lireJour(int year, int month, int dayOfMonth) {
        String strYear = String.valueOf(year);
        String strMonth = null;

        if (month < 10) {
            strMonth = "0" + String.valueOf(month);
        } else {
            strMonth = String.valueOf(month);
        }
        String strDay = null;
        if (dayOfMonth < 10) {
            strDay = "0" + String.valueOf(dayOfMonth);
        } else {
            strDay = String.valueOf(dayOfMonth);
        }


        String date = "'" + strDay + strMonth + strYear + "'";


        return lireEnregistrementsDuJour(date);

    }


    public String composerStringDate(int year, int month, int dayOfMonth) {
        String strYear = String.valueOf(year);
        String strMonth = null;

        if (month < 10) {
            strMonth = "0" + String.valueOf(month);
        } else {
            strMonth = String.valueOf(month);
        }
        String strDay = null;
        if (dayOfMonth < 10) {
            strDay = "0" + String.valueOf(dayOfMonth);
        } else {
            strDay = String.valueOf(dayOfMonth);
        }


        String date = "'" + strDay + strMonth + strYear + "'";


        return date;

    }


    public ArrayList<Enregistrement> lireEnregistrementsDuJour(String keyDate) {
        ArrayList<Enregistrement> listeEnr = new ArrayList<>();
        for (Enregistrement e : values) { //todo maintenir values à jour : values = datasource.getAllEnregistrements();
            if (e.getDate().equals(keyDate)) {
                listeEnr.add(e);

            }
        }
        return listeEnr;

    }


    public void majJour(int year, int month, int dayOfMonth) { //// TODO: 17/03/2025    la sauvergarde de la donnée se fait ici                 à suprimer après refonte
        String strYear = String.valueOf(year);
        String strMonth = null;
        String strDay = null;

        boolean trouve = false;
        String date = "";
        Enregistrement enrTrouve = null;

        long keyIn = 0;
        long keyOut = 0;
        long keyPause = 0;


        if (month < 10) {
            strMonth = "0" + String.valueOf(month);
        } else {
            strMonth = String.valueOf(month);
        }
        if (dayOfMonth < 10) {
            strDay = "0" + String.valueOf(dayOfMonth);
        } else {
            strDay = String.valueOf(dayOfMonth);
        }
        date = "'" + strDay + strMonth + strYear + "'";


        for (Enregistrement enr : values) {
            //      datasource.deleteEnregistrement(enr);

            if (date.equals(enr.getDate())) {
                System.out.println("date deja presente : " + enr.getId() + " " + enr.getDate() + " " + enr.getIn() + " " + enr.getOut() + " " + enr.getPause());
                trouve = true;
                enrTrouve = enr;
                break;
            }
        }
        if (trouve == true) {
            if (editMode == true) {
                datasource.open();
                datasource.deleteEnregistrement(enrTrouve);
                values = datasource.getAllEnregistrements();
                datasource.close();
                try {
                    keyIn = Long.parseLong(entree.getText().toString());
                } catch (Exception e1) {

                }
                try {
                    keyOut = Long.parseLong(sortie.getText().toString());
                } catch (Exception e1) {

                }
                try {
                    keyPause = Long.parseLong(pause.getText().toString());
                } catch (Exception e1) {

                }
                if (keyIn + keyOut + keyPause > 0) {

                    datasource.open();
                    Enregistrement enregistrement = datasource.createEnregistrement(date, keyIn, keyOut, keyPause);
                    values = datasource.getAllEnregistrements();
                    datasource.close();

                    caltim.majCase(ga.getPosition(selectedCase));
                    majTotalMois();
                } else {

                    caltim.majCase(ga.getPosition(selectedCase));
                    majTotalMois();
                }

            }

        } else {
            try {
                keyIn = Long.parseLong(entree.getText().toString());
            } catch (Exception e1) {

            }
            try {
                keyOut = Long.parseLong(sortie.getText().toString());
            } catch (Exception e1) {

            }
            try {
                keyPause = Long.parseLong(pause.getText().toString());
            } catch (Exception e1) {

            }
            if (keyIn + keyOut + keyPause > 0) {
                datasource.open();
                Enregistrement enregistrement = datasource.createEnregistrement(date, keyIn, keyOut, keyPause);
                values = datasource.getAllEnregistrements();
                datasource.close();
                caltim.majCase(ga.getPosition(selectedCase));
                majTotalMois();
            }
        }


        if (trouve == true) {
        }


    }


    public String dureeJour(ArrayList<Enregistrement> listeEnregistrementsDuJour) { // todo à suprimer après refonte
        long entree = 0;
        long sortie = 0;
        long pause = 0;
        long[] t = null;
        for (Enregistrement enr : listeEnregistrementsDuJour) {
            t = calculer(enr.getIn(), enr.getOut(), enr.getPause(), true);
            entree = entree + t[0];
            sortie = sortie + t[1];
            pause = pause + t[2];
            t[0] = entree;
            t[1] = sortie;
            t[2] = pause;

        }
        long dureeJ = (((t[1]) * 60) * 1000);

        String heures = Long.toString((dureeJ / 60 / 1000 / 60));
        String minutes = Long.toString(((dureeJ / 1000 / 60) - (dureeJ / 1000 / 60 / 60) * 60));
        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }
        String a = (heures + ":" + minutes);


        return a;
    }

    public String dureeJour(Enregistrement enr) { // todo à suprimer après refonte
        long[] t = calculer(enr.getIn(), enr.getOut(), enr.getPause(), true);
        long dureeJ = (((t[1]) * 60) * 1000);

        String heures = Long.toString((dureeJ / 60 / 1000 / 60));
        String minutes = Long.toString(((dureeJ / 1000 / 60) - (dureeJ / 1000 / 60 / 60) * 60));
        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }
        String a = (heures + ":" + minutes);


        return a;
    }


    public void majTotalMois() {

        ArrayList<Enregistrement> liste = values;

        long totalMois = 0;
        long[] t;

        for (Enregistrement l : liste) {
            Calendar c = Calendar.getInstance();
            c.setTime(l.getD());
         /*   try{
            } catch (Exception e1) {
                datasource.open();
                datasource.deleteEnregistrement(l); // suprimer automatiquement les incorrects
                datasource.close();
                break;
            }*/
            int displayMonth;
            int displayYear;
            try {
                displayMonth = caltim.cal.get(Calendar.MONTH);
                displayYear = caltim.cal.get(Calendar.YEAR);
                if (c.get(Calendar.MONTH) == displayMonth && c.get(Calendar.YEAR) == displayYear) {
                    t = calculer(l.getIn(), l.getOut(), l.getPause(), true);
                    totalMois = totalMois + (((t[1]) * 60) * 1000);
                } else {

                }
            } catch (Exception e1) {
                Calendar g = Calendar.getInstance();

                displayMonth = g.get(Calendar.MONTH);
                displayYear = g.get(Calendar.YEAR);
                if (c.get(Calendar.MONTH) + 1 == displayMonth && c.get(Calendar.YEAR) == displayYear) {
                    t = calculer(l.getIn(), l.getOut(), l.getPause(), true);
                    totalMois = totalMois + (((t[1]) * 60) * 1000);
                }
            }

        }

        try {
            String minutesString = "";
            if (((totalMois / 1000 / 60) - (totalMois / 1000 / 60 / 60) * 60) < 10) {
                minutesString = "0" + Long.toString(((totalMois / 1000 / 60) - (totalMois / 1000 / 60 / 60) * 60));
            } else {
                minutesString = Long.toString(((totalMois / 1000 / 60) - (totalMois / 1000 / 60 / 60) * 60));
            }
            strTotal2.setText(Long.toString((totalMois / 60 / 1000 / 60)) + " H " + minutesString);
        } catch (Exception e) {

        }

    }


    public void setSelectedDayInt(Date selectedDate) {    //values = datasource.getAllEnregistrements();
        Calendar c = Calendar.getInstance();
        c.setTime(selectedDate);
        //ArrayList<Enregistrement> listeDes = null;
        selectedDay = c.get(Calendar.DAY_OF_MONTH);
        selectedMonth = (c.get(Calendar.MONTH) + 1);
        selectedYear = c.get(Calendar.YEAR);

        LinearLayout donneesDuJour = (LinearLayout) findViewById(R.id.donneesDuJour);
        donneesDuJour.removeAllViews();
        try {
            ArrayList<Enregistrement> enr = lireJour(selectedYear, selectedMonth, selectedDay);
            trierItem(enr);
//// TODO: 17/03/2025
            for (Enregistrement enregistrement : enr) {
                ItemDbLinearLayout item = new ItemDbLinearLayout(this, this, enregistrement);
                item.setModifierEntree(enregistrement.getIn());
                item.setModifierSortie(enregistrement.getOut());
                item.setModifierPause(enregistrement.getPause());

                donneesDuJour.addView(item);
            }


        } catch (Exception e) {
        }

    }


    public void trierItem(ArrayList<Enregistrement> listeEnregistrement) {
      /*  ArrayList<Enregistrement>deepList = new ArrayList<>();
        for(Enregistrement e : listeEnregistrement){
            deepList.add(e.clone());
        }
        Iterator<Enregistrement> it = deepList.iterator();
        it = deepList.iterator();//maj
        */
        ArrayList<Enregistrement> listeTriee = new ArrayList<>();

        long lePlusPetit;

        Enregistrement enr;
        Enregistrement postulant = new Enregistrement();

            long testA;
            long testB;
            boolean trouve = false;
        while(listeEnregistrement.size()!=listeTriee.size()){
            for(Enregistrement e : listeEnregistrement) {
             testA = e.getIn();
                trouve=false;

              //  while (it.hasNext())  {
               //     enr = it.next();
           for (Enregistrement e2 : listeEnregistrement) {
                testB = e2.getIn();


                if (testA < testB && e.getId()!=e2.getId()) {
                    lePlusPetit = testA;
                    postulant = e;
                    trouve=true;
                   // it.remove();
                }


         //   }




        }
                if(trouve=true){
                listeTriee.add(postulant);
                }
        }
        }

        int t = 0;
            }





    public void resetFab() { //todo à supprimer après refonte
        fabOrange = false;
        editMode = false;
        pause.setTextColor(Color.BLACK);
        entree.setTextColor(Color.BLACK);
        sortie.setTextColor(Color.BLACK);
    }


    public void enregistrer(int year, int month, int dayOfMonth) { //todo

        String strYear = String.valueOf(year);
        String strMonth = null;
        String strDay = null;


        String date = "";
        long keyIn = 0;
        long keyOut = 0;
        long keyPause = 0;

        if (month < 10) {
            strMonth = "0" + String.valueOf(month);
        } else {
            strMonth = String.valueOf(month);
        }
        if (dayOfMonth < 10) {
            strDay = "0" + String.valueOf(dayOfMonth);
        } else {
            strDay = String.valueOf(dayOfMonth);
        }
        date = "'" + strDay + strMonth + strYear + "'";


        if (entree.getText().toString().length() > 0 || sortie.getText().toString().length() > 0 || pause.getText().toString().length() > 0) {
            try {
                keyIn = Long.parseLong(entree.getText().toString());
            } catch (Exception e1) {

            }
            try {
                keyOut = Long.parseLong(sortie.getText().toString());
            } catch (Exception e1) {

            }
            try {
                keyPause = Long.parseLong(pause.getText().toString());
            } catch (Exception e1) {

            }
            if (keyIn + keyOut + keyPause > 0) {

                datasource.open();
                Enregistrement enregistrement = datasource.createEnregistrement(date, keyIn, keyOut, keyPause);
                values = datasource.getAllEnregistrements();
                datasource.close();

                caltim.majCase(ga.getPosition(selectedCase));
                majTotalMois();
                pause.getText().clear();
                entree.getText().clear();
                sortie.getText().clear();
                setSelectedDayInt(selectedCase.getDateCase()); //maj
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(entree, InputMethodManager.SHOW_IMPLICIT);
                entree.requestFocus();
                entree.selectAll();
            }
        }
    }

    public void modifier(int year, int month, int dayOfMonth) {//todo à lier avec l'id de l'enregistrement
        if (itemSelected.getModifierEntree().length() > 0 || itemSelected.getModifierSortie().length() > 0 || itemSelected.getModifierPause().length() > 0) {
            // majJour(selectedYear, selectedMonth, selectedDay);

            String strYear = String.valueOf(year);
            String strMonth = null;
            String strDay = null;


            String date = "";
            long keyIn = 0;
            long keyOut = 0;
            long keyPause = 0;

            if (month < 10) {
                strMonth = "0" + String.valueOf(month);
            } else {
                strMonth = String.valueOf(month);
            }
            if (dayOfMonth < 10) {
                strDay = "0" + String.valueOf(dayOfMonth);
            } else {
                strDay = String.valueOf(dayOfMonth);
            }
            date = "'" + strDay + strMonth + strYear + "'";

            datasource.open();
            datasource.deleteEnregistrement(itemSelected.enregistrement);
            values = datasource.getAllEnregistrements();
            datasource.close();
            try {
                keyIn = Long.parseLong(itemSelected.getModifierEntree());
            } catch (Exception e1) {

            }
            try {
                keyOut = Long.parseLong(itemSelected.getModifierSortie());
            } catch (Exception e1) {

            }
            try {
                keyPause = Long.parseLong(itemSelected.getModifierPause());
            } catch (Exception e1) {

            }
            if (keyIn + keyOut + keyPause > 0) {

                datasource.open();
                itemSelected.enregistrement = datasource.createEnregistrement(date, keyIn, keyOut, keyPause);
                values = datasource.getAllEnregistrements();
                datasource.close();

                caltim.majCase(ga.getPosition(selectedCase));
                majTotalMois();
                setSelectedDayInt(selectedCase.getDateCase());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(entree, InputMethodManager.SHOW_IMPLICIT);
                entree.requestFocus();
                entree.selectAll();
            } else {

                caltim.majCase(ga.getPosition(selectedCase));
                setSelectedDayInt(selectedCase.getDateCase());
                majTotalMois();
                //majSelectedCase
                // ArrayList<Enregistrement> enr = lireJour(selectedYear, selectedMonth, selectedDay);
                //lireEnregistrementsDuJour();
                // majCase();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(entree, InputMethodManager.SHOW_IMPLICIT);
                entree.requestFocus();
                entree.selectAll();
            }
        }
    }

    public void switcherFabSaveButtonIsLocked() {
        if (fabSaveButtonIsLocked) {
            fabSaveButtonIsLocked = false;
        } else {
            fabSaveButtonIsLocked = true;
        }
        affichageFabButtons();
    }

    public void affichageFabButtons() { //met à jour l'affichage des deux bouttons fab fabSave et fabUnlock
        affichageFabSaveButton();
        affichageFabUnlockButton();
    }

    public void affichageFabUnlockButton() {
        if (fabSaveButtonIsLocked == false) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabUnlockButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_open_black_24dp, fabUnlockButton.getContext().getTheme()));
            } else {
                fabUnlockButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_open_black_24dp));
            }
            fabUnlockButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F57449"))); //F57449 //orange
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabUnlockButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fabUnlockButton.getContext().getTheme()));
            } else {
                fabUnlockButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
            }
            fabUnlockButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#24abd8"))); //bleu
        }
    }

    public void affichageFabSaveButton() {

        if (fabSaveButtonIsLocked == false) {
            fabSaveButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#24abd8"))); //bleu
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp, fabSaveButton.getContext().getTheme()));
            } else {
                fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
            }
            // setSelectAllParDefaut(outils.loadBoolean("selectionnerToutParDefaut", true));
            setUtiliserValeurParDefaut(outils.loadBoolean("utiliserValeurParDefaut", false));
        } else {
            fabSaveButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY)); //gris
            pause.setTextColor(Color.GRAY);
            entree.setTextColor(Color.GRAY);
            sortie.setTextColor(Color.GRAY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fabSaveButton.getContext().getTheme()));
            } else {
                fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
            }
        }
    }

    public void switcherFabLock() { //todo a suprimer apres refonte ?

        boolean lockFab = false;
        String s = composerStringDate(selectedYear, selectedMonth, selectedDay);
        for (Enregistrement enr : values) {
            if (enr.getDate().equals(s)) {
                lockFab = true;
                break;
            }
        }


        if (lockFab == true && fabOrange == false && editMode == false) { // && editMode == true && fabOrange == false

            //fabOrange = false;
            editMode = true;
            fabOrange = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_open_black_24dp, fabSaveButton.getContext().getTheme()));
            } else {
                fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_open_black_24dp));
            }
            fabSaveButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F57449"))); //F57449 //orange
            pause.setTextColor(Color.BLACK);
            entree.setTextColor(Color.BLACK);
            sortie.setTextColor(Color.BLACK);


            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(entree, InputMethodManager.SHOW_IMPLICIT);
            entree.requestFocus();
            entree.selectAll();


        } else if (editMode == true && lockFab == true && fabOrange == true) {
            fabOrange = false;
            fabSaveButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY)); //  #24abd8 //ff33b5e5
            if (entree.getText().toString().length() > 0 || sortie.getText().toString().length() > 0 || pause.getText().toString().length() > 0) {
                majJour(selectedYear, selectedMonth, selectedDay);
                editMode = false;
            }
            if (lireJour(selectedYear, selectedMonth, selectedDay) != null) {
                pause.setTextColor(Color.GRAY);
                entree.setTextColor(Color.GRAY);
                sortie.setTextColor(Color.GRAY);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fabSaveButton.getContext().getTheme()));
                } else {
                    fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
                }
            } else {
                fabOrange = false;

                pause.getText().clear();
                entree.getText().clear();
                sortie.getText().clear();
                //  affichageFabLock();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(entree, InputMethodManager.SHOW_IMPLICIT);
                entree.requestFocus();
                entree.selectAll();

            }


        } else if (lockFab == false) {
            if (entree.getText().toString().length() > 0 || sortie.getText().toString().length() > 0 || pause.getText().toString().length() > 0) {
                majJour(selectedYear, selectedMonth, selectedDay);
                fabSaveButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY)); //  #24abd8 //ff33b5e5
                pause.setTextColor(Color.GRAY);
                entree.setTextColor(Color.GRAY);
                sortie.setTextColor(Color.GRAY);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fabSaveButton.getContext().getTheme()));
                } else {
                    fabSaveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
                }
            } else {
                pause.setTextColor(Color.BLACK);
                entree.setTextColor(Color.BLACK);
                sortie.setTextColor(Color.BLACK);
            }


        }


    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;


    }


    public void setUtiliserValeurParDefaut(boolean trueOrFalse) {
        if (trueOrFalse == true) {
            entree = findViewById(R.id.entree);
            sortie = findViewById(R.id.sortie);
            pause = findViewById(R.id.pause);


            if (outils.loadString("defautHeureEntree", "").length() > 0) {
                entree.setText(outils.loadString("defautHeureEntree", ""));
            }
            if (outils.loadString("defautHeureSortie", "").length() > 0) {
                sortie.setText(outils.loadString("defautHeureSortie", ""));
            }
            if (outils.loadString("defautPause", "").length() > 0) {
                pause.setText(outils.loadString("defautPause", ""));
            }


        }


    }


}
