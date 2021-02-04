package com.example.porkpit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class productdetails extends AppCompatActivity {
ImageView detailimage;
TextView txtdetailname,txtdetailprice,txtdetaildescr;
ElegantNumberButton add;
    String takeprice;
   String productid;
   String takename;
    String cartkey;
    String amounts;
    String pathsa;
    String takecat;
    String usernamea;
    String userphonea; String usermaila;
Button tocart;
    String htmls;
    ProgressBar indetail;
    FirebaseAuth fAuth;
    String cdate,ctime;
    FirebaseUser using;
    String userid;
    FirebaseFirestore fStore;
String pid,cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        fAuth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();
        detailimage=findViewById(R.id.detailproductimage);
        txtdetailname=findViewById(R.id.detailproductname);
        txtdetailprice=findViewById(R.id.detailproductprice);
        txtdetaildescr=findViewById(R.id.detailproductdescription);
        add=findViewById(R.id.elegandadd);
        userid = fAuth.getCurrentUser().getUid();
        tocart=findViewById(R.id.dcartbtn);
        indetail=findViewById(R.id.detprogresbar);

        pid=getIntent().getStringExtra("pid");
        cid=getIntent().getStringExtra("cid");

      // Toast.makeText(this, pid+"sec"+cid, Toast.LENGTH_SHORT).show();
       uploaddate();


tocart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        indetail.setVisibility(View.VISIBLE);
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
        cdate =currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        ctime =currenttime.format(calendar.getTime());

         cartkey= ctime+cdate;
         takename=txtdetailname.getText().toString();
         takeprice= txtdetailprice.getText().toString();
         takecat=txtdetaildescr.getText().toString();
         productid=pid;
amounts=add.getNumber();
        final Map <String,Object> pdets =new HashMap<>();
        pdets.put("name",takename);
        pdets.put("time",ctime);
        pdets.put("date",cdate);
        pdets.put("key",cartkey);
        pdets.put("amount",amounts);
        pdets.put("price",takeprice);
        pdets.put("category",takecat);
        pdets.put("productid",pid);
String path =userid+"asfor"+cartkey;

        fStore.collection("CartList").document("orders").collection(userid).document(cartkey).set(pdets).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                 pathsa=userid+"asfor"+cartkey;
                Map <String,Object> pdets2 =new HashMap<>();
                pdets2.put("username",usernamea);
                pdets2.put("phone",userphonea);
                pdets2.put("email",usermaila);
                pdets2.put("time",ctime);
                pdets2.put("date",cdate);
                pdets2.put("key",cartkey);
                pdets2.put("productname",takename);
                pdets2.put("price",takeprice);
                pdets2.put("amount",amounts);
                pdets2.put("category",takecat);
                pdets2.put("productid",pid);


                fStore.collection("AdminOrderList").document(pathsa).set(pdets2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        indetail.setVisibility(View.INVISIBLE);
                        formoredetails();

                      //  Toast.makeText(productdetails.this, "Added to cart Successfully", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        indetail.setVisibility(View.INVISIBLE);
                        Toast.makeText(productdetails.this, "error in Data TWO"+e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


              //  Toast.makeText(productdetails.this, "Added to cart Successfully", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(productdetails.this, "error updating Data one"+e.toString(), Toast.LENGTH_SHORT).show();

            }
        });




    }
});

    }

    private void formoredetails() {

        Map <String,Object> details =new HashMap<>();
        details.put("productname",takename);
        details.put("amount",amounts);
        details.put("key",productid);

        fStore.collection("AdminOrderList").document(pathsa).collection("more").document(productid).set(details).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                indetail.setVisibility(View.INVISIBLE);

                Toast.makeText(productdetails.this, "Added to cart Successfully", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                indetail.setVisibility(View.INVISIBLE);
                Toast.makeText(productdetails.this, "error in Data TWO"+e.toString(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void getnameforadmin() {
        final DocumentReference documentReference = fStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                String nametakez=documentSnapshot.getString("name");

                String phonetakez =documentSnapshot.getString("phone");
                String emailtakez =documentSnapshot.getString("mail");
               usernamea=nametakez;
               userphonea=phonetakez;
               usermaila=emailtakez;




            }
        });
    }

    public void uploaddate() {

        final DocumentReference documentReference = fStore.collection("Products").document("all").collection(cid).document(pid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

               String nametake=documentSnapshot.getString("name");
              txtdetailname.setText(nametake);
                String pricetake =documentSnapshot.getString("price");
              String cattake =documentSnapshot.getString("category");
                String phonetake =documentSnapshot.getString("phone");
                String emailtake =documentSnapshot.getString("mail");

                txtdetaildescr.setText("Category: "+cattake);
                txtdetailprice.setText(pricetake);
               htmls=documentSnapshot.getString("filepath");
               // Toast.makeText(productdetails.this, htmls, Toast.LENGTH_SHORT).show();
                Picasso.get().load(htmls).into(detailimage);

getnameforadmin();

            }
        });
    }
}