package com.example.porkpit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Spinner forlocations;
    private static final String TAG =null ;
    Button signin,signup;
    EditText name,phone,email,pass,town;
    String takename,takephone,takemail,takepass,taketown;
    ProgressBar progressBar;
    String userid;
    String loc;

    private FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        forlocations=findViewById(R.id.locspinner);
        ArrayAdapter<CharSequence> arrayAdapter =ArrayAdapter.createFromResource(this,R.array.locations,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        forlocations.setAdapter(arrayAdapter);
        forlocations.setOnItemSelectedListener(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        name=findViewById(R.id.regname);
        phone=findViewById(R.id.regphone);
        email=findViewById(R.id.regmail);
        town=findViewById(R.id.reglocation);
        pass=findViewById(R.id.regpass);
        progressBar=findViewById(R.id.progressBar);
        takename=name.getText().toString();
        takephone=phone.getText().toString();
        takemail=email.getText().toString();

        taketown=town.getText().toString();
        takepass=pass.getText().toString();
        signup=findViewById(R.id.signupbtn);

        signin =findViewById(R.id.tosignin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takename=name.getText().toString();
                takephone=phone.getText().toString();
                if(TextUtils.isEmpty(takemail=email.getText().toString())){
                    email.setError("Email is required");
                    return;
                }
               if(TextUtils.isEmpty(  taketown=town.getText().toString())){
                   town.setError("Town Location is Required");
                   return;
               }

                if(TextUtils.isEmpty( takepass=pass.getText().toString())){
                    pass.setError("password required");
                    return;
                }
                takepass=pass.getText().toString();
                if(takepass.length()< 6 ){
                    pass.setError("password need to be above 6 characters");
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(takemail,takepass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser fuser =fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(signup.this, "LOGIN WITH ACCOUNT CREATED ", Toast.LENGTH_LONG).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(signup.this, "Error sending verification , Contact Admin", Toast.LENGTH_SHORT).show();
                                }
                            });


                            Toast.makeText(signup.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            userid= fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userid);

                            Map<String, Object> user = new HashMap<>();
                            user.put("name", takename);
                            user.put("pass",takepass);
                            user.put("local Town",taketown);
                           user.put("Main City",loc) ;
                            user.put("phone", takephone);
                            user.put("mail", takemail);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void avoid) {
                                    Log.d(TAG, "user profile created " + userid);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG ,"on failure: "+e.toString());
                                }
                            });
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(),login.class));
                            Toast.makeText(signup.this, "LOGIN WITH ACCOUNT CREATED", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(signup.this, "An error occured" +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });


            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       loc=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, loc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}