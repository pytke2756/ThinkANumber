package com.example.thinkanumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button btnPlussz, btnMinusz, btnTipp;
    private ImageView hp1, hp2, hp3, hp4;
    private TextView tipp;
    private int tippInt, gondoltSzam, maximum, elet;
    private Random rnd;
    private ImageView[] eletek;
    private Toast customToast;
    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        ujJatek();

        btnPlussz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tippInt < maximum){
                    tippInt++;
                    tipp.setText(String.valueOf(tippInt));
                }else{
                    //Hibaüzenet, a tibb nem lehet nagyobb a maximumnal
                    Toast.makeText(getApplicationContext(), "10-nél több nem lehet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMinusz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tippInt > 1){
                    tippInt--;
                    tipp.setText(String.valueOf(tippInt));
                }else{
                    //Hibaüzenet, tipp nem lehet kisebb mint 1

                    /*Toast toast = new Toast(getApplicationContext());
                    toast.setText("Így is meg lehet csináni egy toastot");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();*/

                    Toast.makeText(getApplicationContext(), "1-nél kisebb nem lehet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnTipp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tippInt == gondoltSzam){
                    //Győzelem, Alert dialog
                    alertBuilder.setTitle("Győztél, játék vége!").create().show();
                }else if (tippInt < gondoltSzam){
                    //Nagyobb számra gondoltam, Egyedi toast
                    eletLevon();
                    customToast.show();
                    Toast.makeText(getApplicationContext(), "Nagyobb számra gondoltam!", Toast.LENGTH_SHORT).show();
                }else{
                    //Kisebb számra gondoltam, Egyedi toast
                    eletLevon();
                    customToast.show();
                    Toast.makeText(getApplicationContext(), "Kisebb számra gondoltam!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eletLevon() {
        if (this.elet > 0){
            this.elet--;
        }
        this.eletek[elet].setImageResource(R.drawable.heart1);
        if (this.elet == 0){
            //jatek vege
            alertBuilder.setTitle("Vesztettél, játék vége!"); //össze is ehet fűzni a create-e meg a show-al
            alertBuilder.create();
            alertBuilder.show();
        }
    }

    private void ujJatek() {
        this.tippInt = 1;
        this.tipp.setText("1");
        this.gondoltSzam = rnd.nextInt(maximum);
        this.elet = 4;
        Log.d("A gondolt szám:", String.valueOf(gondoltSzam));
        for (ImageView elet : this.eletek) {
            elet.setImageResource(R.drawable.heart2);
        }
    }

    private void init(){
        this.btnPlussz = findViewById(R.id.btn_plussz);
        this.btnMinusz = findViewById(R.id.btn_minusz);
        this.btnTipp = findViewById(R.id.btn_tipp);
        this.hp1 = findViewById(R.id.hp1);
        this.hp2 = findViewById(R.id.hp2);
        this.hp3 = findViewById(R.id.hp3);
        this.hp4 = findViewById(R.id.hp4);
        this.tipp = findViewById(R.id.txt_szamlalo);
        this.maximum = 10;
        this.rnd = new Random();
        this.eletek = new ImageView[]{hp1, hp2, hp3, hp4};
        this.customToast = new Toast(getApplicationContext());
        createCustomToast();
        alertBuilder = new AlertDialog.Builder(getApplicationContext());
        createAlertDialog();
    }

    private void createAlertDialog() {
        alertBuilder.setMessage("Szeretnél új játékot?");
        alertBuilder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertBuilder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ujJatek();
                closeContextMenu();
            }
        });
        alertBuilder.setNeutralButton("Test", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Neutral", "megnyomva");
            }
        });
        //Nem ehet félrenyomva kilépni belőle
        alertBuilder.setCancelable(false);
    }

    private void createCustomToast() {
        this.customToast.setDuration(Toast.LENGTH_SHORT);
        View view = getLayoutInflater().inflate(R.layout.custom_toast,
                findViewById(R.id.custom_toast));
        this.customToast.setView(view);
        this.customToast.setGravity(Gravity.CENTER,0,0);
    }
}