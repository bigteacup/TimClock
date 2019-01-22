package com.someoctets.timclock;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.constraint.solver.Cache;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
    public class CalendarTim extends LinearLayout  {

        private Cache memoryCache;

        private ImageView previousButton, nextButton;
        private TextView currentDate;
        private GridView calendarGridView;

        private static final int MAX_CALENDAR_COLUMN = 42;
        private int month, year;
        private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
        public Calendar cal = Calendar.getInstance();
        private Context context;
        private GridAdapter mAdapter;
        private EnregistrementDataSource datasource = new EnregistrementDataSource(getContext());

        private LayoutInflater mInflater;
        MainActivity main;

        public CalendarTim(Context context) {

            super(context);

        }
        public CalendarTim(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            initializeUILayout();
            setUpCalendarAdapter();
            setPreviousButtonClickEvent();
            setNextButtonClickEvent();
            setGridCellClickEvents();

        }
        public CalendarTim(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
        private void initializeUILayout(){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.calendartim_layout, this);
            previousButton = (ImageView)view.findViewById(R.id.previous_month);
            nextButton = (ImageView)view.findViewById(R.id.next_month);
            currentDate = (TextView)view.findViewById(R.id.display_current_date);
            calendarGridView = (GridView)view.findViewById(R.id.calendar_grid);

        }
        private void setPreviousButtonClickEvent(){
            previousButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal.add(Calendar.MONTH, -1);
                    setUpCalendarAdapter();
                    main.majTotalMois();
                }
            });
        }
        private void setNextButtonClickEvent(){
            nextButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal.add(Calendar.MONTH, 1);
                    setUpCalendarAdapter();
                    main.majTotalMois();
                }
            });
        }
        private void setGridCellClickEvents(){
            calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //    Toast.makeText(context, "Clicked " + position, Toast.LENGTH_LONG).show();

                    main.entree.setEnabled(true);
                    main.sortie.setEnabled(true);
                    main.pause.setEnabled(true);
                    main.entree.getText().clear();
                    main.sortie.getText().clear();
                    main.pause.getText().clear();
                    main.fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#49CAF5")));
                    main.setFabLocked(false);
                    mAdapter.oldCase = main.selectedCase;
                    CaseJour cs = (CaseJour) mAdapter.getItem(position);
                    main.selectedCase = cs;
                    mAdapter.getView(position, view, parent);
                   try{
                       majCase(mAdapter.getPosition(mAdapter.oldCase));
                   }catch(Exception e){

                   }
                    main.editMode = false;
                    main.setSelectedDayInt(main.selectedCase.getDateCase());









            }
            });
        }
        private void setUpCalendarAdapter(){






            List<CaseJour> caseJoursList = new ArrayList<CaseJour>();
         //   List<Date> dayValueInCells = new ArrayList<Date>();
            datasource.open();
            ArrayList<Enregistrement> listeEnregistrements = datasource.getAllEnregistrements();
            datasource.close();
            Calendar mCal = (Calendar)cal.clone();
            mCal.set(Calendar.DAY_OF_MONTH, 1);

            int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK)-2 ;
            mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
            while(caseJoursList.size() < MAX_CALENDAR_COLUMN){
              //  dayValueInCells.add(mCal.getTime());

                CaseJour caseJour = new CaseJour();
                caseJour.setDateCase(mCal.getTime());
                caseJour.setJour(mCal.get(Calendar.DAY_OF_MONTH));
                caseJoursList.add(caseJour);

                mCal.add(Calendar.DAY_OF_MONTH, 1);
            }


            String sDate = formatter.format(cal.getTime());
            currentDate.setText(sDate);
            mAdapter = new GridAdapter(context, caseJoursList, cal, listeEnregistrements);// new GridAdapter(context, dayValueInCells, caseJoursList, cal, listeEnregistrements);
            calendarGridView.setAdapter(mAdapter);
            mAdapter.setMain(main);
            //main.set
          //  memoryCache = new Cache(calendarGridView, 7);


        }

        public void  setMain(MainActivity main){
            this.main = main;
        }
        public GridAdapter getmAdapter() {
            return mAdapter;
        }


        public void majCase(int selectedDay ) {








            mAdapter.getView((selectedDay), calendarGridView.getChildAt(selectedDay), calendarGridView);

        }

//                              mAdapter.getView(position, view, parent);
//                              mAdapter.getPosition(mAdapter.oldDate)

    }



