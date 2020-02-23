package com.example.arduino_odczyty;

public class Komentarze {

    public String id;
    public String id2;
    public String temperatura;
    public String wilgotnosc;
    public String cisnienie;
    public String szk_gazy;
    public String data;
    public String komentarz;

    public Komentarze()
    {

    }
    public Komentarze(String id,String temperatura,String wilgotnosc,String cisnienie,String szk_gazy,String data,String komentarz)
    {
        this.id=id;
        this.temperatura=temperatura;
        this.wilgotnosc=wilgotnosc;
        this.cisnienie=cisnienie;
        this.szk_gazy=szk_gazy;
        this.data=data;
        this.komentarz=komentarz;

    }

}
