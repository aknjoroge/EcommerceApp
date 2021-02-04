package com.example.porkpit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class checkout extends AppCompatActivity {
String inpricea;
Button copytill,confirmpaid;
EditText meso;
String paymentode;
String tillnumber="";
    String mailtake;
TextView sprice;
    String crandomkey;
    Button chelpc;
    String userproductname,userproductprice,userprodustamount;
    String cdate,ctime;
    FirebaseUser using;
    String locatake;
    FirebaseAuth fAuth;
    String locatake2;
    FirebaseFirestore fStore;
    String alllocs;
    String nametake;
    String phonetake;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
sprice=findViewById(R.id.textamount);
chelpc=findViewById(R.id.callhelpc);
chelpc.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        callone();
    }
});
copytill=findViewById(R.id.hastillbtn);
confirmpaid=findViewById(R.id.haspaidbtn);

meso=findViewById(R.id.editTextmpesam);

        fAuth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();

        inpricea=getIntent().getStringExtra("ttprice");
        Toast.makeText(this, "total is "+inpricea, Toast.LENGTH_SHORT).show();
        sprice.setText("5.Next Enter Amount :" +inpricea);


        copytill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tillnumber="000686";
                Toast.makeText(checkout.this, "Till Number Copied To ClipBoard", Toast.LENGTH_SHORT).show();

                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("tillno",tillnumber);
                clipboardManager.setPrimaryClip(clipData);
            }
        });

        using=fAuth.getCurrentUser();
        userid = fAuth.getCurrentUser().getUid();
        autodetails();

        confirmpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(meso.getText().toString())){
                    paymentode="Auto Transaction";

                }else {
                    paymentode="Mpesa message :"+meso.getText().toString();

                }

                Calendar calendar= Calendar.getInstance();
                SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
                cdate =currentdate.format(calendar.getTime());

                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                ctime =currenttime.format(calendar.getTime());
                 crandomkey =ctime+cdate;

                Map<String,Object> ckout= new HashMap<>();
                ckout.put("tobepaid",inpricea);
                ckout.put("transactiontype",paymentode);
                ckout.put("paidname",nametake);
                ckout.put("location",alllocs);
                ckout.put("userphone",phonetake);
                ckout.put("mail",mailtake);
                ckout.put("time",ctime);
                ckout.put("date",cdate);
                ckout.put("randomkey",crandomkey);
                ckout.put("userid",userid);





                fStore.collection("Paidorders").document(crandomkey).set(ckout).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        getorderdata();


                       // Toast.makeText(checkout.this, "name"+userproductname+"price"+userproductprice+"amount"+userprodustamount, Toast.LENGTH_SHORT).show();

                        Toast.makeText(checkout.this, "order received", Toast.LENGTH_SHORT).show();
                        //ordersenddata();
                        confirmreceived();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(checkout.this, "error sending"+e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });




                String inmeso=paymentode+meso.getText().toString();
               // Toast.makeText(checkout.this, inmeso, Toast.LENGTH_SHORT).show();



            }
        });





    }

//    private void ordersenddata() {
//
//        Calendar calendar= Calendar.getInstance();
//        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
//        cdate =currentdate.format(calendar.getTime());
//
//        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
//        ctime =currenttime.format(calendar.getTime());
//        String prokey =ctime+cdate;
//
//        Map <String,Object> pdets2 =new HashMap<>();
//                pdets2.put("Name",userproductname);
//                pdets2.put("price",userproductprice);
//                pdets2.put("amount",userproductprice);
//        pdets2.put("pkey",prokey);
//
//
//        fStore.collection("AdminOrderList").document(userid).collection(prokey).document("orderplaced").set(pdets2).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(checkout.this, "Error sending to Admin", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//
//
//
//    }

//    private void getorderdata() {
//
//        final DocumentReference documentReference = fStore.collection("Checkoutcart").document(userid);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//
//
//                userproductname =documentSnapshot.getString("Productname");
//                userproductprice =documentSnapshot.getString("Productprice");
//                userprodustamount =documentSnapshot.getString("Productno");
//
//
//            }
//        });
//
//
//
//    }


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
                        Toast.makeText(checkout.this, "Call Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    private void autodetails() {

        final DocumentReference documentReference = fStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                 nametake=documentSnapshot.getString("name");
                 mailtake =documentSnapshot.getString("mail");
                 locatake=documentSnapshot.getString("Main City");
                 locatake2=documentSnapshot.getString("local Town");
                 alllocs =locatake2+" in "+locatake;
                 phonetake =documentSnapshot.getString("phone");

            }
        });



    }

    private void confirmreceived() {
        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Order Received")
                .setMessage("PorkPit Will Contact You Soon")
                .setPositiveButton("When", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(checkout.this, "Give us a minute", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}