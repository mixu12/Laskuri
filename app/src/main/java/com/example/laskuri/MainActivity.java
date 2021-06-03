package com.example.laskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    DecimalFormat kaksiDesimaalia = new DecimalFormat("#,###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button laske = (Button) findViewById(R.id.laskeNappain);
        Button tallenna = (Button) findViewById(R.id.tallennaKantaan);
        Button nollaSumma = (Button) findViewById(R.id.nollaaSumma);

        EditText rungonYmparys = (EditText) findViewById(R.id.rungonymparys);
        EditText puunPituus = (EditText) findViewById(R.id.pituus);
        TextView puunTilavuus = (TextView) findViewById(R.id.tilavuus);

        //hakee tallennetut määrän heti käynnistettäessä näytölle
        naytaSumma();

        rungonYmparys.setText("");
        puunPituus.setText("");

        laske.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    laske(rungonYmparys, puunPituus);
            }
        });

        tallenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!puunTilavuus.getText().toString().equals("0,00")) //tarkistaa, että jotain on laskettuna ennen tallennusta
                    tallenna(rungonYmparys, puunPituus);
                else {
                    Toast.makeText(MainActivity.this, "Laske ensin tilavuus", Toast.LENGTH_LONG).show();
                }
            }
        });

        nollaSumma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nollaaSumma();
            }
        });
    }

    //Varmistaa, että lähtöarvot on annettu ennen laskentaa
    public boolean tarkastaLahtoarvot(EditText rungonYmparys, EditText puunPituus) {
        if ((!rungonYmparys.getText().toString().equals("")) && (!puunPituus.getText().toString().equals(""))) {
            return true;
        } else {
            return false;
        }
    }

    public void laske(EditText rungonYmparys, EditText puunPituus){
        Mottilaskuri mottilaskuri = mottilaskuriin(rungonYmparys, puunPituus); //Olio luodaan omalla metodilla.

        if(mottilaskuri != null) {
            double tilavuus = mottilaskuri.getTilavuus();

            TextView puunTilavuus = (TextView) findViewById(R.id.tilavuus);
            puunTilavuus.setText(String.valueOf(kaksiDesimaalia.format(tilavuus)));
        }
    }

    public double getymparys(EditText rungonYmparys){
        double ymparys = Double.valueOf(rungonYmparys.getText().toString());
        return ymparys;
    }

    public double getPituus(EditText puunPituus){
        double pituus = Double.valueOf(puunPituus.getText().toString());
        return pituus;
    }

    public Mottilaskuri mottilaskuriin(EditText rungonYmparys, EditText puunPituus) {
        //Jos lähtöarvot on annettu, luo olion, joka sisältää rungon ympäryksen ja puun pituuden. Palauttaa olion
        if (tarkastaLahtoarvot(rungonYmparys, puunPituus)) {
            double ymparys = getymparys(rungonYmparys);
            double pituus = getPituus(puunPituus);

            Mottilaskuri mottilaskuri = new Mottilaskuri(-1, ymparys, pituus);

            return mottilaskuri;
        } else {
            Toast.makeText(MainActivity.this, "Anna puun pituus ja rungon ympärysmitta", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    //Tallentaa tiedot tietokantaan.
    public void tallenna(EditText rungonYmparys, EditText puunPituus) {
        Puukanta puukanta = new Puukanta(MainActivity.this);

        Mottilaskuri mottilaskuri = mottilaskuriin(rungonYmparys, puunPituus);

        if (mottilaskuri != null) {

            boolean testi = puukanta.lisaaKaato(mottilaskuri);

            TextView summaKaadoista = (TextView) findViewById(R.id.summaKaadoista);
            summaKaadoista.setText(String.valueOf(kaksiDesimaalia.format(puukanta.summaKaadoista())));

            TextView puunTilavuus = (TextView) findViewById(R.id.tilavuus);
            puunTilavuus.setText(String.valueOf(kaksiDesimaalia.format(0)));

        }
    }

    //Tyhjentää tietokannan
    public void nollaaSumma(){
        Puukanta puukanta = new Puukanta(MainActivity.this);
        puukanta.tyhjennaPuukanta();
        naytaSumma();
    }

    //Päivittää näytölle tietokantaan tallennetun summan
    public void naytaSumma(){
        Puukanta puukanta = new Puukanta(MainActivity.this);

        TextView summaKaadoista = (TextView) findViewById(R.id.summaKaadoista);
        summaKaadoista.setText(String.valueOf(kaksiDesimaalia.format(puukanta.summaKaadoista())));
    }

}