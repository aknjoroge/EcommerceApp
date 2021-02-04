package com.example.porkpit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class aboutactivity extends AppCompatActivity {
    Button one,two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutactivity);
        one=findViewById(R.id.button1c);
        two=findViewById(R.id.button2c);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callone();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calltwo();

            }
        });
    }

    public void callone(){
        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("PLACE CALL")
                .setMessage("Contact Support On :+254710664418?")

                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:+254710664418"));
                        startActivity(intent);

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(aboutactivity.this, "Call Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    public void calltwo(){
        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("PLACE CALL")
                .setMessage("Contact Support On :+254706110000?")

                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:+254706110000"));
                        startActivity(intent);

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(aboutactivity.this, "Call Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


}
