package com.someoctets.timclock;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    public Outils outils = Outils.getInstanceOutils();
    //public CalendarView cal;
    private EnregistrementDataSource datasource = new EnregistrementDataSource(this);
    public TextInputEditText entree;
    public TextInputEditText sortie;
    public TextInputEditText pause;
    private TextView strTotal2;
    public CaseJour selectedCase;
    ViewGroup parent;
    int selectedDay = 0;
    int selectedMonth = 0;
    int selectedYear = 0;
    GridAdapter ga;
    CalendarTim caltim;
    boolean fabLockedOrNot = false;
    boolean editMode = false;
    FloatingActionButton fab;
    boolean fabOrange = false;
    boolean selectAllParDefaut ;
    boolean utiliserValeurParDefaut ;

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
        fab = (FloatingActionButton) findViewById(R.id.fab);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        outils.setSharedPreferences(sharedPreferences);

        Calendar firstDate = Calendar.getInstance();
        for (CaseJour f : caltim.getmAdapter().monthlyCases) {

            if (firstDate.get(Calendar.DAY_OF_MONTH) == f.jour) {
                selectedCase = f;


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp, fab.getContext().getTheme()));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
                }


                for (Enregistrement enr : values) {
                    if (enr.getD().equals(firstDate.getTime())) {
                        affichageFabLock();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fab.getContext().getTheme()));
                        } else {
                            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
                        }


                    } else {


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp, fab.getContext().getTheme()));
                        } else {
                            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
                        }


                    }

                }
            }
        }

        final Button optionsButton  = (Button) findViewById(R.id.boutonOption);
        optionsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Options.class);
                startActivity(intent);



            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Snackbar.make(view, "", Snackbar.LENGTH_LONG).setAction("Action", null).show();



                switcherFabLock();



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
                    }
                } catch (Exception e) {

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
                        sortie.setText("0000");
                    }
                } catch (Exception e) {

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
                        pause.setText("0000");
                    }
                } catch (Exception e) {

                }
            }
        });

        strTotal2 = findViewById(R.id.strTotal2);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(entree, InputMethodManager.SHOW_IMPLICIT);


       entree.requestFocus();
        entree.selectAll();
        majTotalMois();
        affichageFabLock();


    }


    @Override
    public void onResume() {
        super.onResume();
        // put your code here...

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

    public String checkS(String a) {
        int b = Integer.parseInt(a);
        if (b >= 2400) {
            a = "0000";
        } else {
            a = a;
        }
        return a;
    }


    public long[] calculer(long entreeD, long sortieD, long pauseD, boolean useDbData) {


        long ecart;
        Date dateZero = null;
        long dureePause = 0;
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


            try {
                String strDateZero = "0000";
                //      dateZero = sdf.parse(parseDate(strDateZero));
            } catch (Exception e) {
            }

            ecart = (sortieDate.getTime() - entreDate.getTime());
            //    System.out.println("Avant modif : " + ecart);

            if (sortieDate.before(entreDate)) {
                GregorianCalendar calendar = new java.util.GregorianCalendar();
                calendar.setTime(sortieDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1);

                ecart = (calendar.getTimeInMillis() - entreDate.getTime());
            }
            /////////// //correction fuseau horaire//////////
            SimpleDateFormat srf = new SimpleDateFormat("HHmm");

            Date refDate = srf.parse("0000");
            long pauseL = pauseDate.getTime() - refDate.getTime();
            //       if(pauseL < 0){
            //       pauseL = pauseL * -1;
            //      }
///////////////////////////////////////////////////

            tempsAvecPause = ecart - pauseL;

            resultatDureePause = pauseL / 1000 / 60;
            resultatSansPause = ecart / 1000 / 60;
            resultatAvecPause = tempsAvecPause / 1000 / 60;

            if (resultatAvecPause < 0) {
                resultatAvecPause = 0;
            }


            //     System.out.println("Apres Modif : " + resultatSansPause + " avec pause : " + resultatAvecPause);
            //  pause


        } catch (Exception e) {
            //    System.out.println("BUGGGGGGGGGGGGG");

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


    public Enregistrement lireJour(int year, int month, int dayOfMonth) {
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
        Enregistrement enr = lireEnregistrement(date);

        return enr;

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









    public Enregistrement lireEnregistrement(String keyDate) {
        Enregistrement enr = null;
        for (Enregistrement e : values) {
            if (e.getDate().equals(keyDate)) {
                enr = e;
                break;
            }
        }
        return enr;

    }










    public void majJour(int year, int month, int dayOfMonth) {
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


/*
      entree.setText(String.valueOf(enrTrouve.getIn()));
    sortie.setText(String.valueOf(enrTrouve.getOut()));
    pause.setText(String.valueOf(enrTrouve.getPause()));
    */
        }


    }


    public String dureeJour(Enregistrement enr) {
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
            //   c.get(Calendar.MONTH);
            //c.add(Calendar.MONTH, 1);
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
            strTotal2.setText(Long.toString((totalMois / 60 / 1000 / 60)) + " H " + Long.toString(((totalMois / 1000 / 60) - (totalMois / 1000 / 60 / 60) * 60)));
        } catch (Exception e) {

        }

    }

    public void deleteJour(int year, int month, int dayOfMonth) {

    }

    public void setSelectedDayInt(Date selectedDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(selectedDate);

        selectedDay = c.get(Calendar.DAY_OF_MONTH);
        selectedMonth = (c.get(Calendar.MONTH) + 1);
        selectedYear = c.get(Calendar.YEAR);

        try {
            Enregistrement enr = lireJour(selectedYear, selectedMonth, selectedDay);
            entree.setText(String.valueOf(enr.getIn()));
            sortie.setText(String.valueOf(enr.getOut()));
            pause.setText(String.valueOf(enr.getPause()));

/*
            if (editMode == false) {
               // entree.setEnabled(false);
               // sortie.setEnabled(false);
               // pause.setEnabled(false);
               // setFabLocked(true);
          /*      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fab.getContext().getTheme()));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
                }


            }*/

        } catch (Exception e) {
        }

    }

/*
    public void setFabLocked(boolean lockedOrNot) {
        this.fabLockedOrNot = lockedOrNot;
        if (fabLockedOrNot == true) {

            fab.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fab.getContext().getTheme()));
            } else {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
            }

        } else {
           // fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }

    }

*/






/*
          if (entree.getText().toString().length() > 0 || sortie.getText().toString().length() > 0 || pause.getText().toString().length() > 0) {
        majJour(selectedYear, selectedMonth, selectedDay);
        editMode = false;
        setFabLocked(true);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#24abd8"))); //  #24abd8 //ff33b5e5


    }
*/

    public void affichageFabLock(){


            boolean lockFab = false;
            String s = composerStringDate(selectedYear, selectedMonth, selectedDay );
            for (Enregistrement enr :values) {
                if (enr.getDate().equals(s)) {
                    lockFab = true;
                    break;
                }

            }
            if (lockFab == false) {
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#24abd8")));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp, fab.getContext().getTheme()));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
                }
                // setSelectAllParDefaut(outils.loadBoolean("selectionnerToutParDefaut", true));
                setUtiliserValeurParDefaut(outils.loadBoolean("utiliserValeurParDefaut", false));
            } else {
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                pause.setTextColor(Color.GRAY);
                entree.setTextColor(Color.GRAY);
                sortie.setTextColor(Color.GRAY);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fab.getContext().getTheme()));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
                }


        }


    }

   public void resetFab(){
       fabOrange = false;
       editMode = false;
       pause.setTextColor(Color.BLACK);
       entree.setTextColor(Color.BLACK);
       sortie.setTextColor(Color.BLACK);
   }




    public void switcherFabLock() {

        boolean lockFab = false;
        String s = composerStringDate(selectedYear, selectedMonth, selectedDay);
        for (Enregistrement enr : values) {
            if (enr.getDate().equals(s)) {
                lockFab = true;
                break;
            }
        }


        if (lockFab == true && fabOrange == false && editMode == false ) { // && editMode == true && fabOrange == false


                fabOrange = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_open_black_24dp, fab.getContext().getTheme()));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_open_black_24dp));
                }
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F57449"))); //F57449
                pause.setTextColor(Color.GRAY);
                entree.setTextColor(Color.GRAY);
                sortie.setTextColor(Color.GRAY);

            }else if(fabOrange == true && lockFab == true && editMode == false ) {
                fabOrange = false;
                editMode = true;
                pause.setTextColor(Color.BLACK);
                entree.setTextColor(Color.BLACK);
                sortie.setTextColor(Color.BLACK);
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#24abd8")));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp, fab.getContext().getTheme()));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
                }
             //  majJour(selectedYear, selectedMonth, selectedDay);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(entree, InputMethodManager.SHOW_IMPLICIT);
                entree.requestFocus();
                entree.selectAll();


            }else if (editMode == true && lockFab == true ) {

                fab.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY)); //  #24abd8 //ff33b5e5
                if (entree.getText().toString().length() > 0 || sortie.getText().toString().length() > 0 || pause.getText().toString().length() > 0) {
                    majJour(selectedYear, selectedMonth, selectedDay);
                    editMode = false;
                }
                pause.setTextColor(Color.GRAY);
                entree.setTextColor(Color.GRAY);
                sortie.setTextColor(Color.GRAY);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fab.getContext().getTheme()));
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
                }


            }else if (lockFab== false){
                if (entree.getText().toString().length() > 0 || sortie.getText().toString().length() > 0 || pause.getText().toString().length() > 0) {
                    majJour(selectedYear, selectedMonth, selectedDay);
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY)); //  #24abd8 //ff33b5e5
                    pause.setTextColor(Color.GRAY);
                    entree.setTextColor(Color.GRAY);
                    sortie.setTextColor(Color.GRAY);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, fab.getContext().getTheme()));
                    } else {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
                    }
                }else {
                    pause.setTextColor(Color.BLACK);
                    entree.setTextColor(Color.BLACK);
                    sortie.setTextColor(Color.BLACK);
                }


            }


        }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
    public  SharedPreferences getSharedPreferences(){
        return sharedPreferences;


    }


    public void setUtiliserValeurParDefaut(boolean trueOrFalse){
        if(trueOrFalse == true) {
            entree = findViewById(R.id.entree);
            sortie = findViewById(R.id.sortie);
            pause = findViewById(R.id.pause);


            if(outils.loadString("defautHeureEntree", "").length() > 0){
                entree.setText(outils.loadString("defautHeureEntree", ""));
            }
            if(outils.loadString("defautHeureSortie", "").length() > 0){
                sortie.setText(outils.loadString("defautHeureSortie", ""));
            }
            if(outils.loadString("defautPause", "").length() > 0){
                pause.setText(outils.loadString("defautPause", ""));
            }

  /*
  nombreDePalettes.setText(outils.loadString("defautNombrePalettes",""));
    poidPalet.setText(outils.loadString("defautTarePalette",""));
    tareColis.setText(outils.loadString("defautTareColis",""));
    */
            //   nombreDePalettes.setText("1");

            //  poidPalet.setText("25");
            //   tareColis.setText("0.6");
        }


    }




//////////////  if (entree.getText().toString().length() > 0 || sortie.getText().toString().length() > 0 || pause.getText().toString().length() > 0) {



/*
    public void deleteRecord(Enregistrement enr) {
        long id = enr.getId();
        System.out.println("Enregistrement deleted with id: " + id);
        db.delete(dbOpenHelper.TABLE_DATE, dbOpenHelper.COLUMN_ID
                + " = " + id, null);
    }
*/
}
