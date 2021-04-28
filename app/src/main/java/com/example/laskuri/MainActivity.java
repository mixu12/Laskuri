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
        EditText rungonYmparys = (EditText) findViewById(R.id.rungonymparys);
        EditText puunPituus = (EditText) findViewById(R.id.pituus);
        TextView puunTilavuus = (TextView) findViewById(R.id.tilavuus);


        rungonYmparys.setText("");
        puunPituus.setText("");

        laske.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!rungonYmparys.getText().toString().equals("")) && (!puunPituus.getText().toString().equals(""))){
                    double ymparys = Double.valueOf(rungonYmparys.getText().toString());
                    double pituus = Double.valueOf(puunPituus.getText().toString());

                    Mottilaskuri mottilaskuri = new Mottilaskuri(ymparys, pituus);

                    double tilavuus = mottilaskuri.getTilavuus();

                    puunTilavuus.setText(String.valueOf(kaksiDesimaalia.format(tilavuus)));
                } else{
                    Toast.makeText(MainActivity.this, "Anna puun pituus ja korkeus", Toast.LENGTH_LONG).show();
                }

            }
        });












    }
}