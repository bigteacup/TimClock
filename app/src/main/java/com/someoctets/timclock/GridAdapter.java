package com.someoctets.timclock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    List<CaseJour> monthlyCases;
    public Calendar currentDate;
    public Calendar dateCal;
    int displayMonth;
    int displayYear;
    public CaseJour oldCase;
  private ArrayList<Enregistrement> listeEnregistrements;
    MainActivity main;

    public GridAdapter(Context context,  List<CaseJour> monthlyCases, Calendar currentDate, ArrayList<Enregistrement> listeEnregistrements ) { //(Context context, List<Date> monthlyDates, List<CaseJour> monthlyCases, Calendar currentDate, ArrayList<Enregistrement> listeEnregistrements ) {
        super(context, R.layout.casejour);
    //    this.monthlyDates = monthlyDates;
        this.monthlyCases = monthlyCases;

     this.currentDate = currentDate;
        this.listeEnregistrements = listeEnregistrements;
        mInflater = LayoutInflater.from(context);

    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Calendar selDayCal = Calendar.getInstance();
        Date a =  main.selectedCase.getDateCase();
        main.setSelectedDayInt(main.selectedCase.getDateCase());
        selDayCal.setTime((Date) a);

        //Date  mDate =  monthlyDates.get(position);
        Date  mDate =  monthlyCases.get(position).getDateCase();
        dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        displayMonth = dateCal.get(Calendar.MONTH) ;
        displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);

        EnregistrementDataSource datasource = new EnregistrementDataSource(getContext());
        datasource.open();
        listeEnregistrements = datasource.getAllEnregistrements();
        datasource.close();


        View view = convertView;
     //  RecyclerView.ViewHolder holder;


        if(view == null){
            view = mInflater.inflate(R.layout.casejour, parent, false);
           // view = LayoutInflater.from(parent.getContext()).inflate(R.layout.casejour, parent, false);

       }

        //Add day to calendar
        TextView cellNumber =ViewHolder.get(view, R.id.jour); //(TextView)view.findViewById(R.id.jour); //
        //Add events to the calendar
        TextView enrIndicator =  ViewHolder.get(view, R.id.temps);//(TextView)view.findViewById(R.id.temps); //

       // CaseJour caseJour = (CaseJour) getItem(position);
        cellNumber.setText(String.valueOf(dayValue));
      //  cellNumber.setText(String.valueOf(caseJour.getJour()));

        enrIndicator.setTypeface(null, Typeface.ITALIC);
        enrIndicator.setText("?");
       // enrIndicator.setBackgroundColor(Color.parseColor("white"));
        enrIndicator.setBackgroundColor(0x00000000);
        enrIndicator.setTextColor(Color.GRAY);

        if(displayMonth == selDayCal.get(Calendar.MONTH)  && displayYear == selDayCal.get(Calendar.YEAR) && dayValue == selDayCal.get(Calendar.DAY_OF_MONTH) ){
            view.setBackgroundColor(Color.parseColor("#4dbef2"));
           main.selectedCase =  monthlyCases.get(position);// mDate;

        }
        else if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(Color.parseColor("white"));
        }else{
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }

        Calendar enrCalendar = Calendar.getInstance();
        for(int i = 0; i < listeEnregistrements.size(); i++){
            Calendar k = Calendar.getInstance();
            k.setTime(listeEnregistrements.get(i).getD());
          // k.add(Calendar.MONTH, 1);
            enrCalendar = k ;
            if(dayValue == enrCalendar.get(Calendar.DAY_OF_MONTH ) && displayMonth == enrCalendar.get(Calendar.MONTH )  && displayYear == enrCalendar.get(Calendar.YEAR)){
                enrIndicator.setBackgroundColor(Color.parseColor("#efedee"));
               // enrIndicator.setTypeface(null, Typeface.ITALIC);
                enrIndicator.setTextColor(Color.RED);
                enrIndicator.setTextSize(14);
               try {
                   enrIndicator.setText(String.valueOf(main.dureeJour(listeEnregistrements.get(i))));
               }catch (Exception e){

               }
            }
        }

        return view;
    }





    public void  setMain(MainActivity main){
        this.main = main;
    }

    @Override
    public int getCount() {
        return  monthlyCases.size();// monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyCases.get(position);// monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyCases.indexOf(item);//monthlyDates.indexOf(item);
    }
}