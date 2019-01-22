package com.someoctets.timclock;

import java.util.Date;

public class CaseJour {

    public CaseJour (){

    }



    Date dateCase;
    int jour;
    String temps;

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }
    public Date getDateCase() {
        return dateCase;
    }

    public void setDateCase(Date dateCase) {
        this.dateCase = dateCase;
    }

}
