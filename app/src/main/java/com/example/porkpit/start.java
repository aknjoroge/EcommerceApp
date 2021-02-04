package com.example.porkpit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class start extends AppCompatActivity {
Button tologin ,tosignup;
TextView passf;
    FirebaseAuth fAuth;
    ProgressBar sone;
    TextView load;
    lanchermanager lanchermanager;
    private static int CODE_AUTHENTICATION_VERIFICATION=241;
    String takechecklock;
    String takenmail,takenpass;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fAuth = FirebaseAuth.getInstance();
        tologin =findViewById(R.id.loginbtn);
        passf=findViewById(R.id.txtpassf);
        load=findViewById(R.id.slogingtxt);
        sone=findViewById(R.id.slogbar);
        passf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(start.this, "Contact admin", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(getApplicationContext(),menuslides.class));
            }
        });
        tosignup =findViewById(R.id.signupbtn);
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
        tosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),signup.class));
            }
        });
        Paper.init(this);

            takenmail = Paper.book().read(prevalent.useremailkey);
            takenpass = Paper.book().read(prevalent.userpasskey);
            takechecklock = Paper.book().read(prevalent.lockstatkey);

          //  Toast.makeText(this, takechecklock, Toast.LENGTH_SHORT).show();

            if (takechecklock.equals("unlocked")) {
                if (takenmail != "" && takenpass != "") {
                    if (!TextUtils.isEmpty(takenmail) && !TextUtils.isEmpty(takenpass)) {
                        sone.setVisibility(View.VISIBLE);
                        load.setVisibility(View.VISIBLE);

                        fAuth.signInWithEmailAndPassword(takenmail,takenpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                sone.setVisibility(View.INVISIBLE);
                                load.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(getApplicationContext(),menuslides.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                sone.setVisibility(View.INVISIBLE);
                                load.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(getApplicationContext(),login.class));
                                Toast.makeText(start.this, "Auto Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                }
            }

            if (takechecklock.equals("locked")) {
callpassword();

               // Toast.makeText(this, "app will be locked", Toast.LENGTH_SHORT).show();
            }



    }

    private void callpassword() {
            KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
            if(km.isKeyguardSecure()) {
                Intent i = km.createConfirmDeviceCredentialIntent("Authentication required", "password");
                startActivityForResult(i, CODE_AUTHENTICATION_VERIFICATION);
            }
            else{
                Toast.makeText(start.this, "No any security setup done by user(pattern or password or pin or fingerprint", Toast.LENGTH_SHORT).show();
                //alert to continue without password

                AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                        .setTitle("No Security Found")
                        .setMessage("Continue?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),login.class));


                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(start.this, "App wont Launch,Restart and click Yes ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==CODE_AUTHENTICATION_VERIFICATION)
        {
            if (takenmail != "" && takenpass != "") {
                if (!TextUtils.isEmpty(takenmail) && !TextUtils.isEmpty(takenpass)) {
                    startActivity(new Intent(getApplicationContext(),login.class));

//                    fAuth.signInWithEmailAndPassword(takenmail,takenpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
//                            startActivity(new Intent(getApplicationContext(),menuslides.class));
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            startActivity(new Intent(getApplicationContext(),login.class));
//                            Toast.makeText(start.this, "Auto Login Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });



                }
            }

        }
        else
        {
            Toast.makeText(this, "Failure: Unable to verify user's identity", Toast.LENGTH_SHORT).show();
        }
    }

}