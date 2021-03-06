package com.example.porkpit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class cart extends AppCompatActivity {
    public RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseUser using;
    String usercname=" ",usercprice= " ",usercno= " ";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
   String keptkey;
   String nameoforder =" ";
    Button tocheck,addetc;

    String setttprice;
    ImageView himage;
    String userid;
   int totalprice=0;
    TextView pname,pmail,fortotalview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        fAuth = FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.recyclermenucart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        using=fAuth.getCurrentUser();
        tocheck=findViewById(R.id.checkoutbtn);
        tocheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalprice == 0){
                    Toast.makeText(cart.this, "First add item To cart", Toast.LENGTH_SHORT).show();
                    tocheck.setVisibility(View.INVISIBLE);
                    fortotalview.setText("Cart is Empty");
                }else {
                   // savetcheckout();
                    saveforadmin();
                    String setinpricea=setttprice;
                    setttprice= null;
                    totalprice=0;
                    Intent intent = new Intent(cart.this, checkout.class);

                    intent.putExtra("ttprice", setinpricea);
                    startActivity(intent);
                }
            }
        });
        fortotalview=findViewById(R.id.carttotaltxt);
        addetc=findViewById(R.id.extracaartbutton);

        addetc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),home.class));
            }
        });
        fStore = FirebaseFirestore.getInstance();
        userid = fAuth.getCurrentUser().getUid();


    }



    private void savetcheckout() {



        Map<String,Object> savedcart= new HashMap<>();

        savedcart.put("Productname",usercname +" ");
        savedcart.put("Productprice",usercprice+" ");
        savedcart.put("Productno",usercno+" ");


        fStore.collection("Checkoutcart").document(userid).set(savedcart).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(cart.this, "error sending"+e.toString(), Toast.LENGTH_SHORT).show();

            }
        });




    }

    public void oncartclick(final String key){

        android.app.AlertDialog dialog = new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                .setTitle("Cart Opptions")
                .setMessage("Remove Product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fStore.collection("CartList").document("orders").collection(userid).document(key)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(cart.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(cart.this, "Error deleting document", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirestoreRecyclerOptions<forcart> options = new FirestoreRecyclerOptions.Builder<forcart>()
                .setQuery(fStore.collection("CartList").document("orders").collection(userid),forcart.class).build();
        FirestoreRecyclerAdapter<forcart,cartviewholder> adapter= new FirestoreRecyclerAdapter<forcart, cartviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cartviewholder holder, int position, @NonNull final forcart model) {

             holder.txtpname.setText("Name :"+model.getName()+".");
             holder.txtpprice.setText("Price: "+model.getPrice()+"/=");
                holder.txtpdescription.setText(model.getCategory());
            keptkey=model.getKey();
          // int priceacf= (Integer.parseInt(model.getPrice()));

                usercname = usercname +"Item Name:"+model.getName()+"\n";
                usercprice = usercprice +"Item Price:"+model.getPrice();
                usercno = usercno +"Item Amount:"+model.getAmount()+"\n";

                nameoforder =nameoforder + model.getName() +", ";

              String curentprice =model.getPrice();
              String currentamoungt= model.getAmount();

               int perquant=((Integer.parseInt(model.getPrice()))) * Integer.parseInt(model.getAmount());


               String forqtotal= String.valueOf(perquant);

                totalprice=totalprice+perquant;
                setttprice= String.valueOf(totalprice);

    holder.txttotalprice.setText("Total :"+forqtotal);
               fortotalview.setText(setttprice);
                holder.txtpamount.setText("No: "+model.getAmount());

                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    oncartclick(model.getKey());

                    }
                });
            }

            @NonNull
            @Override
            public cartviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false);
                cartviewholder holder =new cartviewholder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    private void saveforadmin() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
        String cdate =currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        String ctime =currenttime.format(calendar.getTime());
        String crandomkey =ctime+cdate;

    Map <String ,Object> admind = new HashMap<>();
     admind.put("orders",nameoforder);


        fStore.collection("Paidorders").document("all").collection("saveone").document(userid).set(admind).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(cart.this, "error sending"+e.toString(), Toast.LENGTH_SHORT).show();

            }
        });




    }

}