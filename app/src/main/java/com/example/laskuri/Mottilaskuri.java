package com.example.laskuri;

public class Mottilaskuri {
    private int id;
    private double rungonYmparys;
    private double pituus;

    public Mottilaskuri(int id, double rungonYmparys, double pituus){
        this.id = id;
        this.rungonYmparys = rungonYmparys;
        this.pituus = pituus;
    }

    public int getId(){
        return this.id;
    }

    public double getRungonYmparys(){
        return this.rungonYmparys;
    }

    public double getPituus(){
        return this.pituus;
    }

    public double getTilavuus(){
        return this.pituus * this.rungonYmparys * this.rungonYmparys / (4 * 3.1415926536);
    }
}
