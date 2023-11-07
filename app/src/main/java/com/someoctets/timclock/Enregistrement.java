package com.someoctets.timclock;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class


Enregistrement {
    private long id;
    private String date;
    private Date d;
    private long in;
    private long out;
    private long pause;


    public Date getD() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        sdf.setCalendar(Calendar.getInstance());
        Calendar calendar = Calendar.getInstance();

        // sdf.setLenient(true);
        Date dateb = null;
        try {
            String b = this.date.replace("'", "");
            dateb = sdf.parse(b);

            String fg = "ddd";
        } catch (Exception e) {
        }
        return dateb;
    }


    public void setD(Date d) {
        this.d = d;
    }


    public long getIn() {
        return in;
    }

    public void setIn(long in) {
        this.in = in;
    }

    public long getOut() {
        return out;
    }

    public void setOut(long out) {
        this.out = out;
    }

    public long getPause() {
        return pause;
    }

    public void setPause(long pause) {
        this.pause = pause;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return date;
    }
}