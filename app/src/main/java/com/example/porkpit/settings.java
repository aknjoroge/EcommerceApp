package com.example.porkpit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class settings extends AppCompatActivity {
ImageView settingdp;
    String takenews;
TextView dpchange,forsname,forphonename,forlocname,foremailname;
    FirebaseFirestore fStore;
    String userid;
    Uri imageuri;
String dpstate;
    String takechecklock;
    Switch forlocks;
 Button setforname,setforphone,setforpass,setforemail,setforlocation;
   FirebaseAuth fAuth;
    FirebaseUser using;
   StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        settingdp=findViewById(R.id.dpinsetting);


        forsname=findViewById(R.id.settxtnam);
        dpchange=findViewById(R.id.dpchangetxt);

Paper.init(this);

        userid = fAuth.getCurrentUser().getUid();
        forphonename=findViewById(R.id.setphonename);
        forlocname=findViewById(R.id.setlocationname);

        forlocks=findViewById(R.id.lockswitch);
        foremailname=findViewById(R.id.setemailname);

        using=fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();


       setforname=findViewById(R.id.setchangennamebtn);
        setforphone=findViewById(R.id.setchangephonebtn);
        setforpass=findViewById(R.id.setchangepassbtn);


        setforemail =findViewById(R.id.setchangeemailbtn);
       setforlocation=findViewById(R.id.setupdatelocationbtn);

        setforlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),changelocation.class));
           }
      });

        setforpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),changepassword.class));
            }
        });

        Paper.init(this);
        takechecklock = Paper.book().read(prevalent.lockstatkey);
        if(takechecklock.equals("locked")){
            forlocks.setChecked(true);
        }else {

        }

        setforname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
settingnames();

            }
        });

        setforemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
settingnmails();
            }
        });
        setforphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
settingphone();
            }
        });


forlocks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(forlocks.isChecked()){
            Toast.makeText(settings.this, "App locked", Toast.LENGTH_SHORT).show();
            String sec="locked";
            Paper.book().write(prevalent.lockstatkey,sec);
        }
        else{
            Toast.makeText(settings.this, "App unlocked", Toast.LENGTH_SHORT).show();
            String sec="unlocked";
            Paper.book().write(prevalent.lockstatkey,sec);
        }
    }
});

        dpchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askfordp();
            }
        });
     autochangeprofile2();
    }

    private void settingphone() {

        final EditText resetpassw= new EditText(this);
        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Update Phone?")
                .setMessage("Enter New Phone Number")
                .setView(resetpassw)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!TextUtils.isEmpty(resetpassw.getText().toString())) {
                            String takenew = resetpassw.getText().toString();

Map<String,Object> usermaps =new HashMap<>();
usermaps.put("phone",takenew);
                            final DocumentReference documentReference = fStore.collection("users").document(userid);

                                    documentReference.set(usermaps,SetOptions.merge());




                        }
                        else {
                            Toast.makeText(settings.this, "Field is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(settings.this, "phone change Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();


    }

    private void settingnmails() {

        final EditText resetpassw= new EditText(this);
        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Change Email?")
                .setMessage("Enter new Email : You will need to verify Email Again")
                .setView(resetpassw)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!TextUtils.isEmpty(resetpassw.getText().toString())) {
                            String takenew = resetpassw.getText().toString();
                            takenews=resetpassw.getText().toString();

                            using.updateEmail(takenew).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Map<String,Object> usermaps =new HashMap<>();
                                    usermaps.put("mail",takenews);
                                    final DocumentReference documentReference = fStore.collection("users").document(userid);

                                    documentReference.set(usermaps,SetOptions.merge());
                                    Toast.makeText(settings.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(settings.this, "NOT Updated !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            Toast.makeText(settings.this, "Field is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(settings.this, "Email change Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }

    private void settingnames() {
        final EditText resetpassw= new EditText(this);
        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Update Name?")
                .setMessage("Enter Name.")
                .setView(resetpassw)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!TextUtils.isEmpty(resetpassw.getText().toString())) {
                            String takenew = resetpassw.getText().toString();

                            Map<String,Object> usermaps =new HashMap<>();
                            usermaps.put("name",takenew);
                            final DocumentReference documentReference = fStore.collection("users").document(userid);

                            documentReference.set(usermaps,SetOptions.merge());




                        }
                        else {
                            Toast.makeText(settings.this, "Field is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(settings.this, "name change Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();


    }

    private void autochangeprofile2() {
        final DocumentReference documentReference = fStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String nametake=documentSnapshot.getString("name");
                String mailtake =documentSnapshot.getString("mail");
                String locatake=documentSnapshot.getString("Main City");
                String locatake2=documentSnapshot.getString("local Town");
                String alllocs =locatake2+" in "+locatake;
                String phonetake =documentSnapshot.getString("phone");

                forsname.setText(nametake);
                forphonename.setText(phonetake);
                foremailname.setText(mailtake);
                forlocname.setText(alllocs);
                autochangeprofiles();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Toast.makeText(this, "Data received", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();

                imageuri = data.getData();
                settingdp.setImageURI(imageuri);
//                hasimageset=true;

                 uploadimage(imageuri);
            }catch (Exception e){

                Toast.makeText(this, "Run time exception gained", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadimage(Uri imageuri) {

        final StorageReference fileref = storageReference.child("Userprofile/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Paper.book().write(prevalent.profileset,"Yes");
                        Picasso.get().load(uri).into(settingdp);
                        Toast.makeText(settings.this, "IMAGE UPLOADED", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(settings.this, "Failed in getting url", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(settings.this, "UPLOAD ERROOR", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void autochangeprofiles() {

        dpstate = Paper.book().read(prevalent.profileset);

        if (dpstate != "") {
            if (!TextUtils.isEmpty(dpstate)) {
                StorageReference profileref = storageReference.child("Userprofile/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
                profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //  Toast.makeText(settings.this, "Loading profile", Toast.LENGTH_SHORT).show();
                        Picasso.get().load(uri).into(settingdp);
                        // Picasso.get().load(uri).into(nav_image);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }}else {

        }






    }

    private void askfordp() {
        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Profile Image")
                .setMessage("update an image?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
takeindp();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void takeindp() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);


    }
}