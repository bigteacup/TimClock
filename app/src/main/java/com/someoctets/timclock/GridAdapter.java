package com.someoctets.timclock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    //  private List<Date> monthlyDates;
    ArrayList<CaseJour> monthlyCases;
    public Calendar currentDate;
    public Calendar dateCal;
    int displayMonth;
    int displayYear;
    public CaseJour oldCase;
    private ArrayList<Enregistrement> listeEnregistrements;
    MainActivity main;

    public GridAdapter(Context context, ArrayList<CaseJour> monthlyCases, Calendar currentDate) { //(Context context, List<Date> monthlyDates, List<CaseJour> monthlyCases, Calendar currentDate, ArrayList<Enregistrement> listeEnregistrements ) {
        super(context, R.layout.casejour);
        //    this.monthlyDates = monthlyDates;
        this.monthlyCases = monthlyCases;

        this.currentDate = currentDate;

        mInflater = LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Calendar selDayCal = Calendar.getInstance();
        Date a = main.selectedCase.getDateCase();
        main.setSelectedDayInt(main.selectedCase.getDateCase());
        selDayCal.setTime((Date) a);

        //Date  mDate =  monthlyDates.get(position);
        Date mDate = monthlyCases.get(position).getDateCase();
        dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        displayMonth = dateCal.get(Calendar.MONTH);
        displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);


        listeEnregistrements = main.values;


        View view = convertView;
        //  RecyclerView.ViewHolder holder;


        if (view == null) {
            view = mInflater.inflate(R.layout.casejour, parent, false);
            // view = LayoutInflater.from(parent.getContext()).inflate(R.layout.casejour, parent, false);

        }
       /*
///////////////////////////////////////////////////////////////////////////////////////////////   //////////////////
        //Création du thread par l'UI thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Opération consommatrice en temps exécuté par le nouveau thread
                //appel de updateIHM par le nouveau thread

                main.caltim.updateIHM(resultat);
            }
        }).start();
   //////////////////////////////////////////////////////////////////////////////////////////////    //////////////
*/



        //Add day to calendar
        TextView cellNumber = ViewHolder.get(view, R.id.jour); //(TextView)view.findViewById(R.id.jour); //
        //Add events to the calendar
        TextView enrIndicator = ViewHolder.get(view, R.id.temps);//(TextView)view.findViewById(R.id.temps); //
        //enrIndicator.setLineSpacing(-30,1.0f);
        enrIndicator.setTextSize(12);
        // CaseJour caseJour = (CaseJour) getItem(position);
        cellNumber.setText(String.valueOf(dayValue));
        //  cellNumber.setText(String.valueOf(caseJour.getJour()));

        enrIndicator.setTypeface(null, Typeface.ITALIC);
        enrIndicator.setText("?");
        // enrIndicator.setBackgroundColor(Color.parseColor("white"));
        enrIndicator.setBackgroundColor(0x00000000);
      //    cellNumber.setBackgroundColor(0x00000000);
      //  enrIndicator.setBackgroundColor(Color.WHITE);
       // cellNumber.setBackgroundColor(Color.WHITE);
      //  enrIndicator.setBackgroundResource(R.drawable.roundedborder);
        //enrIndicator.setBackgroundResource(R.drawable.roundedborder);
       // ContextCompat.getDrawable(enrIndicator.getContext(), R.drawable.roundedborder);

        enrIndicator.setTextColor(Color.GRAY);
/*
        LayerDrawable layerDrawable = (LayerDrawable) this.getContext().getResources().getDrawable(R.drawable.roundedborder);
        GradientDrawable shape1 = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.shape1);
        GradientDrawable shape2 = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.shape2);
        shape2.setColor(Color.parseColor("#49CAF5"));
        shape1.setColor(Color.parseColor("#49CAF5"));
        */

        if (displayMonth == selDayCal.get(Calendar.MONTH) && displayYear == selDayCal.get(Calendar.YEAR) && dayValue == selDayCal.get(Calendar.DAY_OF_MONTH)) {
            view.setBackgroundColor(Color.parseColor("#49CAF5"));//ff33b5e5
           // view.setBackgroundColor(Color.WHITE);//ff33b5e5
            main.selectedCase = monthlyCases.get(position);// mDate;
           // view.setBackgroundResource(R.drawable.roundedborder);
            view.setBackgroundResource(R.drawable.rounded);
            setBack(view, R.color.timBleu );

/*
            if (displayMonth == currentMonth) {
                view.setBackgroundColor(Color.parseColor("white"));

                setBack(view, R.color.timWhite );
                setBack(enrIndicator, R.color.timWhite );
                setBack(cellNumber, R.color.timWhite );
            }
*/
        } else if (displayMonth == currentMonth && displayYear == currentYear) {
            view.setBackgroundColor(Color.parseColor("white"));

            setBack(view, R.color.timWhite );
            setBack(enrIndicator, R.color.timWhite );
            setBack(cellNumber, R.color.timWhite );
        } else {
            view.setBackgroundResource(R.drawable.rounded);
          // view.setBackgroundColor(Color.parseColor("#cccccc"));
            setBack(view, R.color.timGrisA);
            view.getBackground().setAlpha(80);
            cellNumber.setTextColor(cellNumber.getTextColors().withAlpha(80));
            enrIndicator.setTextColor(enrIndicator.getTextColors().withAlpha(80));


        }




        boolean trouve = false;
        Calendar enrCalendar = Calendar.getInstance();
        for (int i = 0; i < listeEnregistrements.size(); i++) {

            Calendar k = Calendar.getInstance();
            k.setTime(listeEnregistrements.get(i).getD());
            enrCalendar = k;


            if (dayValue == enrCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == enrCalendar.get(Calendar.MONTH) && displayYear == enrCalendar.get(Calendar.YEAR)) {
                trouve = true;
               /// enrIndicator.setBackgroundColor(Color.parseColor("#efedee"));
             //   view.setBackgroundResource(R.drawable.rounded);
            //    setBack(view, R.color.timBleuClair);
               // view.setBackgroundResource(R.drawable.roundedborder);
                enrIndicator.setBackgroundResource(R.drawable.rounded);
                setBack(enrIndicator, R.color.timBleuClair);

                // enrIndicator.setTypeface(null, Typeface.ITALIC);
                enrIndicator.setTextColor(Color.parseColor("#ffff4444"));
                enrIndicator.setTextSize(12);
                //Drawable mDrawable = enrIndicator.getContext().getResources().getDrawable(R.drawable.roundedborder);
            //    mDrawable.setColorFilter(new ColorFilter(Color.GREEN));

/*
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    main.fab.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp, main.fab.getContext().getTheme()));
                } else {
                    main.fab.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
                }
*/
                try {
                    enrIndicator.setText(String.valueOf(main.dureeJour(listeEnregistrements.get(i))));
                } catch (Exception e) {

                }
                break;
            }


        }

        return view;
    }

    public void setBack(View view, int RColorColorPrimary ){
        Drawable background = view.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(ContextCompat.getColor(view.getContext(),RColorColorPrimary));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(ContextCompat.getColor(view.getContext(),RColorColorPrimary));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(ContextCompat.getColor(view.getContext(),RColorColorPrimary));
        }
    }

    public void setMain(MainActivity main) {
        this.main = main;
    }

    @Override
    public int getCount() {
        return monthlyCases.size();// monthlyDates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyCases.get(position);// monthlyDates.get(position);
    }

    @Override
    public int getPosition(Object item) {
        //
        return monthlyCases.indexOf(item);//monthlyDates.indexOf(item);
    }
}