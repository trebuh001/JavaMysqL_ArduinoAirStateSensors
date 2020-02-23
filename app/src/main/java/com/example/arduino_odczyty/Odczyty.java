package com.example.arduino_odczyty;

public class Odczyty  {

    public String id;
    public String id2;
    public String temperatura;
    public String wilgotnosc;
    public String cisnienie;
    public String szk_gazy;
    public String data;
    public String komentarz;



    public Odczyty()
    {

    }
    public Odczyty(String id,String temperatura,String wilgotnosc,String cisnienie,String szk_gazy,String data)
    {
        this.id=id;
        this.temperatura=temperatura;
        this.wilgotnosc=wilgotnosc;
        this.data=data;
        this.cisnienie=cisnienie;
        this.szk_gazy=szk_gazy;

    }
    public Odczyty(String id2)
    {
        this.id2=id2;
        //this.temperatura=temperatura;
        //this.wilgotnosc=wilgotnosc;
    }

}
