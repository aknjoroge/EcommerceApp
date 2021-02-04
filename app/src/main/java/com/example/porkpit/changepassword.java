package com.example.porkpit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class changepassword extends AppCompatActivity {
Button setpasschanges,dells;
   FirebaseFirestore fStore;
    String oldpps;
    String userid;
    String confpp;
    String passtake1;
    FirebaseUser using;
    String newpp;
    FirebaseAuth fAuth;
  //  StorageReference storageReference;
EditText oldpassqordget,newpassget,confpassget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        dells=findViewById(R.id.delbtn);
        setpasschanges=findViewById(R.id.setchangeppbtn);
        oldpassqordget=findViewById(R.id.confirmoldpass);
        newpassget=findViewById(R.id.passnewpass);
        confpassget=findViewById(R.id.passconfirmnewpass);

        dells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dellaccounts();
                // Toast.makeText(accounts.this, " Not Yet Initialized", Toast.LENGTH_SHORT).show();
            }
        });


   fAuth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();
      userid = fAuth.getCurrentUser().getUid();
//        storageReference = FirebaseStorage.getInstance().getReference();

        setpasschanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(oldpassqordget.getText().toString())){
                    oldpassqordget.setError("enter old password");
                } if(TextUtils.isEmpty(newpassget.getText().toString())){
newpassget.setError("field is required");
                }
                if(TextUtils.isEmpty(confpassget.getText().toString())){
confpassget.setError("field is required");
                }


                    oldpps=oldpassqordget.getText().toString();
                   confpp=confpassget.getText().toString();
                     newpp=newpassget.getText().toString();

                    if(oldpps.equals(passtake1) ){
                        if(confpp.equals(newpp)){
                            Toast.makeText(changepassword.this, "password identical", Toast.LENGTH_SHORT).show();
                            changepassworda();
                        }if(!confpp.equals(newpp)){
                          confpassget.setError("password not dentical");
                        }

                    }else {
                        Toast.makeText(changepassword.this, "Check Details", Toast.LENGTH_SHORT).show();
                    }











            }
        });
        using=fAuth.getCurrentUser();
        checkoldpassabc();

    }


    public void dellaccounts(){

        final EditText dellac= new EditText(this);
        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("DELETE ACCOUNT AND LOOSE DATA!!!")
                .setMessage("Enter The Word 'DELETE' TO CONFIRM")
                .setView(dellac)
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!TextUtils.isEmpty(dellac.getText().toString())) {
                            String in=dellac.getText().toString();
                            if(in.equals("DELETE")){
                                using.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(changepassword.this, "YOU HAVE BENN DELETED CLOSING APP", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(changepassword.this, "Delete was Not Successfull", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }else {
                                Toast.makeText(changepassword.this, "DELETE not typed Correctly", Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {
                            Toast.makeText(changepassword.this, "Field is empty", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(changepassword.this, "Deleting Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }




    public void checkoldpassabc(){

        final DocumentReference documentReference = fStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                passtake1=documentSnapshot.getString("pass");


            }
        });
    }

    public void changepassworda(){

        using.updatePassword(newpp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Map<String,Object> usermaps =new HashMap<>();
                usermaps.put("pass",newpp);
                final DocumentReference documentReference = fStore.collection("users").document(userid);

                documentReference.set(usermaps, SetOptions.merge());

                Toast.makeText(changepassword.this, "Updated successfully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(changepassword.this, "NOT Updated !", Toast.LENGTH_SHORT).show();
            }
        });

    }

}