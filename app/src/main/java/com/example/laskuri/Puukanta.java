package com.example.laskuri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Puukanta extends SQLiteOpenHelper {
    public final String KAADETUT_PUUT = "KAADETUT_PUUT";
    public final String COLUMN_ID = "ID";
    public final String COLUMN_RUNGON_YMPARYS = "RUNGON_YMPARYS";
    public final String COLUMN_PITUUS = "PITUUS";

    public Puukanta(@Nullable Context context){
        super(context, "kaadetut_puut.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + KAADETUT_PUUT + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_RUNGON_YMPARYS + " REAL, " + COLUMN_PITUUS + " REAL)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean lisaaKaato(Mottilaskuri mottilaskuri){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RUNGON_YMPARYS, mottilaskuri.getRungonYmparys());
        cv.put(COLUMN_PITUUS, mottilaskuri.getPituus());

        long insert = db.insert(KAADETUT_PUUT, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else{
            db.close();
            return true;
        }
    }

    public double summaKaadoista() {
        List<Double> listausTilavuuksista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM " + KAADETUT_PUUT;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // Käy läpi kaikki tallennetut tiedot ja luo uuden nimike-olion. Laittaa ne sitten listaan, joka palautetaan.
            do {
                int id = cursor.getInt(0);
                double ymparys = cursor.getDouble(1);
                double pituus = cursor.getDouble(2);

                Mottilaskuri mottilaskuri = new Mottilaskuri(id, ymparys, pituus);
                listausTilavuuksista.add(mottilaskuri.getTilavuus());
            } while (cursor.moveToNext());

        }

        double tilavuuksienSumma = 0;
        for (int i = 0; i < listausTilavuuksista.size(); i++) {
            tilavuuksienSumma = tilavuuksienSumma + listausTilavuuksista.get(i);
        }
        return tilavuuksienSumma;
    }

    public boolean tyhjennaPuukanta(){
        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DELETE FROM " + KAADETUT_PUUT;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }

}
