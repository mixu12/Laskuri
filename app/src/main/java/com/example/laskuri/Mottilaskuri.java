package com.example.laskuri;

public class Mottilaskuri {
    private double rungonYmparys;
    private double pituus;

    public Mottilaskuri(double rungonYmparys, double pituus){
        this.rungonYmparys = rungonYmparys;
        this.pituus = pituus;
    }

    public double getTilavuus(){
        return this.rungonYmparys * this.pituus;
    }
}
