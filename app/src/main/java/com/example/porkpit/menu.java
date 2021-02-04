package com.example.porkpit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import io.paperdb.Paper;

public class menu extends AppCompatActivity {
ImageView breakfast,lunches,acccs,fams,offers, help,ameat,akids,chickens;
Button tobreak,tolunch,toaccc,tofams,tooffer,tohelp,tomeat,tokids,tochicken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        breakfast=findViewById(R.id.breakimg);
        lunches=findViewById(R.id.lunchimg);
        acccs=findViewById(R.id.accsimg);
        Paper.init(this);
        fams=findViewById(R.id.famimg);
        offers=findViewById(R.id.dayimg);
        help=findViewById(R.id.helpimg);
        chickens=findViewById(R.id.chickimg);
        ameat=findViewById(R.id.meatimg);
        akids=findViewById(R.id.kidimg);

        tobreak=findViewById(R.id.breskfbtn);
        tolunch=findViewById(R.id.lunchbtn);
        toaccc=findViewById(R.id.acombtn);
        tofams=findViewById(R.id.fambtn);
        tomeat=findViewById(R.id.meatbtn);
        tokids=findViewById(R.id.kidbtn);
        tochicken=findViewById(R.id.chickbtn);
        tooffer=findViewById(R.id.daybtn);
        tohelp=findViewById(R.id.helpbtn);

        tobreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Breakfast");

                startActivity(intent);
            }
        });
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Breakfast");

                startActivity(intent);

            }
        });




        tochicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Chicken");

                startActivity(intent);
            }
        });
        chickens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Chicken");

                startActivity(intent);
            }
        });

        tokids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Kiddies");

                startActivity(intent);
            }
        });
        akids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Kiddies");

                startActivity(intent);
            }
        });

        tomeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Meat");

                startActivity(intent);

            }
        });
        ameat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Meat");

                startActivity(intent);
            }
        });



        tolunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Lunch");

                startActivity(intent);
            }
        });
        lunches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Lunch");

                startActivity(intent);
            }
        });

        toaccc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Accompaniments");

                startActivity(intent);
            }
        });

        acccs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Accompaniments");

                startActivity(intent);
            }
        });

        tofams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Family");

                startActivity(intent);
            }
        });

        fams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Family");

                startActivity(intent);
            }
        });

        tooffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Offers");

                startActivity(intent);
            }
        });

        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Offers");

                startActivity(intent);
            }
        });

        tohelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Bevarage");

                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, home.class);
                intent.putExtra("catvalue", "Bevarage");

                startActivity(intent);
            }
        });





    }
}