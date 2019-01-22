package com.someoctets.timclock;


import android.content.SharedPreferences;

public class Outils {

    private static final Outils outils = new Outils() ;
    SharedPreferences sharedPreferences;
  //  EcranPremier ecranPremier;
 //   Options options;



    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;


    }
    /*
    public void setEcranPremier(EcranPremier ecranPremier) {
        this.ecranPremier = ecranPremier;
    }
    public  EcranPremier getEcranPremier(){
        return ecranPremier;


    }


    public void setOptions(Options options) {
        this.options = options;
    }
    public  Options getOptions(){
        return options;


    }

*/
    public static  Outils getInstanceOutils() {
        return outils;
    }







        public  void saveBoolean(String key, boolean value){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }



    public boolean loadBoolean(String key, boolean savedValue){
        savedValue = sharedPreferences.getBoolean(key, savedValue);
        return savedValue;
    }




    public  void saveString(String key, String value){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }




    public String loadString(String key, String savedValue){
        savedValue = sharedPreferences.getString(key, savedValue);
        return savedValue;
    }





    public  void saveInt(String key, int value){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }




    public int loadInt(String key, int savedValue){
        savedValue = sharedPreferences.getInt(key, savedValue);
        return savedValue;
    }





}
